package com.BarTender.services;

import com.BarTender.models.Bar;
import com.BarTender.models.Drink;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class DrinkOperationsService {
    private Firestore database = FirestoreClient.getFirestore();
    public void addDrink(Drink drink) {
        database.collection("drinks").document(drink.getId()).create(drink);
    }

    public List<Drink> getDrinksByBarId(String barId) {
        try {
            List<QueryDocumentSnapshot> drinksColleciton = database.collection("drinks").get().get().getDocuments();
            if (drinksColleciton.size() > 0) {
                List<Drink> drinksByBar = new ArrayList<>();
                if (drinksColleciton.stream().anyMatch((d -> d.getString("barId").equals(barId)))) {
                    List<QueryDocumentSnapshot> existingDrinks = drinksColleciton.stream().filter(d -> d.getString("barId").equals(barId)).collect(Collectors.toList());
                    for (QueryDocumentSnapshot drink:existingDrinks) {
                        drinksByBar.add(drink.toObject(Drink.class));
                    }
                    return drinksByBar;
                }
            }
            return null;
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            return null;
        }
    }

    public Drink getDrinkById(String id) {
        try {
            List<QueryDocumentSnapshot> drinks = database.collection("drinks").get().get().getDocuments();
            if (drinks.stream().anyMatch(d -> d.getId().equals(id))) {
                return drinks.stream().filter(d -> d.getId().equals(id)).findFirst().get().toObject(Drink.class);
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public void editDrink(Drink drink) {
        Map<String, Object> editedDrink = new HashMap<>();
        editedDrink.put("name", drink.getName());
        editedDrink.put("alcVolume", drink.getAlcVolume());
        editedDrink.put("price", drink.getPrice());
        editedDrink.put("amount", drink.getAmount());
        editedDrink.put("barId", drink.getBarId());
        editedDrink.put("description", drink.getDescription());
        if (drink.getImage() != null && !drink.getImage().isEmpty()) {
            editedDrink.put("image", drink.getImage());
        }

        if (drink.getId() != null && !drink.getId().isEmpty()) {
            database.collection("drinks").document(drink.getId()).update(editedDrink);
        }
    }
}
