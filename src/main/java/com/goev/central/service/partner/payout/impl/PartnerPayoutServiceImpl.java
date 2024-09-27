package com.goev.central.service.partner.payout.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.partner.payout.PartnerPayoutTransactionDto;
import com.goev.central.enums.partner.PartnerShiftStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutTransactionRepository;
import com.goev.central.service.partner.payout.PartnerPayoutService;
import com.goev.lib.exceptions.ResponseException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class PartnerPayoutServiceImpl implements PartnerPayoutService {
    private final PartnerPayoutRepository partnerPayoutRepository;
    private final PartnerPayoutTransactionRepository partnerPayoutTransactionRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerPayoutDto> getPayoutsForPartner(String partnerUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PaginatedResponseDto<PartnerPayoutDto> result = PaginatedResponseDto.<PartnerPayoutDto>builder().elements(new ArrayList<>()).build();
        List<PartnerPayoutDao> partnerPayoutDaos = partnerPayoutRepository.findAllByPartnerId(partnerDao.getId());
        if (CollectionUtils.isEmpty(partnerPayoutDaos))
            return result;

        for (PartnerPayoutDao partnerPayoutDao : partnerPayoutDaos) {
            result.getElements().add(PartnerPayoutDto.builder()
                    .uuid(partnerPayoutDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerPayoutSummaryDto getPayoutSummaryForPayout(String partnerUUID, String partnerPayoutUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PartnerPayoutDao partnerPayoutDao = partnerPayoutRepository.findByUUID(partnerPayoutUUID);
        if (partnerPayoutDao == null)
            throw new ResponseException("No partner payout  found for Id :" + partnerPayoutUUID);

        return new Gson().fromJson(partnerPayoutDao.getPayoutSummary(), PartnerPayoutSummaryDto.class);
    }

    @Override
    public PaginatedResponseDto<PartnerPayoutTransactionDto> getAllPartnerTransactionsForPayout(String partnerUUID, String partnerPayoutUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PartnerPayoutDao partnerPayoutDao = partnerPayoutRepository.findByUUID(partnerPayoutUUID);
        if (partnerPayoutDao == null)
            throw new ResponseException("No partner payout  found for Id :" + partnerPayoutUUID);


        PaginatedResponseDto<PartnerPayoutTransactionDto> result = PaginatedResponseDto.<PartnerPayoutTransactionDto>builder().elements(new ArrayList<>()).build();
        List<PartnerPayoutTransactionDao> partnerPayoutTransactionDaos = partnerPayoutTransactionRepository.findAllByPartnerPayoutId(partnerPayoutDao.getId());
        if (CollectionUtils.isEmpty(partnerPayoutTransactionDaos))
            return result;

        for (PartnerPayoutTransactionDao partnerPayoutTransactionDao : partnerPayoutTransactionDaos) {
            result.getElements().add(PartnerPayoutTransactionDto.builder()
                    .uuid(partnerPayoutTransactionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<PartnerPayoutDto> getPayouts(String status, PageDto page, FilterDto filter) {

        List<PartnerPayoutDao> activePayouts;
        if(PartnerShiftStatus.IN_PROGRESS.name().equals(status) || PartnerShiftStatus.PENDING.name().equals(status)  )
            activePayouts = partnerPayoutRepository.findAllByStatus(status,page);
        else
            activePayouts = partnerPayoutRepository.findAllByStatus(status,page,filter);

        if (CollectionUtils.isEmpty(activePayouts))
            return PaginatedResponseDto.<PartnerPayoutDto>builder().elements(new ArrayList<>()).build();


        List<PartnerDao> partners = partnerRepository.findAllByIds(activePayouts.stream().map(PartnerPayoutDao::getPartnerId).toList());
        Map<Integer, PartnerDao> partnerDaoMap = partners.stream().collect(Collectors.toMap(PartnerDao::getId, Function.identity()));

        List<PartnerPayoutDto> payoutList = new ArrayList<>();
        activePayouts.forEach(x -> {
            PartnerViewDto partnerViewDto = PartnerViewDto.fromDao(partnerDaoMap.get(x.getPartnerId()));
            payoutList.add(PartnerPayoutDto.fromDao(x, partnerViewDto));
        });
        return PaginatedResponseDto.<PartnerPayoutDto>builder().elements(payoutList).build();

    }


}
