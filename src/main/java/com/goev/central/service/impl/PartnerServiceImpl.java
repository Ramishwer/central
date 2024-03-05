package com.goev.central.service.impl;


import com.goev.central.dto.partner.PartnerDetailsDto;
import com.goev.central.dto.partner.PartnerDto;
import com.goev.central.service.PartnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PartnerServiceImpl implements PartnerService {

    @Override
    public PartnerDetailsDto createPartner(PartnerDetailsDto partnerDto) {
        return null;
    }

    @Override
    public PartnerDetailsDto updatePartner(String partnerUUID, PartnerDetailsDto credentials) {
        return null;
    }

    @Override
    public PartnerDetailsDto getPartnerDetails(String partnerUUID) {
        return null;
    }

    @Override
    public Boolean deletePartner(String partnerUUID) {
        return null;
    }

    @Override
    public List<PartnerDto> getPartners() {
        return new ArrayList<>();
    }
}
