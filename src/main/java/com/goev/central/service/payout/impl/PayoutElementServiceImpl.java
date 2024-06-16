package com.goev.central.service.payout.impl;

import com.goev.central.dao.payout.PayoutElementDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutElementDto;
import com.goev.central.repository.payout.PayoutElementRepository;
import com.goev.central.service.payout.PayoutElementService;
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
public class PayoutElementServiceImpl implements PayoutElementService {

    private final PayoutElementRepository payoutElementRepository;

    @Override
    public PaginatedResponseDto<PayoutElementDto> getPayoutElements() {
        PaginatedResponseDto<PayoutElementDto> result = PaginatedResponseDto.<PayoutElementDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PayoutElementDao> payoutElementDaos = payoutElementRepository.findAllActive();
        if (CollectionUtils.isEmpty(payoutElementDaos))
            return result;

        for (PayoutElementDao payoutElementDao : payoutElementDaos) {
            result.getElements().add(PayoutElementDto.builder()
                    .uuid(payoutElementDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PayoutElementDto createPayoutElement(PayoutElementDto payoutElementDto) {

        PayoutElementDao payoutElementDao = new PayoutElementDao();
        payoutElementDao = payoutElementRepository.save(payoutElementDao);
        if (payoutElementDao == null)
            throw new ResponseException("Error in saving payoutElement payoutElement");
        return PayoutElementDto.builder()
                .uuid(payoutElementDao.getUuid()).build();
    }

    @Override
    public PayoutElementDto updatePayoutElement(String payoutElementUUID, PayoutElementDto payoutElementDto) {
        PayoutElementDao payoutElementDao = payoutElementRepository.findByUUID(payoutElementUUID);
        if (payoutElementDao == null)
            throw new ResponseException("No payoutElement  found for Id :" + payoutElementUUID);
        PayoutElementDao newPayoutElementDao = new PayoutElementDao();


        newPayoutElementDao.setId(payoutElementDao.getId());
        newPayoutElementDao.setUuid(payoutElementDao.getUuid());
        payoutElementDao = payoutElementRepository.update(newPayoutElementDao);
        if (payoutElementDao == null)
            throw new ResponseException("Error in updating details payoutElement ");
        return PayoutElementDto.builder()
                .uuid(payoutElementDao.getUuid()).build();
    }

    @Override
    public PayoutElementDto getPayoutElementDetails(String payoutElementUUID) {
        PayoutElementDao payoutElementDao = payoutElementRepository.findByUUID(payoutElementUUID);
        if (payoutElementDao == null)
            throw new ResponseException("No payoutElement  found for Id :" + payoutElementUUID);
        return PayoutElementDto.builder()
                .uuid(payoutElementDao.getUuid()).build();
    }

    @Override
    public Boolean deletePayoutElement(String payoutElementUUID) {
        PayoutElementDao payoutElementDao = payoutElementRepository.findByUUID(payoutElementUUID);
        if (payoutElementDao == null)
            throw new ResponseException("No payoutElement  found for Id :" + payoutElementUUID);
        payoutElementRepository.delete(payoutElementDao.getId());
        return true;
    }
}
