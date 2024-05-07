package com.goev.central.service.pricing.impl;

import com.goev.central.dao.pricing.PricingElementDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.pricing.PricingElementDto;
import com.goev.central.repository.pricing.PricingElementRepository;
import com.goev.central.service.pricing.PricingElementService;
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
public class PricingElementServiceImpl implements PricingElementService {

    private final PricingElementRepository pricingElementRepository;

    @Override
    public PaginatedResponseDto<PricingElementDto> getPricingElements() {
        PaginatedResponseDto<PricingElementDto> result = PaginatedResponseDto.<PricingElementDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PricingElementDao> pricingElementDaos = pricingElementRepository.findAll();
        if (CollectionUtils.isEmpty(pricingElementDaos))
            return result;

        for (PricingElementDao pricingElementDao : pricingElementDaos) {
            result.getElements().add(PricingElementDto.builder()
                    .uuid(pricingElementDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PricingElementDto createPricingElement(PricingElementDto pricingElementDto) {

        PricingElementDao pricingElementDao = new PricingElementDao();
        pricingElementDao = pricingElementRepository.save(pricingElementDao);
        if (pricingElementDao == null)
            throw new ResponseException("Error in saving pricingElement pricingElement");
        return PricingElementDto.builder()
                .uuid(pricingElementDao.getUuid()).build();
    }

    @Override
    public PricingElementDto updatePricingElement(String pricingElementUUID, PricingElementDto pricingElementDto) {
        PricingElementDao pricingElementDao = pricingElementRepository.findByUUID(pricingElementUUID);
        if (pricingElementDao == null)
            throw new ResponseException("No pricingElement  found for Id :" + pricingElementUUID);
        PricingElementDao newPricingElementDao = new PricingElementDao();
       

        newPricingElementDao.setId(pricingElementDao.getId());
        newPricingElementDao.setUuid(pricingElementDao.getUuid());
        pricingElementDao = pricingElementRepository.update(newPricingElementDao);
        if (pricingElementDao == null)
            throw new ResponseException("Error in updating details pricingElement ");
        return PricingElementDto.builder()
                .uuid(pricingElementDao.getUuid()).build();
    }

    @Override
    public PricingElementDto getPricingElementDetails(String pricingElementUUID) {
        PricingElementDao pricingElementDao = pricingElementRepository.findByUUID(pricingElementUUID);
        if (pricingElementDao == null)
            throw new ResponseException("No pricingElement  found for Id :" + pricingElementUUID);
        return PricingElementDto.builder()
                .uuid(pricingElementDao.getUuid()).build();
    }

    @Override
    public Boolean deletePricingElement(String pricingElementUUID) {
        PricingElementDao pricingElementDao = pricingElementRepository.findByUUID(pricingElementUUID);
        if (pricingElementDao == null)
            throw new ResponseException("No pricingElement  found for Id :" + pricingElementUUID);
        pricingElementRepository.delete(pricingElementDao.getId());
        return true;
    }
}
