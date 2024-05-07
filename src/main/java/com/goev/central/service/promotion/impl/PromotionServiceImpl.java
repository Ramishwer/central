package com.goev.central.service.promotion.impl;

import com.goev.central.dao.promotion.PromotionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.promotion.PromotionDto;
import com.goev.central.repository.promotion.PromotionRepository;
import com.goev.central.service.promotion.PromotionService;
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
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public PaginatedResponseDto<PromotionDto> getPromotions() {
        PaginatedResponseDto<PromotionDto> result = PaginatedResponseDto.<PromotionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PromotionDao> promotionDaos = promotionRepository.findAll();
        if (CollectionUtils.isEmpty(promotionDaos))
            return result;

        for (PromotionDao promotionDao : promotionDaos) {
            result.getElements().add(PromotionDto.builder()
                    .uuid(promotionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PromotionDto createPromotion(PromotionDto promotionDto) {

        PromotionDao promotionDao = new PromotionDao();
        promotionDao = promotionRepository.save(promotionDao);
        if (promotionDao == null)
            throw new ResponseException("Error in saving promotion promotion");
        return PromotionDto.builder()
                .uuid(promotionDao.getUuid()).build();
    }

    @Override
    public PromotionDto updatePromotion(String promotionUUID, PromotionDto promotionDto) {
        PromotionDao promotionDao = promotionRepository.findByUUID(promotionUUID);
        if (promotionDao == null)
            throw new ResponseException("No promotion  found for Id :" + promotionUUID);
        PromotionDao newPromotionDao = new PromotionDao();
       

        newPromotionDao.setId(promotionDao.getId());
        newPromotionDao.setUuid(promotionDao.getUuid());
        promotionDao = promotionRepository.update(newPromotionDao);
        if (promotionDao == null)
            throw new ResponseException("Error in updating details promotion ");
        return PromotionDto.builder()
                .uuid(promotionDao.getUuid()).build();
    }

    @Override
    public PromotionDto getPromotionDetails(String promotionUUID) {
        PromotionDao promotionDao = promotionRepository.findByUUID(promotionUUID);
        if (promotionDao == null)
            throw new ResponseException("No promotion  found for Id :" + promotionUUID);
        return PromotionDto.builder()
                .uuid(promotionDao.getUuid()).build();
    }

    @Override
    public Boolean deletePromotion(String promotionUUID) {
        PromotionDao promotionDao = promotionRepository.findByUUID(promotionUUID);
        if (promotionDao == null)
            throw new ResponseException("No promotion  found for Id :" + promotionUUID);
        promotionRepository.delete(promotionDao.getId());
        return true;
    }
}
