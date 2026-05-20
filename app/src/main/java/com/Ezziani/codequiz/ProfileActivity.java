package com.Ezziani.codequiz;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfile;
    TextView tvUsername, tvEmail, tvLevel, tvCity;
    Button btnTakePhoto, btnMap, btnLogout;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Uri photoUri;
    String currentPhotoPath;
    SharedPreferences prefs;

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Bitmap bitmap = loadAndFixRotation(currentPhotoPath);
                    if (bitmap != null) {
                        ivProfile.setImageBitmap(bitmap);
                        prefs.edit().putString("profile_photo_" +
                                auth.getCurrentUser().getUid(), currentPhotoPath).apply();
                        Toast.makeText(this, "Profile photo updated!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Profile");
        }

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        prefs = getSharedPreferences("codequiz_prefs", MODE_PRIVATE);

        ivProfile = findViewById(R.id.ivProfile);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvLevel = findViewById(R.id.tvLevel);
        tvCity = findViewById(R.id.tvCity);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnMap = findViewById(R.id.btnMap);
        btnLogout = findViewById(R.id.btnLogout);

        loadSavedPhoto();
        loadUserData();

        btnTakePhoto.setOnClickListener(v -> checkCameraPermission());
        btnMap.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, MapActivity.class)));
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // ✅ Fix: reads EXIF orientation and rotates the bitmap correctly
    private Bitmap loadAndFixRotation(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) return null;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90); break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180); break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270); break;
                default: return bitmap;
            }
            return Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            return bitmap;
        }
    }

    private void loadSavedPhoto() {
        String uid = auth.getCurrentUser().getUid();
        String savedPath = prefs.getString("profile_photo_" + uid, null);
        if (savedPath != null) {
            File imgFile = new File(savedPath);
            if (imgFile.exists()) {
                Bitmap bitmap = loadAndFixRotation(savedPath);
                if (bitmap != null) ivProfile.setImageBitmap(bitmap);
            }
        }
    }

    private void loadUserData() {
        String uid = auth.getCurrentUser().getUid();
        tvEmail.setText(auth.getCurrentUser().getEmail());
        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        tvUsername.setText(doc.getString("username"));
                        tvLevel.setText("Level: " + doc.getString("level"));
                        String city = doc.getString("city");
                        tvCity.setText(city != null ? "📍 " + city : "📍 Location not set");
                    }
                });
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(this, "Camera permission denied",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File photoFile = createImageFile();
            photoUri = FileProvider.getUriForFile(this,
                    "com.Ezziani.codequiz.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraLauncher.launch(intent);
        } catch (IOException e) {
            Toast.makeText(this, "Error creating file",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile("PROFILE_" + timeStamp, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}