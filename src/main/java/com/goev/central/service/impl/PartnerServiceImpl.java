package com.goev.central.service.impl;


import com.goev.central.dao.partner.PartnerDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerDetailsDto;
import com.goev.central.dto.partner.PartnerDto;
import com.goev.central.repository.partner.PartnerRepository;
import com.goev.central.service.PartnerService;
import com.goev.lib.exceptions.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PartnerServiceImpl  implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;
    @Override
    public PartnerDetailsDto createPartner(PartnerDetailsDto partnerDto) {
        PartnerDao partner = partnerRepository.save(new PartnerDao().fromDto(partnerDto.getDetails()));
        if(partner == null)
            throw new ResponseException("Error in saving details");
        return PartnerDetailsDto.builder().details(partner.toDto()).uuid(partner.getUuid()).build();
    }

    @Override
    public PartnerDetailsDto updatePartner(String partnerUUID, PartnerDetailsDto partnerDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if(partner == null)
            throw new ResponseException("No partner found for Id :"+partnerUUID);
        PartnerDao newPartnerDto = partner.fromDto(partnerDto.getDetails());
        newPartnerDto.setId(partner.getId());
        newPartnerDto.setUuid(partner.getUuid());
        partner =partnerRepository.update(newPartnerDto);
        if(partner == null)
            throw new ResponseException("Error in saving details");
        return PartnerDetailsDto.builder().details(partner.toDto()).uuid(partner.getUuid()).build();
    }

    @Override
    public PartnerDetailsDto getPartnerDetails(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if(partner == null)
            throw new ResponseException("No partner found for Id :"+partnerUUID);
        return PartnerDetailsDto.builder().details(partner.toDto()).uuid(partner.getUuid()).build();
    }

    @Override
    public Boolean deletePartner(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if(partner == null)
            throw new ResponseException("No partner found for Id :"+partnerUUID);

        partnerRepository.delete(partner.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<PartnerDto> getPartners() {

        PaginatedResponseDto<PartnerDto> result = PaginatedResponseDto.<PartnerDto>builder().elements(new ArrayList<>()).build();
        partnerRepository.findAll().forEach(x->{
            result.getElements().add(x.toDto());
        });
        return result;
    }
}
