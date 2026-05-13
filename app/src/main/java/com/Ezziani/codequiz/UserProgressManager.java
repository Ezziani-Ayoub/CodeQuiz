package com.Ezziani.codequiz;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class UserProgressManager {

    private static final int UNLOCK_THRESHOLD = 8;

    public interface ProgressCallback {
        void onProgressLoaded(boolean mediumUnlocked, boolean hardUnlocked);
    }

    public interface SaveCallback {
        void onSaved(String newLevel);
    }

    public static void getProgress(String language, ProgressCallback callback) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(uid)
                .collection("progress")
                .document(language)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        Long easyBest = doc.getLong("easyBest");
                        Long mediumBest = doc.getLong("mediumBest");
                        boolean mediumUnlocked = easyBest != null && easyBest >= UNLOCK_THRESHOLD;
                        boolean hardUnlocked = mediumBest != null && mediumBest >= UNLOCK_THRESHOLD;
                        callback.onProgressLoaded(mediumUnlocked, hardUnlocked);
                    } else {
                        callback.onProgressLoaded(false, false);
                    }
                })
                .addOnFailureListener(e -> callback.onProgressLoaded(false, false));
    }

    public static void saveProgress(String language, String difficulty,
                                    int score, SaveCallback callback) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String field = difficulty.equals("Easy") ? "easyBest"
                : difficulty.equals("Medium") ? "mediumBest" : "hardBest";

        db.collection("users")
                .document(uid)
                .collection("progress")
                .document(language)
                .get()
                .addOnSuccessListener(doc -> {
                    long currentBest = 0;
                    if (doc.exists() && doc.getLong(field) != null) {
                        currentBest = doc.getLong(field);
                    }

                    Map<String, Object> update = new HashMap<>();
                    if (score > currentBest) {
                        update.put(field, score);
                    }

                    db.collection("users")
                            .document(uid)
                            .collection("progress")
                            .document(language)
                            .set(update, com.google.firebase.firestore.SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                updateUserLevel(db, uid, callback);
                            });
                });
    }

    private static void updateUserLevel(FirebaseFirestore db,
                                        String uid, SaveCallback callback) {
        db.collection("users")
                .document(uid)
                .collection("progress")
                .get()
                .addOnSuccessListener(snapshot -> {
                    int totalBest = 0;
                    for (var doc : snapshot.getDocuments()) {
                        Long easyBest = doc.getLong("easyBest");
                        Long mediumBest = doc.getLong("mediumBest");
                        Long hardBest = doc.getLong("hardBest");
                        if (easyBest != null) totalBest += easyBest;
                        if (mediumBest != null) totalBest += mediumBest;
                        if (hardBest != null) totalBest += hardBest;
                    }

                    String newLevel;
                    if (totalBest >= 150) newLevel = "Expert";
                    else if (totalBest >= 100) newLevel = "Advanced";
                    else if (totalBest >= 50) newLevel = "Intermediate";
                    else newLevel = "Beginner";

                    Map<String, Object> levelUpdate = new HashMap<>();
                    levelUpdate.put("level", newLevel);
                    levelUpdate.put("totalScore", totalBest);

                    db.collection("users").document(uid)
                            .update(levelUpdate)
                            .addOnSuccessListener(unused -> callback.onSaved(newLevel));
                });
    }
}