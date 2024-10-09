package com.goev.central.service.booking.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingScheduleConfigurationDao;
import com.goev.central.dao.booking.BookingScheduleDao;
import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dao.business.BusinessClientDetailDao;
import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.BookingScheduleDto;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.enums.EntityType;
import com.goev.central.enums.booking.BookingScheduleStatus;
import com.goev.central.enums.customer.CustomerOnboardingStatus;
import com.goev.central.repository.booking.BookingScheduleConfigurationRepository;
import com.goev.central.repository.booking.BookingScheduleRepository;
import com.goev.central.repository.business.BusinessClientDetailRepository;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.central.service.booking.BookingScheduleService;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.dto.ContactDetailsDto;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class BookingScheduleServiceImpl implements BookingScheduleService {

    private final BookingScheduleRepository bookingScheduleRepository;
    private final BookingScheduleConfigurationRepository bookingScheduleConfigurationRepository;
    private final CustomerRepository customerRepository;
    private final BusinessClientRepository businessClientRepository;
    private final BusinessClientDetailRepository businessClientDetailRepository;

    @Override
    public PaginatedResponseDto<BookingScheduleDto> getBookingSchedules(String status, String subStatus) {
        PaginatedResponseDto<BookingScheduleDto> result = PaginatedResponseDto.<BookingScheduleDto>builder().elements(new ArrayList<>()).build();
        List<BookingScheduleDao> bookingScheduleDaos = bookingScheduleRepository.findAllActive(status, subStatus);
        if (CollectionUtils.isEmpty(bookingScheduleDaos))
            return result;

        for (BookingScheduleDao bookingScheduleDao : bookingScheduleDaos) {
            result.getElements().add(getBookingScheduleDto(bookingScheduleDao));
        }
        return result;
    }

    @Override
    public BookingScheduleDto createBookingSchedule(BookingRequestDto bookingRequestDto) {

        if(bookingRequestDto.getCustomerDetails().getPhoneNumber()==null)
            throw new ResponseException("Invalid Customer Details");
        CustomerDao customer = customerRepository.findByPhoneNumber(bookingRequestDto.getCustomerDetails().getPhoneNumber());

        if(customer == null){
            customer = new CustomerDao();
            customer.setPhoneNumber(customer.getPhoneNumber());
            customer.setStatus(CustomerOnboardingStatus.ONBOARDED.name());
            customer = customerRepository.save(customer);
            bookingRequestDto.getCustomerDetails().setUuid(customer.getUuid());
        }

        bookingRequestDto.setStartContact(ContactDetailsDto.builder()
                .firstName(bookingRequestDto.getCustomerDetails().getFirstName())
                .lastName(bookingRequestDto.getCustomerDetails().getLastName())
                .phoneNumber(bookingRequestDto.getCustomerDetails().getPhoneNumber()).build());
        bookingRequestDto.setEndContact(bookingRequestDto.getStartContact());
        BookingScheduleDao bookingScheduleDao = new BookingScheduleDao();

        bookingScheduleDao.setCustomerId(customer.getId());
        bookingScheduleDao.setStatus(BookingScheduleStatus.CONFIRMED.name());
        bookingScheduleDao.setEntityType(EntityType.CLIENT.name());
        bookingScheduleDao.setApplicableFromTime(bookingRequestDto.getScheduleDetails().getStartTime());
        bookingScheduleDao.setApplicableToTime(bookingRequestDto.getScheduleDetails().getEndTime());

        BusinessClientDao clientDao =businessClientRepository.findByUUID(bookingRequestDto.getBusinessClient().getUuid());
        bookingScheduleDao.setBusinessClientId(clientDao.getId());
        bookingRequestDto.setBusinessClient(BusinessClientDto.builder().uuid(clientDao.getUuid()).name(clientDao.getName()).build());

        BusinessClientDetailDao clientDetailDao = businessClientDetailRepository.findById(clientDao.getBusinessClientDetailsId());
        bookingScheduleDao.setBusinessSegmentId(clientDetailDao.getBusinessSegmentId());

        bookingScheduleDao.setDisplayCode("SCH-"+SecretGenerationUtils.getCode());
        bookingScheduleDao.setViewInfo(ApplicationConstants.GSON.toJson(bookingRequestDto));
        bookingScheduleDao = bookingScheduleRepository.save(bookingScheduleDao);



        for(Map.Entry<String,String> configEntry :bookingRequestDto.getScheduleDetails().getSchedule().entrySet()){
            String day = configEntry.getKey();
            String time = configEntry.getValue();
            BookingScheduleConfigurationDao bookingScheduleConfigurationDao = new BookingScheduleConfigurationDao();
            bookingScheduleConfigurationDao.setStatus(bookingScheduleDao.getStatus());
            bookingScheduleConfigurationDao.setDay(day);
            bookingScheduleConfigurationDao.setBookingScheduleId(bookingScheduleDao.getId());
            bookingScheduleConfigurationDao.setStart(time);

            bookingScheduleConfigurationDao.setBookingConfig(ApplicationConstants.GSON.toJson(bookingRequestDto));

            bookingScheduleConfigurationRepository.save(bookingScheduleConfigurationDao);

        }
        if (bookingScheduleDao == null)
            throw new ResponseException("Error in saving bookingSchedule");
        return getBookingScheduleDto(bookingScheduleDao);
    }

    private BookingScheduleDao getBookingScheduleDao(BookingScheduleDto bookingScheduleDto) {
        return BookingScheduleDao.fromDto(bookingScheduleDto);
    }

    private BookingScheduleDto getBookingScheduleDto(BookingScheduleDao bookingScheduleDao) {

        BookingRequestDto bookingRequestDto = ApplicationConstants.GSON.fromJson(bookingScheduleDao.getViewInfo(),BookingRequestDto.class);
        if(bookingRequestDto!=null){
            return BookingScheduleDto.builder()
                    .uuid(bookingScheduleDao.getUuid())
                    .bookingTypeDetails(bookingRequestDto.getBookingType())
                    .scheduleDetails(bookingRequestDto.getScheduleDetails())
                    .status(bookingScheduleDao.getStatus())
                    .subStatus(bookingScheduleDao.getSubStatus())
                    .startLocationDetails(bookingRequestDto.getStartLocationDetails())
                    .endLocationDetails(bookingRequestDto.getEndLocationDetails())
                    .startContact(bookingRequestDto.getStartContact())
                    .endContact(bookingRequestDto.getEndContact())
                    .businessClient(bookingRequestDto.getBusinessClient())
                    .businessSegment(bookingRequestDto.getBusinessSegment())
                    .customerDetails(bookingRequestDto.getCustomerDetails())
                    .displayCode(bookingScheduleDao.getDisplayCode())
                    .distance(bookingRequestDto.getDistance())
                    .duration(bookingRequestDto.getDuration())
                    .paymentDetails(bookingRequestDto.getPaymentDetails())
                    .scheduleDetails(bookingRequestDto.getScheduleDetails())
                    .build();
        }

        return null;
    }

    @Override
    public BookingScheduleDto updateBookingSchedule(String bookingScheduleUUID, BookingScheduleDto bookingScheduleDto) {
        BookingScheduleDao bookingScheduleDao = bookingScheduleRepository.findByUUID(bookingScheduleUUID);
        if (bookingScheduleDao == null)
            throw new ResponseException("No bookingSchedule  found for Id :" + bookingScheduleUUID);
        BookingScheduleDao newBookingScheduleDao = getBookingScheduleDao(bookingScheduleDto);

        newBookingScheduleDao.setId(bookingScheduleDao.getId());
        newBookingScheduleDao.setUuid(bookingScheduleDao.getUuid());
        bookingScheduleDao = bookingScheduleRepository.update(newBookingScheduleDao);
        if (bookingScheduleDao == null)
            throw new ResponseException("Error in updating details bookingSchedule ");
        return getBookingScheduleDto(bookingScheduleDao);
    }

    @Override
    public BookingScheduleDto getBookingScheduleDetails(String bookingScheduleUUID) {
        BookingScheduleDao bookingScheduleDao = bookingScheduleRepository.findByUUID(bookingScheduleUUID);
        if (bookingScheduleDao == null)
            throw new ResponseException("No bookingSchedule  found for Id :" + bookingScheduleUUID);
        return getBookingScheduleDto(bookingScheduleDao);
    }

    @Override
    public Boolean deleteBookingSchedule(String bookingScheduleUUID) {
        BookingScheduleDao bookingScheduleDao = bookingScheduleRepository.findByUUID(bookingScheduleUUID);
        if (bookingScheduleDao == null)
            throw new ResponseException("No bookingSchedule  found for Id :" + bookingScheduleUUID);
        bookingScheduleRepository.delete(bookingScheduleDao.getId());
        return true;
    }
}
