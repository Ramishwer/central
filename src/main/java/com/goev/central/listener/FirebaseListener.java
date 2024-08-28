package com.goev.central.listener;

import com.google.firebase.database.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

@Component
@Slf4j
@AllArgsConstructor
public class FirebaseListener {


    private final Semaphore semaphore = new Semaphore(1);

    private final ExecutorService executorService;

    @PostConstruct
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void startListening() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        if (!semaphore.tryAcquire()) {
            log.info("Event listener already running");
            return;
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/partner");
        ref.keepSynced(true);
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object data = dataSnapshot.getValue();
                log.info("Firebase Added Data:{} {}", data, dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Object data = dataSnapshot.getValue();
                log.info("Firebase Changed Data:{} {}", data, dataSnapshot.getKey());
                String partnerUUID = dataSnapshot.getKey();


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Object data = dataSnapshot.getValue();
                log.info("Firebase Remove Data:{}", data);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Object data = dataSnapshot.getValue();
                log.info("Firebase Move Data:{} {}", data, dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                semaphore.release();
            }
        });

        executorService.submit(() -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
