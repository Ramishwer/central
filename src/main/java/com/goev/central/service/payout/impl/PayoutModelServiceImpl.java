package com.goev.central.service.payout.impl;

import com.goev.central.dao.payout.PayoutModelDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutModelDto;
import com.goev.central.repository.payout.PayoutModelRepository;
import com.goev.central.service.payout.PayoutModelService;
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
public class PayoutModelServiceImpl implements PayoutModelService {

    private final PayoutModelRepository payoutModelRepository;

    @Override
    public PaginatedResponseDto<PayoutModelDto> getPayoutModels() {
        PaginatedResponseDto<PayoutModelDto> result = PaginatedResponseDto.<PayoutModelDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PayoutModelDao> payoutModelDaos = payoutModelRepository.findAllActive();
        if (CollectionUtils.isEmpty(payoutModelDaos))
            return result;

        for (PayoutModelDao payoutModelDao : payoutModelDaos) {
            result.getElements().add(PayoutModelDto.builder()
                    .uuid(payoutModelDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PayoutModelDto createPayoutModel(PayoutModelDto payoutModelDto) {

        PayoutModelDao payoutModelDao = new PayoutModelDao();
        payoutModelDao = payoutModelRepository.save(payoutModelDao);
        if (payoutModelDao == null)
            throw new ResponseException("Error in saving payoutModel payoutModel");
        return PayoutModelDto.builder()
                .uuid(payoutModelDao.getUuid()).build();
    }

    @Override
    public PayoutModelDto updatePayoutModel(String payoutModelUUID, PayoutModelDto payoutModelDto) {
        PayoutModelDao payoutModelDao = payoutModelRepository.findByUUID(payoutModelUUID);
        if (payoutModelDao == null)
            throw new ResponseException("No payoutModel  found for Id :" + payoutModelUUID);
        PayoutModelDao newPayoutModelDao = new PayoutModelDao();


        newPayoutModelDao.setId(payoutModelDao.getId());
        newPayoutModelDao.setUuid(payoutModelDao.getUuid());
        payoutModelDao = payoutModelRepository.update(newPayoutModelDao);
        if (payoutModelDao == null)
            throw new ResponseException("Error in updating details payoutModel ");
        return PayoutModelDto.builder()
                .uuid(payoutModelDao.getUuid()).build();
    }

    @Override
    public PayoutModelDto getPayoutModelDetails(String payoutModelUUID) {
        PayoutModelDao payoutModelDao = payoutModelRepository.findByUUID(payoutModelUUID);
        if (payoutModelDao == null)
            throw new ResponseException("No payoutModel  found for Id :" + payoutModelUUID);
        return PayoutModelDto.builder()
                .uuid(payoutModelDao.getUuid()).build();
    }

    @Override
    public Boolean deletePayoutModel(String payoutModelUUID) {
        PayoutModelDao payoutModelDao = payoutModelRepository.findByUUID(payoutModelUUID);
        if (payoutModelDao == null)
            throw new ResponseException("No payoutModel  found for Id :" + payoutModelUUID);
        payoutModelRepository.delete(payoutModelDao.getId());
        return true;
    }
}
