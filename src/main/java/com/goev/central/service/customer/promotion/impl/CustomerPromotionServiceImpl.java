package com.goev.central.service.customer.promotion.impl;

import com.goev.central.dao.customer.promotion.CustomerPromotionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.promotion.CustomerPromotionDto;
import com.goev.central.repository.customer.promotion.CustomerPromotionRepository;
import com.goev.central.service.customer.promotion.CustomerPromotionService;
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
public class CustomerPromotionServiceImpl implements CustomerPromotionService {

    private final CustomerPromotionRepository customerPromotionRepository;

    @Override
    public PaginatedResponseDto<CustomerPromotionDto> getCustomerPromotions(String customerUUID) {
        PaginatedResponseDto<CustomerPromotionDto> result = PaginatedResponseDto.<CustomerPromotionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerPromotionDao> customerPromotionDaos = customerPromotionRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerPromotionDaos))
            return result;

        for (CustomerPromotionDao customerPromotionDao : customerPromotionDaos) {
            result.getElements().add(CustomerPromotionDto.builder()
                    .uuid(customerPromotionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerPromotionDto createCustomerPromotion(String customerUUID, CustomerPromotionDto customerPromotionDto) {

        CustomerPromotionDao customerPromotionDao = new CustomerPromotionDao();
        customerPromotionDao = customerPromotionRepository.save(customerPromotionDao);
        if (customerPromotionDao == null)
            throw new ResponseException("Error in saving customerPromotion customerPromotion");
        return CustomerPromotionDto.builder()
                .uuid(customerPromotionDao.getUuid()).build();
    }

    @Override
    public CustomerPromotionDto getCustomerPromotionDetails(String customerUUID, String customerPromotionUUID) {
        CustomerPromotionDao customerPromotionDao = customerPromotionRepository.findByUUID(customerPromotionUUID);
        if (customerPromotionDao == null)
            throw new ResponseException("No customerPromotion  found for Id :" + customerPromotionUUID);
        return CustomerPromotionDto.builder()
                .uuid(customerPromotionDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerPromotion(String customerUUID, String customerPromotionUUID) {
        CustomerPromotionDao customerPromotionDao = customerPromotionRepository.findByUUID(customerPromotionUUID);
        if (customerPromotionDao == null)
            throw new ResponseException("No customerPromotion  found for Id :" + customerPromotionUUID);
        customerPromotionRepository.delete(customerPromotionDao.getId());
        return true;
    }
}
