package com.goev.central.service.booking.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dao.business.BusinessClientDetailDao;
import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.booking.*;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.booking.BookingSubStatus;
import com.goev.central.enums.booking.SchedulingTypes;
import com.goev.central.enums.customer.CustomerOnboardingStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.business.BusinessClientDetailRepository;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.service.booking.BookingService;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.dto.ContactDetailsDto;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PartnerRepository partnerRepository;
    private final VehicleRepository vehicleRepository;
    private final BusinessClientRepository businessClientRepository;
    private final BusinessClientDetailRepository businessClientDetailRepository;
    private final CustomerRepository customerRepository;

    @Override
    public PaginatedResponseDto<BookingViewDto> getBookings(List<String> status, String subStatus, DateTime from, DateTime to) {
        PaginatedResponseDto<BookingViewDto> result = PaginatedResponseDto.<BookingViewDto>builder().elements(new ArrayList<>()).build();
        List<BookingDao> bookingDaos = bookingRepository.findAllActive(status, subStatus, from, to);
        if (CollectionUtils.isEmpty(bookingDaos))
            return result;

        for (BookingDao bookingDao : bookingDaos) {
            result.getElements().add(BookingViewDto.fromDao(bookingDao));
        }
        return result;
    }

    @Override
    public BookingDto createBooking(BookingRequestDto bookingRequest) {
        DateTime startTime = DateTime.now();

        if(bookingRequest.getCustomerDetails().getPhoneNumber()==null)
            throw new ResponseException("Invalid Customer Details");
        CustomerDao customer = customerRepository.findByPhoneNumber(bookingRequest.getCustomerDetails().getPhoneNumber());

        if(customer == null){
            customer = new CustomerDao();
            customer.setPhoneNumber(customer.getPhoneNumber());
            customer.setOnboardingStatus(CustomerOnboardingStatus.ONBOARDED.name());
            customer = customerRepository.save(customer);

        }

        bookingRequest.setStartContact(ContactDetailsDto.builder()
                .firstName(bookingRequest.getCustomerDetails().getFirstName())
                .lastName(bookingRequest.getCustomerDetails().getLastName())
                .phoneNumber(bookingRequest.getCustomerDetails().getPhoneNumber()).build());
        bookingRequest.setEndContact(bookingRequest.getStartContact());

        if (bookingRequest.getScheduleDetails() != null && SchedulingTypes.SCHEDULED.equals(bookingRequest.getScheduleDetails().getType()))
            startTime = bookingRequest.getScheduleDetails().getStartTime() == null ? DateTime.now() : bookingRequest.getScheduleDetails().getStartTime();

        BookingDao bookingDao = new BookingDao();
        bookingDao.setCustomerId(customer.getId());
        bookingRequest.getCustomerDetails().setUuid(customer.getUuid());
        bookingDao.setCustomerDetails(ApplicationConstants.GSON.toJson(bookingRequest.getCustomerDetails()));
        bookingDao.setStartLocationDetails(ApplicationConstants.GSON.toJson(bookingRequest.getStartLocationDetails()));
        bookingDao.setEndLocationDetails(ApplicationConstants.GSON.toJson(bookingRequest.getEndLocationDetails()));
        bookingDao.setStatus(BookingStatus.CONFIRMED.name());
        bookingDao.setSubStatus(BookingSubStatus.UNASSIGNED.name());
        bookingDao.setPlannedStartTime(startTime);
        bookingDao.setDisplayCode("BRN-" + SecretGenerationUtils.getCode());
        bookingDao.setBookingTypeDetails(ApplicationConstants.GSON.toJson(bookingRequest.getBookingType()));

        BusinessClientDao clientDao =businessClientRepository.findByUUID(bookingRequest.getBusinessClient().getUuid());
        bookingDao.setBusinessClientId(clientDao.getId());

        BusinessClientDetailDao clientDetailDao = businessClientDetailRepository.findById(clientDao.getBusinessClientDetailsId());
        bookingDao.setBusinessSegmentId(clientDetailDao.getBusinessSegmentId());

        bookingDao = bookingRepository.save(bookingDao);


        BookingViewDto viewDto = BookingViewDto.builder()
                .uuid(bookingDao.getUuid())
                .bookingTypeDetails(bookingRequest.getBookingType())
                .customerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getCustomerDetails(), CustomerViewDto.class))
                .partnerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getPartnerDetails(), PartnerViewDto.class))
                .vehicleDetails(ApplicationConstants.GSON.fromJson(bookingDao.getVehicleDetails(), VehicleViewDto.class))
                .status(bookingDao.getStatus())
                .subStatus(bookingDao.getSubStatus())
                .startLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getStartLocationDetails(), LatLongDto.class))
                .endLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getEndLocationDetails(), LatLongDto.class))
                .plannedStartTime(bookingDao.getPlannedStartTime())
                .displayCode(bookingDao.getDisplayCode())
                .paymentDetails(BookingPaymentDto.builder()
                        .paymentMethod(bookingRequest.getPaymentDetails().getPaymentMethod())
                        .paymentMode(bookingRequest.getPaymentDetails().getPaymentMode()).build())
                .pricingDetails(bookingRequest.getPricingDetails())
                .startContact(bookingRequest.getStartContact())
                .endContact(bookingRequest.getEndContact())
                .duration(bookingRequest.getDuration())
                .distance(bookingRequest.getDistance())
                .businessClient(BusinessClientDto.builder().uuid(clientDao.getUuid()).name(clientDao.getName()).build())
                .businessSegment(bookingRequest.getBusinessSegment())
                .build();


        bookingDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
        bookingDao = bookingRepository.update(bookingDao);
        return getBookingDto(bookingDao);
    }

    private BookingDao getBookingDao(BookingDto bookingDto) {
        return BookingDao.fromDto(bookingDto);
    }

    private BookingDto getBookingDto(BookingDao bookingDao) {
        return BookingDto.fromDao(bookingDao);
    }

    @Override
    public BookingDto updateBooking(String bookingUUID, BookingActionDto bookingActionDto) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        switch (bookingActionDto.getAction()) {
            case UNDO -> {
            }
            case RESCHEDULE -> {
            }
            case CANCEL -> {
                bookingDao = cancelBooking(bookingDao, bookingActionDto);
            }
            case ADD_STOP -> {
            }
            case DELETE_STOP -> {
            }
            case NO_SHOW -> {
            }
            case ASSIGN_PARTNER -> {
                bookingDao = assignPartner(bookingDao, bookingActionDto);
            }
            case CHANGE_PARTNER -> {
                bookingDao = changePartner(bookingDao, bookingActionDto);
            }
            case UNASSIGN_PARTNER -> {
                bookingDao = unassignPartner(bookingDao, bookingActionDto);
            }
        }

        return getBookingDto(bookingDao);
    }

    private BookingDao unassignPartner(BookingDao bookingDao, BookingActionDto bookingActionDto) {
        PartnerDao partnerDao = partnerRepository.findById(bookingDao.getPartnerId());

        if (partnerDao != null && partnerDao.getBookingId() != null
                && partnerDao.getBookingId().equals(bookingDao.getId())
                && PartnerStatus.ON_BOOKING.name().equals(partnerDao.getStatus())) {
            partnerDao.setBookingId(null);
            partnerDao.setBookingDetails(null);
            partnerDao.setSubStatus(PartnerSubStatus.NO_BOOKING.name());
            partnerDao.setStatus(PartnerStatus.ONLINE.name());
            partnerDao.setBookingId(null);
            partnerDao.setBookingDetails(null);
            partnerRepository.update(partnerDao);
        }

        bookingDao.setPartnerId(null);
        bookingDao.setPartnerDetails(null);
        bookingDao.setSubStatus(BookingSubStatus.UNASSIGNED.name());
        bookingDao.setVehicleDetails(null);
        bookingDao.setVehicleId(null);

        BookingViewDto viewDto = BookingViewDto.fromDao(bookingDao);
        if (viewDto != null) {
            viewDto.setStatus(bookingDao.getStatus());
            viewDto.setSubStatus(bookingDao.getSubStatus());
            viewDto.setPartnerDetails(null);
            viewDto.setVehicleDetails(null);
        }
        bookingDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
        return bookingRepository.update(bookingDao);

    }

    private BookingDao changePartner(BookingDao bookingDao, BookingActionDto bookingActionDto) {
        bookingDao = unassignPartner(bookingDao, bookingActionDto);
        return assignPartner(bookingDao, bookingActionDto);
    }

    private BookingDao assignPartner(BookingDao bookingDao, BookingActionDto bookingActionDto) {

        PartnerDao partnerDao = partnerRepository.findByUUID(bookingActionDto.getPartnerUUID());
        if (partnerDao == null)
            throw new ResponseException("Invalid partner for id : " + bookingActionDto.getPartnerUUID());
        if (!(PartnerStatus.ONLINE.name().equals(partnerDao.getStatus()) && PartnerSubStatus.NO_BOOKING.name().equals(partnerDao.getSubStatus()))) {
            throw new ResponseException("Partner should be in online state.");
        }
        VehicleDao vehicleDao = vehicleRepository.findById(partnerDao.getVehicleId());
        bookingDao.setStatus(BookingStatus.IN_PROGRESS.name());
        bookingDao.setSubStatus(BookingSubStatus.ASSIGNED.name());
        bookingDao.setPartnerId(partnerDao.getId());
        bookingDao.setVehicleId(vehicleDao.getId());
        bookingDao.setPartnerDetails(ApplicationConstants.GSON.toJson(PartnerViewDto.fromDao(partnerDao)));
        bookingDao.setVehicleDetails(ApplicationConstants.GSON.toJson(VehicleViewDto.fromDao(vehicleDao)));


        BookingViewDto viewDto = BookingViewDto.fromDao(bookingDao);
        if (viewDto != null) {
            viewDto.setStatus(bookingDao.getStatus());
            viewDto.setSubStatus(bookingDao.getSubStatus());
            viewDto.setPartnerDetails(PartnerViewDto.fromDao(partnerDao));
            viewDto.setVehicleDetails(VehicleViewDto.fromDao(vehicleDao));
        }
        bookingDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
        bookingDao = bookingRepository.update(bookingDao);
        partnerDao.setStatus(PartnerStatus.ON_BOOKING.name());
        partnerDao.setBookingId(bookingDao.getId());
        partnerDao.setSubStatus(PartnerSubStatus.ASSIGNED.name());
        partnerDao.setBookingDetails(ApplicationConstants.GSON.toJson(viewDto));
        partnerRepository.update(partnerDao);
        return bookingDao;

    }

    private BookingDao cancelBooking(BookingDao bookingDao, BookingActionDto bookingActionDto) {

        if (BookingStatus.COMPLETED.name().equals(bookingDao.getStatus()))
            throw new ResponseException("Booking already completed");

        bookingDao.setStatus(BookingStatus.CANCELLED.name());
        bookingDao.setSubStatus(BookingStatus.CANCELLED.name());

        BookingViewDto viewDto = BookingViewDto.fromDao(bookingDao);
        if (viewDto != null) {
            viewDto.setStatus(bookingDao.getStatus());
            viewDto.setSubStatus(bookingDao.getSubStatus());
        }
        bookingDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
        bookingDao = bookingRepository.update(bookingDao);

        if (bookingDao.getPartnerId() != null) {
            PartnerDao partnerDao = partnerRepository.findById(bookingDao.getPartnerId());
            if (partnerDao.getBookingId() != null && partnerDao.getBookingId().equals(bookingDao.getId()) && PartnerStatus.ON_BOOKING.name().equals(partnerDao.getStatus())) {
                partnerDao.setBookingDetails(null);
                partnerDao.setBookingId(null);
                partnerDao.setStatus(PartnerStatus.ONLINE.name());
                partnerDao.setSubStatus(PartnerSubStatus.NO_BOOKING.name());
                partnerDao.setBookingId(null);
                partnerDao.setBookingDetails(null);
                partnerRepository.update(partnerDao);
            }
        }
        return bookingDao;
    }

    @Override
    public BookingDto getBookingDetails(String bookingUUID) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        return getBookingDto(bookingDao);
    }

    @Override
    public Boolean deleteBooking(String bookingUUID) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        bookingRepository.delete(bookingDao.getId());
        return true;
    }
}
