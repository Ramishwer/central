package com.goev.central.scheduler;

import com.goev.central.dao.booking.BookingDao;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.booking.BookingSubStatus;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.partner.duty.PartnerShiftMappingRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class BookingStartingScheduler {

    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 1 *60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}",this.getClass().getName() ,DateTime.now());
        List<BookingDao> allBooking =bookingRepository.findAllActiveWithTime(DateTime.now().minusDays(1),DateTime.now().plusHours(2));

        for(BookingDao bookingDao:allBooking){
          bookingDao.setStatus(BookingStatus.IN_PROGRESS.name());
          bookingDao.setSubStatus(BookingSubStatus.UNASSIGNED.name());
          bookingRepository.update(bookingDao);
        }

    }
}
