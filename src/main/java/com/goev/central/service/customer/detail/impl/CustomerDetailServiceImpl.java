package com.goev.central.service.customer.detail.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dao.customer.detail.CustomerDetailDao;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDetailDto;
import com.goev.central.enums.customer.CustomerOnboardingStatus;
import com.goev.central.repository.customer.detail.CustomerDetailRepository;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.central.service.customer.detail.CustomerDetailService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerDetailServiceImpl implements CustomerDetailService {

    private final CustomerRepository customerRepository;
    private final CustomerDetailRepository customerDetailRepository;

    @Override
    public CustomerDetailDto createCustomer(CustomerDetailDto customerDto) {

        CustomerDao existingPartner = customerRepository.findByPhoneNumber(customerDto.getPhoneNumber());

        if (existingPartner != null) {
            throw new ResponseException("Error in saving customer: Partner with Phone Number :" + customerDto.getPhoneNumber() + " already exist");
        }
        CustomerDao customerDao = new CustomerDao();
        customerDao.setPhoneNumber(customerDto.getPhoneNumber());
        customerDao.setStatus(CustomerOnboardingStatus.ONBOARDED.name());
        CustomerDao customer = customerRepository.save(customerDao);

        if (customer == null)
            throw new ResponseException("Error in saving details");

        CustomerDetailDao customerDetailDao = getCustomerDetailDao(customerDto);
        customerDetailDao.setCustomerId(customer.getId());
        customerDetailDao = customerDetailRepository.save(customerDetailDao);
        if (customerDetailDao == null)
            throw new ResponseException("Error in saving customer details");

        customer.setCustomerDetailsId(customerDetailDao.getId());
        customer.setViewInfo(ApplicationConstants.GSON.toJson(getCustomerViewDto(customerDetailDao, customer)));
        customerRepository.update(customer);
        return getCustomerDetailDto(customerDetailDao, customerDao);
    }

    private CustomerDetailDao getCustomerDetailDao(CustomerDetailDto customerDto) {
        CustomerDetailDao customerDetailDao = new CustomerDetailDao();
        customerDetailDao.setPhoneNumber(customerDto.getPhoneNumber());
        customerDetailDao.setEmail(customerDto.getEmail());
        customerDetailDao.setFirstName(customerDto.getFirstName());
        customerDetailDao.setLastName(customerDto.getLastName());
        customerDetailDao.setPreferredLanguage(customerDto.getPreferredLanguage());
        return customerDetailDao;
    }

    private CustomerViewDto getCustomerViewDto(CustomerDetailDao customerDetailDao, CustomerDao customerDao) {
        return CustomerViewDto.builder()
                .firstName(customerDetailDao.getFirstName())
                .lastName(customerDetailDao.getLastName())
                .profileUrl(customerDao.getProfileUrl())
                .phoneNumber(customerDao.getPhoneNumber())
                .email(customerDetailDao.getEmail())
                .status(customerDao.getStatus())
                .uuid(customerDao.getUuid())
                .preferredLanguage(customerDetailDao.getPreferredLanguage())
                .build();
    }

    @Override
    public CustomerDetailDto getCustomerDetails(String customerUUID) {
        CustomerDao customerDao = customerRepository.findByUUID(customerUUID);
        if (customerDao == null)
            throw new ResponseException("No customer  found for Id :" + customerUUID);
        CustomerDetailDao customerDetailDao = customerDetailRepository.findById(customerDao.getCustomerDetailsId());

        if (customerDetailDao == null)
            throw new ResponseException("No customer details found for Id :" + customerUUID);


        return getCustomerDetailDto(customerDetailDao, customerDao);
    }

    private CustomerDetailDto getCustomerDetailDto(CustomerDetailDao customerDetailDao, CustomerDao customerDao) {
        return CustomerDetailDto.builder()
                .uuid(customerDao.getUuid())
                .email(customerDetailDao.getEmail())
                .firstName(customerDetailDao.getFirstName())
                .lastName(customerDetailDao.getLastName())
                .preferredLanguage(customerDetailDao.getPreferredLanguage())
                .phoneNumber(customerDetailDao.getPhoneNumber())
                .customer(getCustomerViewDto(customerDetailDao, customerDao))
                .build();
    }

}
