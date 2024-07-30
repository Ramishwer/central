package com.goev.central.repository;

import com.goev.central.constant.ApplicationConstants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FirebaseRepository {
    private final FirebaseApp app;

    public Boolean writeToFirebase(String path, Object data) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (data instanceof Integer || data instanceof String || data instanceof Double || data instanceof Boolean ||
                data instanceof Float || data instanceof Long) {
            database.getReference(path).setValueAsync(data);
            return true;
        }

        Type t = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> parsedData = ApplicationConstants.GSON.fromJson(ApplicationConstants.GSON.toJson(data), t);
        database.getReference(path).setValueAsync(parsedData);
        return true;
    }

    public Map<String, Object> getFromFirebase(String path) throws Exception {
        List<Object> result = new ArrayList<>();
        final Semaphore semaphore = new Semaphore(0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object data = dataSnapshot.getValue();
                result.add(data);
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                semaphore.release();
            }
        });

        semaphore.acquire();
        Type t = new TypeToken<Map<String, Object>>() {
        }.getType();
        return ApplicationConstants.GSON.fromJson(ApplicationConstants.GSON.toJson(result.get(0)), t);
    }

    public Boolean updateChildrenToFirebase(String path, Object data) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (data instanceof Integer || data instanceof String || data instanceof Double || data instanceof Boolean ||
                data instanceof Float || data instanceof Long) {
            database.getReference(path).setValueAsync(data);
            return true;
        }

        Type t = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> parsedData = ApplicationConstants.GSON.fromJson(ApplicationConstants.GSON.toJson(data), t);
        database.getReference(path).updateChildrenAsync(parsedData);
        return true;
    }
}
