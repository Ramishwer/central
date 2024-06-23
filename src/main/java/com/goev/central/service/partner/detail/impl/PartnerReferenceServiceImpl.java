package com.goev.central.service.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.detail.PartnerReferenceDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerReferenceDto;
import com.goev.central.repository.partner.detail.PartnerReferenceRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.detail.PartnerReferenceService;
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
public class PartnerReferenceServiceImpl implements PartnerReferenceService {

    private final PartnerReferenceRepository partnerReferenceRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerReferenceDto> getReferences(String partnerUUID) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerReferenceDao> activeReferences = partnerReferenceRepository.findAllByPartnerId(partner.getId());
        if (CollectionUtils.isEmpty(activeReferences))
            return PaginatedResponseDto.<PartnerReferenceDto>builder().elements(new ArrayList<>()).build();

        List<PartnerReferenceDto> referenceList = new ArrayList<>();
        activeReferences.forEach(x -> referenceList.add(PartnerReferenceDto.builder()
                .uuid(x.getUuid())
                .name(x.getName())
                .phoneNumber(x.getPhoneNumber())
                .relation(x.getRelation())
                .address(x.getAddress())
                .email(x.getEmail())
                .build()));
        return PaginatedResponseDto.<PartnerReferenceDto>builder().elements(referenceList).build();

    }

    @Override
    public PartnerReferenceDto createReference(String partnerUUID, PartnerReferenceDto partnerReferenceDto) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerReferenceDao partnerReferenceDao = new PartnerReferenceDao();
        partnerReferenceDao.setName(partnerReferenceDto.getName());
        partnerReferenceDao.setPhoneNumber(partnerReferenceDto.getPhoneNumber());
        partnerReferenceDao.setRelation(partnerReferenceDto.getRelation());
        partnerReferenceDao.setAddress(partnerReferenceDto.getAddress());
        partnerReferenceDao.setEmail(partnerReferenceDto.getEmail());

        partnerReferenceDao.setPartnerId(partner.getId());
        partnerReferenceDao = partnerReferenceRepository.save(partnerReferenceDao);
        if (partnerReferenceDao == null)
            throw new ResponseException("Error in saving partner reference");
        return PartnerReferenceDto.builder()
                .uuid(partnerReferenceDao.getUuid())
                .name(partnerReferenceDao.getName())
                .phoneNumber(partnerReferenceDao.getPhoneNumber())
                .relation(partnerReferenceDao.getRelation())
                .address(partnerReferenceDao.getAddress())
                .email(partnerReferenceDao.getEmail())
                .build();
    }

    @Override
    public PartnerReferenceDto updateReference(String partnerUUID, String referenceUUID, PartnerReferenceDto partnerReferenceDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerReferenceDao partnerReferenceDao = partnerReferenceRepository.findByUUID(referenceUUID);
        if (partnerReferenceDao == null)
            throw new ResponseException("No partner reference found for Id :" + referenceUUID);

        PartnerReferenceDao newPartnerReferenceDao = new PartnerReferenceDao();

        newPartnerReferenceDao.setName(partnerReferenceDto.getName());
        newPartnerReferenceDao.setPhoneNumber(partnerReferenceDto.getPhoneNumber());
        newPartnerReferenceDao.setRelation(partnerReferenceDto.getRelation());
        newPartnerReferenceDao.setAddress(partnerReferenceDto.getAddress());
        newPartnerReferenceDao.setEmail(partnerReferenceDto.getEmail());

        newPartnerReferenceDao.setPartnerId(partner.getId());

        newPartnerReferenceDao.setId(partnerReferenceDao.getId());
        newPartnerReferenceDao.setUuid(partnerReferenceDao.getUuid());
        partnerReferenceDao = partnerReferenceRepository.update(newPartnerReferenceDao);
        if (partnerReferenceDao == null)
            throw new ResponseException("Error in updating details partner reference");


        return PartnerReferenceDto.builder()
                .uuid(partnerReferenceDao.getUuid())
                .name(partnerReferenceDao.getName())
                .phoneNumber(partnerReferenceDao.getPhoneNumber())
                .relation(partnerReferenceDao.getRelation())
                .address(partnerReferenceDao.getAddress())
                .email(partnerReferenceDao.getEmail())
                .build();
    }

    @Override
    public PartnerReferenceDto getReferenceDetails(String partnerUUID, String referenceUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerReferenceDao partnerReferenceDao = partnerReferenceRepository.findByUUID(referenceUUID);
        if (partnerReferenceDao == null)
            throw new ResponseException("No partner reference found for Id :" + referenceUUID);


        return PartnerReferenceDto.builder()
                .uuid(partnerReferenceDao.getUuid())
                .name(partnerReferenceDao.getName())
                .phoneNumber(partnerReferenceDao.getPhoneNumber())
                .relation(partnerReferenceDao.getRelation())
                .address(partnerReferenceDao.getAddress())
                .email(partnerReferenceDao.getEmail())
                .build();
    }

    @Override
    public Boolean deleteReference(String partnerUUID, String referenceUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerReferenceDao partnerReferenceDao = partnerReferenceRepository.findByUUID(referenceUUID);
        if (partnerReferenceDao == null)
            throw new ResponseException("No partner reference found for Id :" + referenceUUID);
        partnerReferenceRepository.delete(partnerReferenceDao.getId());
        return true;
    }


    @Override
    public List<PartnerReferenceDto> bulkCreateReference(String partnerUUID, List<PartnerReferenceDto> partnerReferenceDtoList) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for id :" + partnerUUID);

        if (CollectionUtils.isEmpty(partnerReferenceDtoList))
            throw new ResponseException("No Reference present");

        List<PartnerReferenceDto> result = new ArrayList<>();
        for (PartnerReferenceDto partnerReferenceDto : partnerReferenceDtoList) {
            if (partnerReferenceDto.getUuid() != null) {
                result.add(updateReference(partnerUUID, partnerReferenceDto.getUuid(), partnerReferenceDto));
            } else {
                result.add(createReference(partnerUUID, partnerReferenceDto));
            }
        }
        return result;
    }
}
