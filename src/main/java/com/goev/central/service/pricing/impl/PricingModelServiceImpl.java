package com.goev.central.service.pricing.impl;

import com.goev.central.dao.pricing.PricingModelDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.pricing.PricingModelDto;
import com.goev.central.repository.pricing.PricingModelRepository;
import com.goev.central.service.pricing.PricingModelService;
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
public class PricingModelServiceImpl implements PricingModelService {

    private final PricingModelRepository pricingModelRepository;

    @Override
    public PaginatedResponseDto<PricingModelDto> getPricingModels() {
        PaginatedResponseDto<PricingModelDto> result = PaginatedResponseDto.<PricingModelDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PricingModelDao> pricingModelDaos = pricingModelRepository.findAllActive();
        if (CollectionUtils.isEmpty(pricingModelDaos))
            return result;

        for (PricingModelDao pricingModelDao : pricingModelDaos) {
            result.getElements().add(PricingModelDto.builder()
                    .uuid(pricingModelDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PricingModelDto createPricingModel(PricingModelDto pricingModelDto) {

        PricingModelDao pricingModelDao = new PricingModelDao();
        pricingModelDao = pricingModelRepository.save(pricingModelDao);
        if (pricingModelDao == null)
            throw new ResponseException("Error in saving pricingModel pricingModel");
        return PricingModelDto.builder()
                .uuid(pricingModelDao.getUuid()).build();
    }

    @Override
    public PricingModelDto updatePricingModel(String pricingModelUUID, PricingModelDto pricingModelDto) {
        PricingModelDao pricingModelDao = pricingModelRepository.findByUUID(pricingModelUUID);
        if (pricingModelDao == null)
            throw new ResponseException("No pricingModel  found for Id :" + pricingModelUUID);
        PricingModelDao newPricingModelDao = new PricingModelDao();


        newPricingModelDao.setId(pricingModelDao.getId());
        newPricingModelDao.setUuid(pricingModelDao.getUuid());
        pricingModelDao = pricingModelRepository.update(newPricingModelDao);
        if (pricingModelDao == null)
            throw new ResponseException("Error in updating details pricingModel ");
        return PricingModelDto.builder()
                .uuid(pricingModelDao.getUuid()).build();
    }

    @Override
    public PricingModelDto getPricingModelDetails(String pricingModelUUID) {
        PricingModelDao pricingModelDao = pricingModelRepository.findByUUID(pricingModelUUID);
        if (pricingModelDao == null)
            throw new ResponseException("No pricingModel  found for Id :" + pricingModelUUID);
        return PricingModelDto.builder()
                .uuid(pricingModelDao.getUuid()).build();
    }

    @Override
    public Boolean deletePricingModel(String pricingModelUUID) {
        PricingModelDao pricingModelDao = pricingModelRepository.findByUUID(pricingModelUUID);
        if (pricingModelDao == null)
            throw new ResponseException("No pricingModel  found for Id :" + pricingModelUUID);
        pricingModelRepository.delete(pricingModelDao.getId());
        return true;
    }
}
