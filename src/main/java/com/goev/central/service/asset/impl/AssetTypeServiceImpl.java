package com.goev.central.service.asset.impl;

import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.central.service.asset.AssetTypeService;
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
public class AssetTypeServiceImpl implements AssetTypeService {

    private final AssetTypeRepository assetTypeRepository;

    @Override
    public PaginatedResponseDto<AssetTypeDto> getAssetTypes() {
        PaginatedResponseDto<AssetTypeDto> result = PaginatedResponseDto.<AssetTypeDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<AssetTypeDao> assetTypeDaos = assetTypeRepository.findAllActive();
        if (CollectionUtils.isEmpty(assetTypeDaos))
            return result;

        for (AssetTypeDao assetTypeDao : assetTypeDaos) {
            result.getElements().add(getAssetTypeDto(assetTypeDao));
        }
        return result;
    }

    @Override
    public AssetTypeDto createAssetType(AssetTypeDto assetTypeDto) {

        AssetTypeDao assetTypeDao = getAssetTypeDao(assetTypeDto);
        assetTypeDao = assetTypeRepository.save(assetTypeDao);
        if (assetTypeDao == null)
            throw new ResponseException("Error in saving assetType assetType");
        return getAssetTypeDto(assetTypeDao);
    }

    private AssetTypeDao getAssetTypeDao(AssetTypeDto assetTypeDto) {
        return AssetTypeDao.fromDto(assetTypeDto);
    }

    private AssetTypeDto getAssetTypeDto(AssetTypeDao assetTypeDao) {
        return AssetTypeDto.fromDao(assetTypeDao);
    }

    @Override
    public AssetTypeDto updateAssetType(String assetTypeUUID, AssetTypeDto assetTypeDto) {
        AssetTypeDao assetTypeDao = assetTypeRepository.findByUUID(assetTypeUUID);
        if (assetTypeDao == null)
            throw new ResponseException("No assetType  found for Id :" + assetTypeUUID);
        AssetTypeDao newAssetTypeDao = getAssetTypeDao(assetTypeDto);

        newAssetTypeDao.setId(assetTypeDao.getId());
        newAssetTypeDao.setUuid(assetTypeDao.getUuid());
        assetTypeDao = assetTypeRepository.update(newAssetTypeDao);
        if (assetTypeDao == null)
            throw new ResponseException("Error in updating details assetType ");
        return getAssetTypeDto(assetTypeDao);
    }

    @Override
    public AssetTypeDto getAssetTypeDetails(String assetTypeUUID) {
        AssetTypeDao assetTypeDao = assetTypeRepository.findByUUID(assetTypeUUID);
        if (assetTypeDao == null)
            throw new ResponseException("No assetType  found for Id :" + assetTypeUUID);
        return getAssetTypeDto(assetTypeDao);
    }

    @Override
    public Boolean deleteAssetType(String assetTypeUUID) {
        AssetTypeDao assetTypeDao = assetTypeRepository.findByUUID(assetTypeUUID);
        if (assetTypeDao == null)
            throw new ResponseException("No assetType  found for Id :" + assetTypeUUID);
        assetTypeRepository.delete(assetTypeDao.getId());
        return true;
    }
}
