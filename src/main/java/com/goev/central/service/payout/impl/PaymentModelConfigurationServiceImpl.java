package com.goev.central.service.payout.impl;

import com.goev.central.dao.payout.PayoutModelConfigurationDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutModelConfigurationDto;
import com.goev.central.repository.payout.PayoutModelConfigurationRepository;
import com.goev.central.service.payout.PayoutModelConfigurationService;
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
public class PaymentModelConfigurationServiceImpl implements PayoutModelConfigurationService {

    private final PayoutModelConfigurationRepository payoutModelConfigurationRepository;

    @Override
    public PaginatedResponseDto<PayoutModelConfigurationDto> getPayoutModelConfigurations(String customerUUID) {
        PaginatedResponseDto<PayoutModelConfigurationDto> result = PaginatedResponseDto.<PayoutModelConfigurationDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PayoutModelConfigurationDao> payoutModelConfigurationDaos = payoutModelConfigurationRepository.findAllActive();
        if (CollectionUtils.isEmpty(payoutModelConfigurationDaos))
            return result;

        for (PayoutModelConfigurationDao payoutModelConfigurationDao : payoutModelConfigurationDaos) {
            result.getElements().add(PayoutModelConfigurationDto.builder()
                    .uuid(payoutModelConfigurationDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PayoutModelConfigurationDto createPayoutModelConfiguration(String customerUUID, PayoutModelConfigurationDto payoutModelConfigurationDto) {

        PayoutModelConfigurationDao payoutModelConfigurationDao = new PayoutModelConfigurationDao();
        payoutModelConfigurationDao = payoutModelConfigurationRepository.save(payoutModelConfigurationDao);
        if (payoutModelConfigurationDao == null)
            throw new ResponseException("Error in saving payoutModelConfiguration payoutModelConfiguration");
        return PayoutModelConfigurationDto.builder()
                .uuid(payoutModelConfigurationDao.getUuid()).build();
    }

    @Override
    public PayoutModelConfigurationDto updatePayoutModelConfiguration(String customerUUID, String payoutModelConfigurationUUID, PayoutModelConfigurationDto payoutModelConfigurationDto) {
        PayoutModelConfigurationDao payoutModelConfigurationDao = payoutModelConfigurationRepository.findByUUID(payoutModelConfigurationUUID);
        if (payoutModelConfigurationDao == null)
            throw new ResponseException("No payoutModelConfiguration  found for Id :" + payoutModelConfigurationUUID);
        PayoutModelConfigurationDao newPayoutModelConfigurationDao = new PayoutModelConfigurationDao();


        newPayoutModelConfigurationDao.setId(payoutModelConfigurationDao.getId());
        newPayoutModelConfigurationDao.setUuid(payoutModelConfigurationDao.getUuid());
        payoutModelConfigurationDao = payoutModelConfigurationRepository.update(newPayoutModelConfigurationDao);
        if (payoutModelConfigurationDao == null)
            throw new ResponseException("Error in updating details payoutModelConfiguration ");
        return PayoutModelConfigurationDto.builder()
                .uuid(payoutModelConfigurationDao.getUuid()).build();
    }

    @Override
    public PayoutModelConfigurationDto getPayoutModelConfigurationDetails(String customerUUID, String payoutModelConfigurationUUID) {
        PayoutModelConfigurationDao payoutModelConfigurationDao = payoutModelConfigurationRepository.findByUUID(payoutModelConfigurationUUID);
        if (payoutModelConfigurationDao == null)
            throw new ResponseException("No payoutModelConfiguration  found for Id :" + payoutModelConfigurationUUID);
        return PayoutModelConfigurationDto.builder()
                .uuid(payoutModelConfigurationDao.getUuid()).build();
    }

    @Override
    public Boolean deletePayoutModelConfiguration(String customerUUID, String payoutModelConfigurationUUID) {
        PayoutModelConfigurationDao payoutModelConfigurationDao = payoutModelConfigurationRepository.findByUUID(payoutModelConfigurationUUID);
        if (payoutModelConfigurationDao == null)
            throw new ResponseException("No payoutModelConfiguration  found for Id :" + payoutModelConfigurationUUID);
        payoutModelConfigurationRepository.delete(payoutModelConfigurationDao.getId());
        return true;
    }
}
