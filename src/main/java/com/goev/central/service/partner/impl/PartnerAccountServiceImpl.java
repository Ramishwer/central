package com.goev.central.service.partner.impl;

import com.goev.central.dao.partner.detail.PartnerAccountDetailDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerAccountDto;
import com.goev.central.repository.partner.detail.PartnerAccountDetailRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.PartnerAccountService;
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
public class PartnerAccountServiceImpl implements PartnerAccountService {

    private final PartnerAccountDetailRepository partnerAccountDetailRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerAccountDto> getAccounts(String partnerUUID) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerAccountDetailDao> activeAccounts = partnerAccountDetailRepository.findAllByPartnerId(partner.getId());
        if (CollectionUtils.isEmpty(activeAccounts))
            return PaginatedResponseDto.<PartnerAccountDto>builder().currentPage(0).totalPages(0).elements(new ArrayList<>()).build();

        List<PartnerAccountDto> accountList = new ArrayList<>();
        activeAccounts.forEach(x -> accountList.add(PartnerAccountDto.builder()
                .uuid(x.getUuid())
                .accountHolderName(x.getAccountHolderName())
                .accountNumber(x.getAccountNumber())
                .ifscCode(x.getIfscCode())
                .build()));
        return PaginatedResponseDto.<PartnerAccountDto>builder().elements(accountList).build();

    }

    @Override
    public PartnerAccountDto createAccount(String partnerUUID, PartnerAccountDto partnerAccountDto) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerAccountDetailDao partnerAccountDetailDao = new PartnerAccountDetailDao();
        partnerAccountDetailDao.setAccountNumber(partnerAccountDto.getAccountNumber());
        partnerAccountDetailDao.setIfscCode(partnerAccountDto.getAccountNumber());
        partnerAccountDetailDao.setAccountNumber(partnerAccountDto.getAccountNumber());

        partnerAccountDetailDao.setPartnerId(partner.getId());
        partnerAccountDetailDao = partnerAccountDetailRepository.save(partnerAccountDetailDao);
        if (partnerAccountDetailDao == null)
            throw new ResponseException("Error in saving partner account");
        return PartnerAccountDto.builder()
                .ifscCode(partnerAccountDetailDao.getIfscCode())
                .accountHolderName(partnerAccountDetailDao.getAccountHolderName())
                .accountNumber(partnerAccountDetailDao.getAccountNumber())
                .uuid(partnerAccountDetailDao.getUuid())
                .build();
    }

    @Override
    public PartnerAccountDto updateAccount(String partnerUUID, String accountUUID, PartnerAccountDto partnerAccountDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerAccountDetailDao partnerAccountDetailDao = partnerAccountDetailRepository.findByUUID(accountUUID);
        if (partnerAccountDetailDao == null)
            throw new ResponseException("No partner account found for Id :" + accountUUID);

        PartnerAccountDetailDao newPartnerAccountDetailDao = new PartnerAccountDetailDao();
        newPartnerAccountDetailDao.setAccountNumber(partnerAccountDto.getAccountNumber());
        newPartnerAccountDetailDao.setAccountHolderName(partnerAccountDto.getAccountHolderName());
        newPartnerAccountDetailDao.setIfscCode(partnerAccountDto.getIfscCode());
        newPartnerAccountDetailDao.setPartnerId(partner.getId());

        newPartnerAccountDetailDao.setId(partnerAccountDetailDao.getId());
        newPartnerAccountDetailDao.setUuid(partnerAccountDetailDao.getUuid());
        partnerAccountDetailDao = partnerAccountDetailRepository.update(newPartnerAccountDetailDao);
        if (partnerAccountDetailDao == null)
            throw new ResponseException("Error in updating details partner account");


        return PartnerAccountDto.builder()
                .ifscCode(partnerAccountDetailDao.getIfscCode())
                .accountHolderName(partnerAccountDetailDao.getAccountHolderName())
                .accountNumber(partnerAccountDetailDao.getAccountNumber())
                .uuid(partnerAccountDetailDao.getUuid())
                .build();
    }

    @Override
    public PartnerAccountDto getAccountDetails(String partnerUUID, String accountUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerAccountDetailDao partnerAccountDetailDao = partnerAccountDetailRepository.findByUUID(accountUUID);
        if (partnerAccountDetailDao == null)
            throw new ResponseException("No partner account found for Id :" + accountUUID);


        return PartnerAccountDto.builder()
                .ifscCode(partnerAccountDetailDao.getIfscCode())
                .accountHolderName(partnerAccountDetailDao.getAccountHolderName())
                .accountNumber(partnerAccountDetailDao.getAccountNumber())
                .uuid(partnerAccountDetailDao.getUuid())
                .build();
    }

    @Override
    public Boolean deleteAccount(String partnerUUID, String accountUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerAccountDetailDao partnerAccountDetailDao = partnerAccountDetailRepository.findByUUID(accountUUID);
        if (partnerAccountDetailDao == null)
            throw new ResponseException("No partner account found for Id :" + accountUUID);
        partnerAccountDetailRepository.delete(partnerAccountDetailDao.getId());
        return true;
    }
}
