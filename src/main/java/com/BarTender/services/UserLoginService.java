package com.BarTender.services;

import com.BarTender.models.User;
import com.BarTender.utils.AppConstants;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class UserLoginService {
    private Firestore database = FirestoreClient.getFirestore();
    private int managerRole = AppConstants.RoleConstants.MANAGER;
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

    public List<User> getAllManagers() {
        try {
            List<QueryDocumentSnapshot> users = database.collection("users").get().get().getDocuments();
            List<User> managerUsers = new ArrayList<>();
            if (users.stream().anyMatch((u -> u.getLong("roleId") == managerRole))) {
                List<QueryDocumentSnapshot> managers = users.stream().filter(u -> u.getLong("roleId") == managerRole).collect(Collectors.toList());
                for (QueryDocumentSnapshot manager:managers) {
                    managerUsers.add(manager.toObject(User.class));
                }
                return managerUsers;
            }
            return null;
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            return null;
        }
    }
}
