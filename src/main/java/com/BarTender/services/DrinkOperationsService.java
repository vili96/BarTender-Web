package com.BarTender.services;

import com.BarTender.models.Drink;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class DrinkOperationsService {
    private Firestore database = FirestoreClient.getFirestore();
    public void addDrink(Drink drink) {
        database.collection("drinks").document(drink.getId()).create(drink);
    }
}
