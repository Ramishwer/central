package com.goev.central.service.partner.incentive.impl;

import com.goev.central.dao.incentive.IncentiveModelDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.incentive.PartnerIncentiveMappingDao;
import com.goev.central.dto.incentive.IncentiveModelDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.incentive.PartnerIncentiveMappingDto;
import com.goev.central.repository.incentive.IncentiveModelRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.incentive.PartnerIncentiveMappingRepository;
import com.goev.central.service.partner.incentive.PartnerIncentiveService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class PartnerIncentiveServiceImpl implements PartnerIncentiveService {
    private final PartnerRepository partnerRepository;
    private final PartnerIncentiveMappingRepository partnerIncentiveMappingRepository;
    private final IncentiveModelRepository incentiveModelRepository;

    @Override
    public List<PartnerIncentiveMappingDto> getIncentiveModelMappings(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerIncentiveMappingDao> partnerIncentiveMappingDaoList = partnerIncentiveMappingRepository.findAllByPartnerId(partner.getId());

        List<PartnerIncentiveMappingDto> result = new ArrayList<>();

        for (PartnerIncentiveMappingDao partnerIncentiveMappingDao : partnerIncentiveMappingDaoList) {
            IncentiveModelDao incentiveModelDao = incentiveModelRepository.findById(partnerIncentiveMappingDao.getIncentiveModelId());
            if (incentiveModelDao == null)
                continue;
            result.add(PartnerIncentiveMappingDto.fromDao(partnerIncentiveMappingDao, PartnerViewDto.fromDao(partner), IncentiveModelDto.fromDao(incentiveModelDao)));
        }
        return result;
    }

    @Override
    public Boolean deleteIncentiveModelMapping(String partnerUUID, String partnerIncentiveMappingUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerIncentiveMappingDao partnerIncentiveMappingDao = partnerIncentiveMappingRepository.findByUUID(partnerIncentiveMappingUUID);
        if (partnerIncentiveMappingDao == null)
            throw new ResponseException("No incentive mapping found for Id :" + partnerIncentiveMappingUUID);

        partnerIncentiveMappingRepository.delete(partnerIncentiveMappingDao.getId());
        return true;
    }

    @Override
    public PartnerIncentiveMappingDto createIncentiveModelMapping(String partnerUUID, PartnerIncentiveMappingDto partnerIncentiveMappingDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        if (partnerIncentiveMappingDto.getIncentiveModel() == null)
            throw new ResponseException("No incentive model details present.");

        IncentiveModelDao incentiveModelDao = incentiveModelRepository.findByUUID(partnerIncentiveMappingDto.getIncentiveModel().getUuid());

        if (incentiveModelDao == null)
            throw new ResponseException("No incentive model found for Id :" + partnerIncentiveMappingDto.getIncentiveModel().getUuid());

        PartnerIncentiveMappingDao partnerIncentiveMappingDao = new PartnerIncentiveMappingDao();
        partnerIncentiveMappingDao.setTriggerType(partnerIncentiveMappingDao.getTriggerType());
        partnerIncentiveMappingDao.setPartnerId(partner.getId());
        partnerIncentiveMappingDao.setIncentiveModelId(incentiveModelDao.getId());
        partnerIncentiveMappingDao = partnerIncentiveMappingRepository.save(partnerIncentiveMappingDao);
        if (partnerIncentiveMappingDao == null)
            throw new ResponseException("Error in saving partner incentive mapping");

        return PartnerIncentiveMappingDto.fromDao(partnerIncentiveMappingDao, PartnerViewDto.fromDao(partner), IncentiveModelDto.fromDao(incentiveModelDao));
    }
}
