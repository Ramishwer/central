package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.booking.BookingScheduleConfigurationDao;
import com.goev.central.dao.booking.BookingScheduleDao;
import com.goev.central.dao.booking.BookingScheduleTrackingDetailDao;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.SchedulingDetailDto;
import com.goev.central.enums.booking.SchedulingTypes;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.booking.BookingScheduleConfigurationRepository;
import com.goev.central.repository.booking.BookingScheduleRepository;
import com.goev.central.repository.booking.BookingScheduleTrackingDetailRepository;
import com.goev.central.service.booking.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class BookingCreationScheduler {

    private final BookingScheduleRepository bookingScheduleRepository;
    private final BookingScheduleConfigurationRepository bookingScheduleConfigurationRepository;
    private final BookingScheduleTrackingDetailRepository bookingScheduleTrackingDetailRepository;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<BookingScheduleDao> allSchedule = bookingScheduleRepository.findAllActiveWithTimeBetween(DateTime.now());

        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        int day = DateTime.now().getDayOfWeek();
        DateTime date = DateTime.now().withTimeAtStartOfDay();
        for (BookingScheduleDao scheduleDao : allSchedule) {
            BookingScheduleTrackingDetailDao existingBooking = bookingScheduleTrackingDetailRepository.findByBookingScheduleIdDayDate(scheduleDao.getId(), String.valueOf(day), date);
            if (existingBooking != null)
                continue;

            BookingScheduleConfigurationDao bookingScheduleConfigurationDao = bookingScheduleConfigurationRepository.findByBookingScheduleIdAndDay(scheduleDao.getId(), String.valueOf(day));


            if (bookingScheduleConfigurationDao != null) {
                BookingScheduleTrackingDetailDao bookingScheduleTrackingDetailDao = new BookingScheduleTrackingDetailDao();
                bookingScheduleTrackingDetailDao.setBookingScheduleId(scheduleDao.getId());
                bookingScheduleTrackingDetailDao.setBookingDate(date);
                bookingScheduleTrackingDetailDao.setDay(String.valueOf(day));
                bookingScheduleTrackingDetailDao.setStatus("PENDING");
                bookingScheduleTrackingDetailDao =bookingScheduleTrackingDetailRepository.save(bookingScheduleTrackingDetailDao);


                BookingRequestDto bookingRequest = ApplicationConstants.GSON.fromJson(bookingScheduleConfigurationDao.getBookingConfig(), BookingRequestDto.class);

                bookingRequest.setScheduleDetails(SchedulingDetailDto.builder()
                        .startTime(date.plus(formatter.parseDateTime(bookingScheduleConfigurationDao.getStart()).getMillis()))
                        .type(SchedulingTypes.SCHEDULED)
                        .build());

                BookingDto bookingDto = bookingService.createBooking(bookingRequest);
                BookingDao booking = bookingRepository.findByUUID(bookingDto.getUuid());
                if(booking!=null){
                    bookingScheduleTrackingDetailDao.setStatus("CONFIRMED");
                    bookingScheduleTrackingDetailDao.setBookingId(booking.getId());
                    bookingScheduleTrackingDetailDao = bookingScheduleTrackingDetailRepository.update(bookingScheduleTrackingDetailDao);
                }

            }


        }

    }
}
