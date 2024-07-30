package com.goev.central.service.booking.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dto.booking.BookingPaymentDto;
import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.booking.BookingSubStatus;
import com.goev.central.enums.booking.SchedulingTypes;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.booking.BookingRequestRepository;
import com.goev.central.service.booking.BookingRequestService;
import com.goev.central.service.booking.BookingScheduleService;
import com.goev.central.service.booking.BookingService;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.dto.LatLongDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BookingRequestServiceImpl implements BookingRequestService {
    private final BookingRequestRepository bookingRequestRepository;
    private final BookingService bookingService;
    private final BookingScheduleService bookingScheduleService;
    @Override
    public BookingRequestDto createBookingRequest(BookingRequestDto bookingRequest) {


        if (SchedulingTypes.RECURRING.equals(bookingRequest.getScheduleDetails().getType())) {
            bookingScheduleService.createBookingSchedule(bookingRequest);
        } else {
            bookingService.createBooking(bookingRequest);
        }

        return bookingRequest;
    }

    @Override
    public BookingRequestDto updateBookingRequest(String bookingRequestUUID, BookingRequestDto bookingRequest) {
        return null;
    }

}
