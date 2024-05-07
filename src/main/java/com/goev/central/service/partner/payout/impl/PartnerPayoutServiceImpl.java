package com.goev.central.service.partner.payout.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.partner.payout.PartnerPayoutTransactionDto;
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

@AllArgsConstructor
@Slf4j
@Service
public class PartnerPayoutServiceImpl implements PartnerPayoutService {
    private final PartnerPayoutRepository partnerPayoutRepository;
    private final PartnerPayoutTransactionRepository partnerPayoutTransactionRepository;
    private final PartnerRepository partnerRepository;
    @Override
    public PaginatedResponseDto<PartnerPayoutDto> getPayouts(String partnerUUID) {
        PartnerDao partnerDao  = partnerRepository.findByUUID(partnerUUID);
        if(partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PaginatedResponseDto<PartnerPayoutDto> result = PaginatedResponseDto.<PartnerPayoutDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
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
        PartnerDao partnerDao  = partnerRepository.findByUUID(partnerUUID);
        if(partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PartnerPayoutDao partnerPayoutDao  = partnerPayoutRepository.findByUUID(partnerPayoutUUID);
        if(partnerPayoutDao == null)
            throw new ResponseException("No partner payout  found for Id :" + partnerPayoutUUID);

        return new Gson().fromJson(partnerPayoutDao.getPayoutSummary(),PartnerPayoutSummaryDto.class);
    }

    @Override
    public PaginatedResponseDto<PartnerPayoutTransactionDto> getAllPartnerTransactionsForPayout(String partnerUUID, String partnerPayoutUUID) {
        PartnerDao partnerDao  = partnerRepository.findByUUID(partnerUUID);
        if(partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PartnerPayoutDao partnerPayoutDao  = partnerPayoutRepository.findByUUID(partnerPayoutUUID);
        if(partnerPayoutDao == null)
            throw new ResponseException("No partner payout  found for Id :" + partnerPayoutUUID);


        PaginatedResponseDto<PartnerPayoutTransactionDto> result = PaginatedResponseDto.<PartnerPayoutTransactionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
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



}
