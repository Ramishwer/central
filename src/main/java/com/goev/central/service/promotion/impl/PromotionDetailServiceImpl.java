package com.goev.central.service.promotion.impl;

import com.goev.central.dao.promotion.PromotionDetailDao;
import com.goev.central.dto.promotion.PromotionDetailDto;
import com.goev.central.repository.promotion.PromotionDetailRepository;
import com.goev.central.service.promotion.PromotionDetailService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PromotionDetailServiceImpl implements PromotionDetailService {

    private final PromotionDetailRepository promotionDetailRepository;


    @Override
    public PromotionDetailDto createPromotionDetail(PromotionDetailDto promotionDetailDto) {

        PromotionDetailDao promotionDetailDao = new PromotionDetailDao();
        promotionDetailDao = promotionDetailRepository.save(promotionDetailDao);
        if (promotionDetailDao == null)
            throw new ResponseException("Error in saving promotionDetail promotionDetail");
        return PromotionDetailDto.builder()
                .uuid(promotionDetailDao.getUuid()).build();
    }


    @Override
    public PromotionDetailDto getPromotionDetailDetails(String promotionDetailUUID) {
        PromotionDetailDao promotionDetailDao = promotionDetailRepository.findByUUID(promotionDetailUUID);
        if (promotionDetailDao == null)
            throw new ResponseException("No promotionDetail  found for Id :" + promotionDetailUUID);
        return PromotionDetailDto.builder()
                .uuid(promotionDetailDao.getUuid()).build();
    }

}
