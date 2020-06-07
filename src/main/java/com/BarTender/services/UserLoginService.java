package com.BarTender.services;

import com.BarTender.models.User;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserLoginService {
    private Firestore database = FirestoreClient.getFirestore();
    public void addUser(User user) {
        database.collection("users").document(user.getId()).create(user);
    }

    public User getUserById(String id) {
        try {
            List<QueryDocumentSnapshot> users = database.collection("users").get().get().getDocuments();
            if (users.stream().anyMatch(u -> u.getId().equals(id))) {
                return users.stream().filter(u -> u.getId().equals(id)).findFirst().get().toObject(User.class);
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
}
