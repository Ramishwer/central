package com.goev.central.service.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppSupportedLanguageDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppSupportedLanguageDto;
import com.goev.central.repository.customer.app.CustomerAppSupportedLanguageRepository;
import com.goev.central.service.customer.app.CustomerAppSupportedLanguageService;
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
public class CustomerAppSupportedLanguageServiceImpl implements CustomerAppSupportedLanguageService {

    private final CustomerAppSupportedLanguageRepository customerAppSupportedLanguageRepository;

    @Override
    public PaginatedResponseDto<CustomerAppSupportedLanguageDto> getCustomerAppSupportedLanguages() {
        PaginatedResponseDto<CustomerAppSupportedLanguageDto> result = PaginatedResponseDto.<CustomerAppSupportedLanguageDto>builder().elements(new ArrayList<>()).build();
        List<CustomerAppSupportedLanguageDao> customerAppSupportedLanguageDaos = customerAppSupportedLanguageRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerAppSupportedLanguageDaos))
            return result;

        for (CustomerAppSupportedLanguageDao customerAppSupportedLanguageDao : customerAppSupportedLanguageDaos) {
            result.getElements().add(CustomerAppSupportedLanguageDto.builder()
                    .uuid(customerAppSupportedLanguageDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerAppSupportedLanguageDto createCustomerAppSupportedLanguage(CustomerAppSupportedLanguageDto customerAppSupportedLanguageDto) {

        CustomerAppSupportedLanguageDao customerAppSupportedLanguageDao = new CustomerAppSupportedLanguageDao();
        customerAppSupportedLanguageDao = customerAppSupportedLanguageRepository.save(customerAppSupportedLanguageDao);
        if (customerAppSupportedLanguageDao == null)
            throw new ResponseException("Error in saving customerAppSupportedLanguage customerAppSupportedLanguage");
        return CustomerAppSupportedLanguageDto.builder()
                .uuid(customerAppSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public CustomerAppSupportedLanguageDto updateCustomerAppSupportedLanguage(String customerAppSupportedLanguageUUID, CustomerAppSupportedLanguageDto customerAppSupportedLanguageDto) {
        CustomerAppSupportedLanguageDao customerAppSupportedLanguageDao = customerAppSupportedLanguageRepository.findByUUID(customerAppSupportedLanguageUUID);
        if (customerAppSupportedLanguageDao == null)
            throw new ResponseException("No customerAppSupportedLanguage  found for Id :" + customerAppSupportedLanguageUUID);
        CustomerAppSupportedLanguageDao newCustomerAppSupportedLanguageDao = new CustomerAppSupportedLanguageDao();


        newCustomerAppSupportedLanguageDao.setId(customerAppSupportedLanguageDao.getId());
        newCustomerAppSupportedLanguageDao.setUuid(customerAppSupportedLanguageDao.getUuid());
        customerAppSupportedLanguageDao = customerAppSupportedLanguageRepository.update(newCustomerAppSupportedLanguageDao);
        if (customerAppSupportedLanguageDao == null)
            throw new ResponseException("Error in updating details customerAppSupportedLanguage ");
        return CustomerAppSupportedLanguageDto.builder()
                .uuid(customerAppSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public CustomerAppSupportedLanguageDto getCustomerAppSupportedLanguageDetails(String customerAppSupportedLanguageUUID) {
        CustomerAppSupportedLanguageDao customerAppSupportedLanguageDao = customerAppSupportedLanguageRepository.findByUUID(customerAppSupportedLanguageUUID);
        if (customerAppSupportedLanguageDao == null)
            throw new ResponseException("No customerAppSupportedLanguage  found for Id :" + customerAppSupportedLanguageUUID);
        return CustomerAppSupportedLanguageDto.builder()
                .uuid(customerAppSupportedLanguageDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerAppSupportedLanguage(String customerAppSupportedLanguageUUID) {
        CustomerAppSupportedLanguageDao customerAppSupportedLanguageDao = customerAppSupportedLanguageRepository.findByUUID(customerAppSupportedLanguageUUID);
        if (customerAppSupportedLanguageDao == null)
            throw new ResponseException("No customerAppSupportedLanguage  found for Id :" + customerAppSupportedLanguageUUID);
        customerAppSupportedLanguageRepository.delete(customerAppSupportedLanguageDao.getId());
        return true;
    }
}
