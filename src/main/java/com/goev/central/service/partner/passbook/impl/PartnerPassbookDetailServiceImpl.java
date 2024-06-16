package com.goev.central.service.partner.passbook.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.passbook.PartnerPassbookDetailDao;
import com.goev.central.dto.partner.passbook.PartnerPassbookDetailDto;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.passbook.PartnerPassbookDetailRepository;
import com.goev.central.service.partner.passbook.PartnerPassbookDetailService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerPassbookDetailServiceImpl implements PartnerPassbookDetailService {

    private final PartnerPassbookDetailRepository partnerPassbookDetailRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PartnerPassbookDetailDto getPartnerPassbookDetails(String partnerUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PartnerPassbookDetailDao partnerPassbookDetailDao = partnerPassbookDetailRepository.findByPartnerId(partnerDao.getId());
        if (partnerPassbookDetailDao == null)
            throw new ResponseException("No partnerPassbookDetail  found for Id :" + partnerUUID);
        return PartnerPassbookDetailDto.builder()
                .uuid(partnerPassbookDetailDao.getUuid()).build();
    }

}
