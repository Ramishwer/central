package com.goev.central.listener;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.FirebaseDetailDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.booking.BookingSubStatus;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.utilities.DistanceUtils;
import com.google.common.reflect.TypeToken;
import com.google.firebase.database.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

@Component
@Slf4j
@AllArgsConstructor
public class FirebaseListener {


    private final Semaphore semaphore = new Semaphore(1);

    private final ExecutorService executorService;
    private final PartnerRepository partnerRepository;
    private final BookingRepository bookingRepository;

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

                FirebaseDetailDto data = dataSnapshot.getValue(FirebaseDetailDto.class);
                log.info("Firebase Changed Data:{} {}", data, dataSnapshot.getKey());


                String partnerUUID = dataSnapshot.getKey();

                if (data==null || data.getLocation()==null || data.getLocation().getCoords()==null) {
                    log.info("{}", data);
                    return;
                }


                PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);

                if (partnerDao != null && partnerDao.getBookingId() != null) {
                    BookingDao booking = bookingRepository.findById(partnerDao.getBookingId());
                    if (booking != null && booking.getPartnerId() != null && booking.getPartnerId().equals(partnerDao.getId()) && BookingStatus.IN_PROGRESS.name().equals(booking.getStatus())) {
                        BookingViewDto viewDto = BookingViewDto.fromDao(booking);
                        if (viewDto != null) {
                            if (BookingSubStatus.ENROUTE.name().equals(booking.getSubStatus())) {
                                viewDto.setDistanceToReach(BigDecimal.valueOf(DistanceUtils.calculateDistance(data.getLocation().getCoords(), viewDto.getStartLocationDetails()) * 1.3).longValue());
                            } else if (BookingSubStatus.STARTED.name().equals(booking.getSubStatus())) {
                                viewDto.setDistanceToReach(BigDecimal.valueOf(DistanceUtils.calculateDistance(data.getLocation().getCoords(), viewDto.getEndLocationDetails()) * 1.3).longValue());

                            }
                            viewDto.setTimeToReach(BigDecimal.valueOf((viewDto.getDistanceToReach()/1000.0 / 40.0) * 3600000).longValue());
                            booking.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
                            bookingRepository.update(booking);

                            partnerDao.setBookingDetails(ApplicationConstants.GSON.toJson(viewDto));
                            partnerRepository.update(partnerDao);
                        }


                    }
                }


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
