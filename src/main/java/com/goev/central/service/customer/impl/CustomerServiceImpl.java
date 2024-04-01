//package com.goev.central.service.impl;
//
//
//import com.goev.central.dao.customer.detail.CustomerDao;
//import com.goev.central.dto.common.PaginatedResponseDto;
//import com.goev.central.dto.customer.detail.CustomerDetailsDto;
//import com.goev.central.dto.customer.detail.CustomerDto;
//import com.goev.central.repository.customer.detail.CustomerRepository;
//import com.goev.central.service.customer.CustomerService;
//import com.goev.lib.exceptions.ResponseException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Slf4j
//@Service

//import lombok.AllArgsConstructor;

//@AllArgsConstructor
//public class CustomerServiceImpl  implements CustomerService {
//
//
//    private final CustomerRepository customerRepository;
//    @Override
//    public CustomerDetailsDto createCustomer(CustomerDetailsDto customerDto) {
//        CustomerDao customer = customerRepository.save(new CustomerDao().fromDto(customerDto.getDetails()));
//        if(customer == null)
//            throw new ResponseException("Error in saving details");
//        return CustomerDetailsDto.builder().details(customer.toDto()).uuid(customer.getUuid()).build();
//    }
//
//    @Override
//    public CustomerDetailsDto updateCustomer(String customerUUID, CustomerDetailsDto customerDto) {
//        CustomerDao customer = customerRepository.findByUUID(customerUUID);
//        if(customer == null)
//            throw new ResponseException("No customer found for Id :"+customerUUID);
//        CustomerDao newCustomer = customer.fromDto(customerDto.getDetails());
//        newCustomer.setId(customer.getId());
//        newCustomer.setUuid(customer.getUuid());
//        customer =customerRepository.update(newCustomer);
//        if(customer == null)
//            throw new ResponseException("Error in saving details");
//        return CustomerDetailsDto.builder().details(customer.toDto()).uuid(customer.getUuid()).build();
//    }
//
//    @Override
//    public CustomerDetailsDto getCustomerDetails(String customerUUID) {
//        CustomerDao customer = customerRepository.findByUUID(customerUUID);
//        if(customer == null)
//            throw new ResponseException("No customer found for Id :"+customerUUID);
//        return CustomerDetailsDto.builder().details(customer.toDto()).uuid(customer.getUuid()).build();
//    }
//
//    @Override
//    public Boolean deleteCustomer(String customerUUID) {
//        CustomerDao customer = customerRepository.findByUUID(customerUUID);
//        if(customer == null)
//            throw new ResponseException("No customer found for Id :"+customerUUID);
//
//        customerRepository.delete(customer.getId());
//        return true;
//    }
//
//    @Override
//    public PaginatedResponseDto<CustomerDto> getCustomers() {
//
//        PaginatedResponseDto<CustomerDto> result = PaginatedResponseDto.<CustomerDto>builder().elements(new ArrayList<>()).build();
//        customerRepository.findAll().forEach(x->{
//            result.getElements().add(x.toDto());
//        });
//        return result;
//    }
//}
