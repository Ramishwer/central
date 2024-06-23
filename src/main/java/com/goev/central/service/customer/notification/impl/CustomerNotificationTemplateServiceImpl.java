package com.goev.central.service.customer.notification.impl;

import com.goev.central.dao.customer.notification.CustomerNotificationTemplateDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.notification.CustomerNotificationTemplateDto;
import com.goev.central.repository.customer.notification.CustomerNotificationTemplateRepository;
import com.goev.central.service.customer.notification.CustomerNotificationTemplateService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerNotificationTemplateServiceImpl implements CustomerNotificationTemplateService {

    private final CustomerNotificationTemplateRepository customerNotificationTemplateRepository;

    @Override
    public PaginatedResponseDto<CustomerNotificationTemplateDto> getCustomerNotificationTemplates() {
        PaginatedResponseDto<CustomerNotificationTemplateDto> result = PaginatedResponseDto.<CustomerNotificationTemplateDto>builder().elements(new ArrayList<>()).build();
        List<CustomerNotificationTemplateDao> customerNotificationTemplateDaos = customerNotificationTemplateRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerNotificationTemplateDaos))
            return result;

        for (CustomerNotificationTemplateDao customerNotificationTemplateDao : customerNotificationTemplateDaos) {
            result.getElements().add(CustomerNotificationTemplateDto.builder()
                    .uuid(customerNotificationTemplateDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerNotificationTemplateDto createCustomerNotificationTemplate(CustomerNotificationTemplateDto customerNotificationTemplateDto) {

        CustomerNotificationTemplateDao customerNotificationTemplateDao = new CustomerNotificationTemplateDao();
        customerNotificationTemplateDao = customerNotificationTemplateRepository.save(customerNotificationTemplateDao);
        if (customerNotificationTemplateDao == null)
            throw new ResponseException("Error in saving customerNotificationTemplate customerNotificationTemplate");
        return CustomerNotificationTemplateDto.builder()
                .uuid(customerNotificationTemplateDao.getUuid()).build();
    }

    @Override
    public CustomerNotificationTemplateDto updateCustomerNotificationTemplate(String customerNotificationTemplateUUID, CustomerNotificationTemplateDto customerNotificationTemplateDto) {
        CustomerNotificationTemplateDao customerNotificationTemplateDao = customerNotificationTemplateRepository.findByUUID(customerNotificationTemplateUUID);
        if (customerNotificationTemplateDao == null)
            throw new ResponseException("No customerNotificationTemplate  found for Id :" + customerNotificationTemplateUUID);
        CustomerNotificationTemplateDao newCustomerNotificationTemplateDao = new CustomerNotificationTemplateDao();


        newCustomerNotificationTemplateDao.setId(customerNotificationTemplateDao.getId());
        newCustomerNotificationTemplateDao.setUuid(customerNotificationTemplateDao.getUuid());
        customerNotificationTemplateDao = customerNotificationTemplateRepository.update(newCustomerNotificationTemplateDao);
        if (customerNotificationTemplateDao == null)
            throw new ResponseException("Error in updating details customerNotificationTemplate ");
        return CustomerNotificationTemplateDto.builder()
                .uuid(customerNotificationTemplateDao.getUuid()).build();
    }

    @Override
    public CustomerNotificationTemplateDto getCustomerNotificationTemplateDetails(String customerNotificationTemplateUUID) {
        CustomerNotificationTemplateDao customerNotificationTemplateDao = customerNotificationTemplateRepository.findByUUID(customerNotificationTemplateUUID);
        if (customerNotificationTemplateDao == null)
            throw new ResponseException("No customerNotificationTemplate  found for Id :" + customerNotificationTemplateUUID);
        return CustomerNotificationTemplateDto.builder()
                .uuid(customerNotificationTemplateDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerNotificationTemplate(String customerNotificationTemplateUUID) {
        CustomerNotificationTemplateDao customerNotificationTemplateDao = customerNotificationTemplateRepository.findByUUID(customerNotificationTemplateUUID);
        if (customerNotificationTemplateDao == null)
            throw new ResponseException("No customerNotificationTemplate  found for Id :" + customerNotificationTemplateUUID);
        customerNotificationTemplateRepository.delete(customerNotificationTemplateDao.getId());
        return true;
    }
}
