package com.goev.central.service.partner.passbook.impl;

import com.goev.central.dao.partner.passbook.PartnerPassbookTransactionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.passbook.PartnerPassbookTransactionDto;
import com.goev.central.repository.partner.passbook.PartnerPassbookTransactionRepository;
import com.goev.central.service.partner.passbook.PartnerPassbookTransactionService;
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
public class PartnerPassbookTransactionServiceImpl implements PartnerPassbookTransactionService {

    private final PartnerPassbookTransactionRepository partnerPassbookTransactionRepository;

    @Override
    public PaginatedResponseDto<PartnerPassbookTransactionDto> getPartnerPassbookTransactions(String partnerUUID) {
        PaginatedResponseDto<PartnerPassbookTransactionDto> result = PaginatedResponseDto.<PartnerPassbookTransactionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerPassbookTransactionDao> partnerPassbookTransactionDaos = partnerPassbookTransactionRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerPassbookTransactionDaos))
            return result;

        for (PartnerPassbookTransactionDao partnerPassbookTransactionDao : partnerPassbookTransactionDaos) {
            result.getElements().add(PartnerPassbookTransactionDto.builder()
                    .uuid(partnerPassbookTransactionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerPassbookTransactionDto getPartnerPassbookTransactionDetails(String partnerUUID, String partnerPassbookTransactionUUID) {
        PartnerPassbookTransactionDao partnerPassbookTransactionDao = partnerPassbookTransactionRepository.findByUUID(partnerPassbookTransactionUUID);
        if (partnerPassbookTransactionDao == null)
            throw new ResponseException("No partnerPassbookTransaction  found for Id :" + partnerPassbookTransactionUUID);
        return PartnerPassbookTransactionDto.builder()
                .uuid(partnerPassbookTransactionDao.getUuid()).build();
    }

}
