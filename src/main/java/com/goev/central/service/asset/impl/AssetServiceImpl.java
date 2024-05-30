package com.goev.central.service.asset.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.asset.AssetDao;
import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.common.QrValueDto;
import com.goev.central.repository.asset.AssetRepository;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.central.service.asset.AssetService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;

    @Override
    public PaginatedResponseDto<AssetDto> getAssets() {
        PaginatedResponseDto<AssetDto> result = PaginatedResponseDto.<AssetDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<AssetDao> assetDaos = assetRepository.findAll();
        if (CollectionUtils.isEmpty(assetDaos))
            return result;

        for (AssetDao assetDao : assetDaos) {
            result.getElements().add(getAssetDto(assetDao));
        }
        return result;
    }

    @Override
    public AssetDto createAsset(AssetDto assetDto) {

        AssetDao assetDao = getAssetDao(assetDto);
        assetDao = assetRepository.save(assetDao);
        if (assetDao == null)
            throw new ResponseException("Error in saving asset asset");
        return getAssetDto(assetDao);
    }

    private AssetDao getAssetDao(AssetDto assetDto) {
        AssetTypeDao type = assetTypeRepository.findByUUID(assetDto.getType().getUuid());
       return AssetDao.fromDto(assetDto,type.getId());
    }

    private AssetDto getAssetDto(AssetDao assetDao) {
        AssetTypeDao type = assetTypeRepository.findById(assetDao.getAssetTypeId());
        AssetDto assetDto = AssetDto.fromDao(assetDao);
        assetDto.setType(AssetTypeDto.fromDao(type));
        return assetDto;
    }

    @Override
    public AssetDto updateAsset(String assetUUID, AssetDto assetDto) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);
        AssetDao newAssetDao = getAssetDao(assetDto);
        newAssetDao.setId(assetDao.getId());
        newAssetDao.setUuid(assetDao.getUuid());
        assetDao = assetRepository.update(newAssetDao);
        if (assetDao == null)
            throw new ResponseException("Error in updating details asset ");
        return getAssetDto(assetDao);
    }

    @Override
    public AssetDto getAssetDetails(String assetUUID) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);
        return getAssetDto(assetDao);
    }

    @Override
    public String getAssetQr(String assetUUID) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);

        return ApplicationConstants.GSON.toJson(QrValueDto.builder()
                .name(assetDao.getAssetName())
                .entity("ASSET")
                .timestamp(DateTime.now())
                .uuid(assetUUID)
                .build());
    }

    @Override
    public Boolean deleteAsset(String assetUUID) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);
        assetRepository.delete(assetDao.getId());
        return true;
    }
}
