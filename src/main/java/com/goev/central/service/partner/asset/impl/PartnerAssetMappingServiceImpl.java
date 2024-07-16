package com.goev.central.service.partner.asset.impl;


import com.goev.central.dao.asset.AssetDao;
import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dao.partner.asset.PartnerAssetMappingDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.asset.AssetRepository;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.central.repository.partner.asset.PartnerAssetMappingRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.asset.PartnerAssetMappingService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerAssetMappingServiceImpl implements PartnerAssetMappingService {


    private final PartnerRepository partnerRepository;
    private final PartnerAssetMappingRepository partnerAssetMappingRepository;
    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;


    @Override
    public PaginatedResponseDto<AssetDto> getAssetsForPartners(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);
        List<PartnerAssetMappingDao> mappings = partnerAssetMappingRepository.findAllByPartnerId(partner.getId());

        if (CollectionUtils.isEmpty(mappings))
            return PaginatedResponseDto.<AssetDto>builder().elements(new ArrayList<>()).build();

        List<AssetDao> assets = assetRepository.findAllByIds(mappings.stream().map(PartnerAssetMappingDao::getAssetId).collect(Collectors.toList()));

        return PaginatedResponseDto.<AssetDto>builder().elements(assets.stream().map(x->{
            AssetTypeDao type = assetTypeRepository.findById(x.getAssetTypeId());
            return AssetDto.fromDao(x, AssetTypeDto.fromDao(type));
        }).collect(Collectors.toList())).build();
    }

    @Override
    public AssetDto createAssetsForPartner(AssetDto assetDto, String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        if (assetDto == null || assetDto.getUuid() == null)
            throw new ResponseException("No asset data  found");
        AssetDao assetDao = assetRepository.findByUUID(assetDto.getUuid());
        if (assetDao == null)
            throw new ResponseException("No asset found for Id :" + assetDto.getUuid());
        PartnerAssetMappingDao mapping = new PartnerAssetMappingDao();
        mapping.setPartnerId(partner.getId());
        mapping.setAssetId(assetDao.getId());
        partnerAssetMappingRepository.save(mapping);
        return assetDto;
    }

    @Override
    public List<AssetDto> createBulkAssetsForPartner(List<AssetDto> assetDtoList, String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        if (CollectionUtils.isEmpty(assetDtoList))
            return new ArrayList<>();

        for (AssetDto assetDto : assetDtoList) {
            AssetDao assetDao = assetRepository.findByUUID(assetDto.getUuid());
            if (assetDao == null)
                throw new ResponseException("No asset found for Id :" + assetDto.getUuid());
            PartnerAssetMappingDao mapping = new PartnerAssetMappingDao();
            mapping.setPartnerId(partner.getId());
            mapping.setAssetId(assetDao.getId());
            partnerAssetMappingRepository.save(mapping);
        }

        return assetDtoList;
    }

    @Override
    public Boolean deleteAssetsForPartner(String assetUUID, String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset found for Id :" + assetUUID);
        PartnerAssetMappingDao mapping = partnerAssetMappingRepository.findByPartnerIdAndAssetId(partner.getId(), assetDao.getId());
        if (mapping == null)
            throw new ResponseException("No mapping found for partner Id:" + partnerUUID + "and asset Id :" + assetUUID);

        partnerAssetMappingRepository.delete(mapping.getId());
        return true;
    }

}
