package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.booking.BookingScheduleConfigurationDao;
import com.goev.central.dao.booking.BookingScheduleDao;
import com.goev.central.dao.booking.BookingScheduleTrackingDetailDao;
import com.goev.central.dto.booking.BookingPaymentDto;
import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.booking.BookingScheduleConfigurationRepository;
import com.goev.central.repository.booking.BookingScheduleRepository;
import com.goev.central.repository.booking.BookingScheduleTrackingDetailRepository;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.dto.LatLongDto;
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
    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<BookingScheduleDao> allSchedule = bookingScheduleRepository.findAllActiveWithTimeBetween(DateTime.now());

        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        int day = DateTime.now().getDayOfWeek();
        DateTime date = DateTime.now().withTimeAtStartOfDay();
        for (BookingScheduleDao scheduleDao : allSchedule) {
            BookingScheduleTrackingDetailDao existingShiftDao = bookingScheduleTrackingDetailRepository.findByBookingScheduleIdDayDate(scheduleDao.getId(), String.valueOf(day), date);
            if (existingShiftDao != null)
                continue;

            BookingScheduleConfigurationDao bookingScheduleConfigurationDao = bookingScheduleConfigurationRepository.findByBookingScheduleIdAndDay(scheduleDao.getId(), String.valueOf(day));


            if (bookingScheduleConfigurationDao != null) {
                BookingScheduleTrackingDetailDao bookingScheduleTrackingDetailDao = new BookingScheduleTrackingDetailDao();
                bookingScheduleTrackingDetailDao.setBookingScheduleId(scheduleDao.getId());
                bookingScheduleTrackingDetailDao.setBookingDate(date);
                bookingScheduleTrackingDetailDao.setDay(String.valueOf(day));
                bookingScheduleTrackingDetailDao.setStatus("PENDING");
                bookingScheduleTrackingDetailRepository.save(bookingScheduleTrackingDetailDao);


                BookingRequestDto bookingRequest = ApplicationConstants.GSON.fromJson(bookingScheduleConfigurationDao.getBookingConfig(),BookingRequestDto.class);

                BookingDao bookingDao = new BookingDao();
                bookingDao.setCustomerDetails(ApplicationConstants.GSON.toJson(bookingRequest.getCustomerDetails()));
                bookingDao.setStartLocationDetails(ApplicationConstants.GSON.toJson(bookingRequest.getStartLocationDetails(), LatLongDto.class));
                bookingDao.setEndLocationDetails(ApplicationConstants.GSON.toJson(bookingRequest.getEndLocationDetails(), LatLongDto.class));
                bookingDao.setStatus(BookingStatus.CONFIRMED.name());

                bookingDao.setPlannedStartTime(date.plus(formatter.parseDateTime(bookingScheduleConfigurationDao.getStartTime()).getMillis()));

                bookingDao.setDisplayCode("BRN-" + SecretGenerationUtils.getCode());
                bookingDao = bookingRepository.save(bookingDao);


                BookingViewDto viewDto = BookingViewDto.builder()
                        .uuid(bookingDao.getUuid())
                        .customerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getCustomerDetails(), CustomerViewDto.class))
                        .partnerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getPartnerDetails(), PartnerViewDto.class))
                        .vehicleDetails(ApplicationConstants.GSON.fromJson(bookingDao.getVehicleDetails(), VehicleViewDto.class))
                        .status(bookingDao.getStatus())
                        .subStatus(bookingDao.getSubStatus())
                        .startLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getStartLocationDetails(), LatLongDto.class))
                        .endLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getEndLocationDetails(), LatLongDto.class))
                        .plannedStartTime(bookingDao.getPlannedStartTime())
                        .displayCode(bookingDao.getDisplayCode())
                        .payment(BookingPaymentDto.builder()
                                .paymentMode(bookingRequest.getPaymentDetails().getType()).build())
                        .build();


                bookingDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
                bookingDao = bookingRepository.update(bookingDao);


            }


        }

    }
}
