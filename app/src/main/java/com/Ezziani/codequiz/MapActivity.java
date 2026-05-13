package com.Ezziani.codequiz;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationClient;
    TextView tvLocationInfo;
    FirebaseFirestore db;
    FirebaseAuth auth;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("🌍 Global Players Map");
        }

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        tvLocationInfo = findViewById(R.id.tvLocationInfo);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // When user taps a marker show their info
        mMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            return true;
        });

        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            Toast.makeText(this, "Location permission denied",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        mMap.setMyLocationEnabled(true);
        tvLocationInfo.setText("📍 Getting your location...");

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        saveAndShowLocation(location);
                    } else {
                        requestFreshLocation();
                    }
                })
                .addOnFailureListener(e -> requestFreshLocation());
    }

    private void requestFreshLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        LocationRequest locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdates(1)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult result) {
                Location location = result.getLastLocation();
                if (location != null) {
                    saveAndShowLocation(location);
                } else {
                    runOnUiThread(() ->
                            tvLocationInfo.setText(
                                    "📍 Could not get location. Set location in emulator settings.")
                    );
                }
                fusedLocationClient.removeLocationUpdates(locationCallback);
            }
        };

        fusedLocationClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void saveAndShowLocation(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng userLatLng = new LatLng(lat, lng);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(lat, lng, 1, addresses -> {
                String cityName = getCityFromAddresses(addresses);
                runOnUiThread(() -> {
                    saveLocationToFirestore(lat, lng, cityName);
                    focusOnUser(userLatLng, cityName);
                    loadAllPlayers();
                });
            });
        } else {
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                String cityName = getCityFromAddresses(addresses);
                saveLocationToFirestore(lat, lng, cityName);
                focusOnUser(userLatLng, cityName);
                loadAllPlayers();
            } catch (Exception e) {
                saveLocationToFirestore(lat, lng, "Unknown");
                focusOnUser(userLatLng, "Unknown");
                loadAllPlayers();
            }
        }
    }

    private void saveLocationToFirestore(double lat, double lng, String cityName) {
        String uid = auth.getCurrentUser().getUid();
        Map<String, Object> update = new HashMap<>();
        update.put("city", cityName);
        update.put("lat", lat);
        update.put("lng", lng);
        db.collection("users").document(uid).update(update);
    }

    private void focusOnUser(LatLng userLatLng, String cityName) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 5f));
        tvLocationInfo.setText("📍 Your location: " + cityName +
                " — Tap any marker to see a player!");
    }

    private void loadAllPlayers() {
        String currentUid = auth.getCurrentUser().getUid();

        db.collection("users").get()
                .addOnSuccessListener(snapshot -> {
                    int playerCount = 0;
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Double lat = doc.getDouble("lat");
                        Double lng = doc.getDouble("lng");
                        String username = doc.getString("username");
                        String level = doc.getString("level");
                        String city = doc.getString("city");

                        if (lat == null || lng == null || username == null) continue;

                        playerCount++;
                        LatLng playerLatLng = new LatLng(lat, lng);
                        String uid = doc.getId();

                        // Current user gets a different color marker
                        float markerColor = uid.equals(currentUid)
                                ? BitmapDescriptorFactory.HUE_AZURE
                                : BitmapDescriptorFactory.HUE_RED;

                        String snippet = "Level: " + (level != null ? level : "Beginner")
                                + (city != null ? " | 📍 " + city : "");

                        mMap.addMarker(new MarkerOptions()
                                .position(playerLatLng)
                                .title((uid.equals(currentUid) ? "⭐ You — " : "👤 ") + username)
                                .snippet(snippet)
                                .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
                    }

                    int finalPlayerCount = playerCount;
                    runOnUiThread(() ->
                            tvLocationInfo.setText("🌍 " + finalPlayerCount +
                                    " players on the map — You are the blue marker!")
                    );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Could not load players",
                                Toast.LENGTH_SHORT).show()
                );
    }

    private String getCityFromAddresses(List<Address> addresses) {
        if (addresses != null && !addresses.isEmpty()) {
            Address address = addresses.get(0);
            if (address.getLocality() != null) return address.getLocality();
            if (address.getAdminArea() != null) return address.getAdminArea();
        }
        return "Unknown";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}