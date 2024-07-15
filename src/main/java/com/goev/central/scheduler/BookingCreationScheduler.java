package com.goev.central.scheduler;

import com.goev.central.dao.booking.BookingScheduleDao;
import com.goev.central.repository.booking.BookingScheduleRepository;
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
public class BookingCreationScheduler {

    private final BookingScheduleRepository bookingScheduleRepository;
//    private final BookingScheduleConfigurationRepository bookingScheduleConfigurationRepository;

//    @Scheduled(fixedRate = 10*60 * 1000)
//    public void reportCurrentTime() {
//        log.info("The {} time is now {}",this.getClass().getName() ,DateTime.now());
//        List<BookingScheduleDao> allSchedule =bookingScheduleRepository.findAllActiveWithTimeBetween(DateTime.now(),DateTime.now().plusHours(12));
//
//        for(BookingScheduleDao scheduleDao:allSchedule){
//
//
//        }
//
//    }
}
