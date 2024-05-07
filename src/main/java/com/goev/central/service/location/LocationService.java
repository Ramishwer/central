package com.goev.central.service.location;

import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface LocationService {
    PaginatedResponseDto<LocationDto> getLocations();

    LocationDto createLocation(LocationDto locationDto);

    LocationDto updateLocation(String locationUUID, LocationDto locationDto);

    LocationDto getLocationDetails(String locationUUID);

    Boolean deleteLocation(String locationUUID);

    String getLocationQr(String locationUUID);
}
