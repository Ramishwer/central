package com.goev.central.service.location.impl;

import com.goev.central.dao.location.LocationDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.service.location.LocationService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public PaginatedResponseDto<LocationDto> getLocations() {
        PaginatedResponseDto<LocationDto> result = PaginatedResponseDto.<LocationDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<LocationDao> locationDaos = locationRepository.findAll();
        if (CollectionUtils.isEmpty(locationDaos))
            return result;

        for (LocationDao locationDao : locationDaos) {
            result.getElements().add(LocationDto.builder()
                    .name(locationDao.getName())
                    .uuid(locationDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public LocationDto createLocation(LocationDto locationDto) {

        LocationDao locationDao = new LocationDao();
        locationDao.setName(locationDto.getName());
        locationDao = locationRepository.save(locationDao);
        if (locationDao == null)
            throw new ResponseException("Error in saving location");
        return LocationDto.builder().name(locationDao.getName()).uuid(locationDao.getUuid()).build();
    }

    @Override
    public LocationDto updateLocation(String locationUUID, LocationDto locationDto) {
        LocationDao locationDao = locationRepository.findByUUID(locationUUID);
        if (locationDao == null)
            throw new ResponseException("No location location found for Id :" + locationUUID);
        LocationDao newLocationDao = new LocationDao();
        newLocationDao.setName(locationDto.getName());

        newLocationDao.setId(locationDao.getId());
        newLocationDao.setUuid(locationDao.getUuid());
        locationDao = locationRepository.update(newLocationDao);
        if (locationDao == null)
            throw new ResponseException("Error in updating  location details ");
        return LocationDto.builder()
                .name(locationDao.getName())
                .uuid(locationDao.getUuid()).build();
    }

    @Override
    public LocationDto getLocationDetails(String locationUUID) {
        LocationDao locationDao = locationRepository.findByUUID(locationUUID);
        if (locationDao == null)
            throw new ResponseException("No location found for Id :" + locationUUID);
        return LocationDto.builder()
                .name(locationDao.getName())
                .uuid(locationDao.getUuid()).build();
    }

    @Override
    public Boolean deleteLocation(String locationUUID) {
        LocationDao locationDao = locationRepository.findByUUID(locationUUID);
        if (locationDao == null)
            throw new ResponseException("No location found for Id :" + locationUUID);
        locationRepository.delete(locationDao.getId());
        return true;
    }
}
