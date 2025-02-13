package com.goev.central.service.customer.detail.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.central.service.customer.detail.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public PaginatedResponseDto<CustomerViewDto> getCustomers(String onboardingStatus) {
        PaginatedResponseDto<CustomerViewDto> result = PaginatedResponseDto.<CustomerViewDto>builder().elements(new ArrayList<>()).build();
        List<CustomerDao> customerDaos = customerRepository.findAllActive(onboardingStatus);
        if (CollectionUtils.isEmpty(customerDaos))
            return result;

        for (CustomerDao customerDao : customerDaos) {
            CustomerViewDto customerViewDto = ApplicationConstants.GSON.fromJson(customerDao.getViewInfo(), CustomerViewDto.class);
            if (customerViewDto == null)
                continue;
            customerViewDto.setUuid(customerViewDto.getUuid());
            result.getElements().add(customerViewDto);
        }
        return result;
    }


}
