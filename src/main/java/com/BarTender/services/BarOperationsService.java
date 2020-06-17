package com.BarTender.services;

import com.BarTender.models.Bar;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BarOperationsService {
    private Firestore database = FirestoreClient.getFirestore();
    public void addBar(Bar bar) {
        database.collection("bars").document(bar.getId()).create(bar);
    }

    public List<Bar> getAllBars() {
        try {
            List<QueryDocumentSnapshot> barsCollection = database.collection("bars").get().get().getDocuments();
            if (barsCollection.size() > 0) {
                List<Bar> bars = new ArrayList<>();
                for (QueryDocumentSnapshot bar:barsCollection) {
                    bars.add(bar.toObject(Bar.class));
                }
                return bars;
            }
            return null;
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            return null;
        }
    }
}
