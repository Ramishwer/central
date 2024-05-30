package com.goev.central.service.location;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.common.PaginatedResponseDto;

import java.util.List;

public interface LocationService {
    PaginatedResponseDto<LocationDto> getLocations();

    LocationDto createLocation(LocationDto locationDto);

    LocationDto updateLocation(String locationUUID, LocationDto locationDto);

    LocationDto getLocationDetails(String locationUUID);

    Boolean deleteLocation(String locationUUID);

    String getLocationQr(String locationUUID);

    PaginatedResponseDto<AssetDto> getAssetsForLocations(String locationUUID);

    AssetDto createAssetsForLocation(AssetDto assetDto, String locationUUID);
    List<AssetDto> createBulkAssetsForLocation(List<AssetDto> assetDtoList, String locationUUID);

    Boolean deleteAssetsForLocation(String assetUUID,String locationUUID);
}
