package com.goev.central.service.asset.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.asset.AssetDao;
import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dao.location.LocationAssetMappingDao;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.partner.asset.PartnerAssetMappingDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.vehicle.asset.VehicleAssetMappingDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.asset.AssetMappingDto;
import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.common.QrValueDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.enums.asset.AssetMappingStatus;
import com.goev.central.enums.asset.AssetParentType;
import com.goev.central.repository.asset.AssetRepository;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.central.repository.location.LocationAssetMappingRepository;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.repository.partner.asset.PartnerAssetMappingRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.vehicle.asset.VehicleAssetMappingRepository;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.service.asset.AssetService;
import com.goev.central.utilities.SecretGenerationUtils;
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
    private final PartnerAssetMappingRepository partnerAssetMappingRepository;
    private final VehicleAssetMappingRepository vehicleAssetMappingRepository;
    private final LocationAssetMappingRepository locationAssetMappingRepository;
    private final PartnerRepository partnerRepository;
    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;

    @Override
    public PaginatedResponseDto<AssetDto> getAssets() {
        PaginatedResponseDto<AssetDto> result = PaginatedResponseDto.<AssetDto>builder().elements(new ArrayList<>()).build();
        List<AssetDao> assetDaos = assetRepository.findAllActive();
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
        if (assetDto.getType() == null || assetDto.getType().getUuid() == null)
            throw new ResponseException("Invalid asset type");

        AssetTypeDao type = assetTypeRepository.findByUUID(assetDto.getType().getUuid());
        if (type == null)
            throw new ResponseException("Invalid asset type");
        assetDto.setParentType(type.getParentType());
        AssetDao dao = AssetDao.fromDto(assetDto, type.getId());
        dao.setDisplayCode("AST-"+ SecretGenerationUtils.getCode());
        return dao ;
    }

    private AssetDto getAssetDto(AssetDao assetDao) {
        AssetTypeDao type = assetTypeRepository.findById(assetDao.getAssetTypeId());
        return AssetDto.fromDao(assetDao,AssetTypeDto.fromDao(type));
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

    @Override
    public AssetMappingDto getAssetMappingDetails(String assetUUID) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);

        if (assetDao.getParentType() == null)
            return null;
        switch (AssetParentType.valueOf(assetDao.getParentType())) {
            case PARTNER -> {
                return getPartnerMappingDetails(assetDao);
            }
            case VEHICLE -> {
                return getVehicleMappingDetails(assetDao);
            }
            case LOCATION -> {
                return getLocationMappingDetails(assetDao);
            }
            default -> {
                return null;
            }

        }

    }

    @Override
    public AssetMappingDto createAssetMapping(String assetUUID, AssetMappingDto assetMappingDto) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);

        if (assetDao.getParentType() == null)
            return null;
        switch (AssetParentType.valueOf(assetDao.getParentType())) {
            case PARTNER -> {
                return createPartnerMappingDetails(assetDao, assetMappingDto);
            }
            case VEHICLE -> {
                return createVehicleMappingDetails(assetDao, assetMappingDto);
            }
            case LOCATION -> {
                return createLocationMappingDetails(assetDao, assetMappingDto);
            }
            default -> {
                throw new ResponseException("Invalid parent type :" + assetUUID);
            }

        }

    }


    @Override
    public AssetMappingDto updateAssetMapping(String assetUUID, String assetMappingUUID, AssetMappingDto assetMappingDto) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);

        if (assetDao.getParentType() == null)
            return null;
        switch (AssetParentType.valueOf(assetDao.getParentType())) {
            case PARTNER -> {
                deletePartnerAssetMapping(assetMappingUUID);
                if (assetMappingDto.getParentUUID() != null)
                    return createPartnerMappingDetails(assetDao, assetMappingDto);
                return null;
            }
            case VEHICLE -> {
                deleteVehicleAssetMapping(assetMappingUUID);
                if (assetMappingDto.getParentUUID() != null)
                    return createVehicleMappingDetails(assetDao, assetMappingDto);
                return null;
            }
            case LOCATION -> {
                deleteLocationAssetMapping(assetMappingUUID);
                if (assetMappingDto.getParentUUID() != null)
                    return createLocationMappingDetails(assetDao, assetMappingDto);
                return null;
            }
            default -> {
                throw new ResponseException("Invalid parent type :" + assetUUID);
            }

        }

    }

    @Override
    public Boolean deleteAssetMapping(String assetUUID, String assetMappingUUID) {
        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset  found for Id :" + assetUUID);

        if (assetDao.getParentType() == null)
            return null;
        switch (AssetParentType.valueOf(assetDao.getParentType())) {
            case PARTNER -> {
                return deletePartnerAssetMapping(assetMappingUUID);
            }
            case VEHICLE -> {
                return deleteVehicleAssetMapping(assetMappingUUID);
            }
            case LOCATION -> {
                return deleteLocationAssetMapping(assetMappingUUID);
            }
            default -> {
                throw new ResponseException("Invalid parent type :" + assetUUID);
            }

        }
    }

    private AssetMappingDto getLocationMappingDetails(AssetDao assetDao) {
        LocationAssetMappingDao mappingDao = locationAssetMappingRepository.findByAssetId(assetDao.getId());
        if (mappingDao == null)
            return null;
        LocationDao locationDao = locationRepository.findById(mappingDao.getLocationId());
        return AssetMappingDto.builder()
                .parentType(assetDao.getParentType())
                .parentName(locationDao.getName())
                .uuid(mappingDao.getUuid())
                .parentUUID(locationDao.getUuid()).build();
    }


    private AssetMappingDto createLocationMappingDetails(AssetDao assetDao, AssetMappingDto assetMappingDto) {

        LocationDao locationDao = locationRepository.findByUUID(assetMappingDto.getParentUUID());
        if (locationDao == null)
            throw new ResponseException("Invalid Partner Details");


        LocationAssetMappingDao mappingDao = new LocationAssetMappingDao();
        mappingDao.setLocationId(locationDao.getId());
        mappingDao.setAssetId(assetDao.getId());
        mappingDao.setStatus(AssetMappingStatus.MAPPED.name());
        mappingDao = locationAssetMappingRepository.save(mappingDao);
        if (mappingDao == null)
            throw new ResponseException("Error in mapping asset :" + assetDao.getUuid() + " to location :" + locationDao.getUuid());

        return AssetMappingDto.builder()
                .parentType(assetDao.getParentType())
                .parentName(locationDao.getName())
                .uuid(mappingDao.getUuid())
                .parentUUID(locationDao.getUuid()).build();
    }

    private AssetMappingDto getPartnerMappingDetails(AssetDao assetDao) {

        PartnerAssetMappingDao mappingDao = partnerAssetMappingRepository.findByAssetId(assetDao.getId());
        if (mappingDao == null)
            return null;
        PartnerDao partnerDao = partnerRepository.findById(mappingDao.getPartnerId());
        PartnerViewDto partnerViewDto = ApplicationConstants.GSON.fromJson(partnerDao.getViewInfo(), PartnerViewDto.class);
        return AssetMappingDto.builder()
                .parentType(assetDao.getParentType())
                .parentName(partnerDao.getPunchId() + "-" + PartnerViewDto.getPartnerName(partnerViewDto))
                .uuid(mappingDao.getUuid())
                .parentUUID(partnerDao.getUuid()).build();
    }


    private AssetMappingDto createPartnerMappingDetails(AssetDao assetDao, AssetMappingDto assetMappingDto) {

        PartnerDao partnerDao = partnerRepository.findByUUID(assetMappingDto.getParentUUID());
        if (partnerDao == null)
            throw new ResponseException("Invalid Partner Details");


        PartnerAssetMappingDao mappingDao = new PartnerAssetMappingDao();
        mappingDao.setPartnerId(partnerDao.getId());
        mappingDao.setAssetId(assetDao.getId());
        mappingDao.setStatus(AssetMappingStatus.MAPPED.name());
        mappingDao = partnerAssetMappingRepository.save(mappingDao);
        if (mappingDao == null)
            throw new ResponseException("Error in mapping asset :" + assetDao.getUuid() + " to partner :" + partnerDao.getUuid());

        PartnerViewDto partnerViewDto = ApplicationConstants.GSON.fromJson(partnerDao.getViewInfo(), PartnerViewDto.class);
        return AssetMappingDto.builder()
                .parentType(assetDao.getParentType())
                .parentName(partnerDao.getPunchId() + "-" + PartnerViewDto.getPartnerName(partnerViewDto))
                .uuid(mappingDao.getUuid())
                .parentUUID(partnerDao.getUuid()).build();
    }


    private AssetMappingDto getVehicleMappingDetails(AssetDao assetDao) {

        VehicleAssetMappingDao mappingDao = vehicleAssetMappingRepository.findByAssetId(assetDao.getId());
        if (mappingDao == null)
            return null;
        VehicleDao vehicleDao = vehicleRepository.findById(mappingDao.getVehicleId());
        return AssetMappingDto.builder()
                .parentType(assetDao.getParentType())
                .parentName(vehicleDao.getPlateNumber())
                .uuid(mappingDao.getUuid())
                .parentUUID(vehicleDao.getUuid()).build();
    }

    private AssetMappingDto createVehicleMappingDetails(AssetDao assetDao, AssetMappingDto assetMappingDto) {

        VehicleDao vehicleDao = vehicleRepository.findByUUID(assetMappingDto.getParentUUID());
        if (vehicleDao == null)
            throw new ResponseException("Invalid Vehicle Details");


        VehicleAssetMappingDao mappingDao = new VehicleAssetMappingDao();
        mappingDao.setVehicleId(vehicleDao.getId());
        mappingDao.setAssetId(assetDao.getId());
        mappingDao.setStatus(AssetMappingStatus.MAPPED.name());
        mappingDao = vehicleAssetMappingRepository.save(mappingDao);
        if (mappingDao == null)
            throw new ResponseException("Error in mapping asset :" + assetDao.getUuid() + " to vehicle :" + vehicleDao.getUuid());

        return AssetMappingDto.builder()
                .parentType(assetDao.getParentType())
                .parentName(vehicleDao.getPlateNumber())
                .uuid(mappingDao.getUuid())
                .parentUUID(vehicleDao.getUuid()).build();
    }


    private Boolean deletePartnerAssetMapping(String assetMappingUUID) {
        PartnerAssetMappingDao mappingDao = partnerAssetMappingRepository.findByUUID(assetMappingUUID);
        if (mappingDao == null)
            throw new ResponseException("No mapping found for asset mapping : " + assetMappingUUID);
        partnerAssetMappingRepository.delete(mappingDao.getId());
        return true;
    }

    private Boolean deleteVehicleAssetMapping(String assetMappingUUID) {
        VehicleAssetMappingDao mappingDao = vehicleAssetMappingRepository.findByUUID(assetMappingUUID);
        if (mappingDao == null)
            throw new ResponseException("No mapping found for asset mapping : " + assetMappingUUID);
        vehicleAssetMappingRepository.delete(mappingDao.getId());
        return true;
    }

    private Boolean deleteLocationAssetMapping(String assetMappingUUID) {
        LocationAssetMappingDao mappingDao = locationAssetMappingRepository.findByUUID(assetMappingUUID);
        if (mappingDao == null)
            throw new ResponseException("No mapping found for asset mapping : " + assetMappingUUID);
        locationAssetMappingRepository.delete(mappingDao.getId());
        return true;
    }
}


