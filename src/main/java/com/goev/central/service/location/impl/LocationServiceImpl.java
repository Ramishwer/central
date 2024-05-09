package com.goev.central.service.location.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.location.LocationDetailDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.common.QrValueDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.location.OwnerDetailDto;
import com.goev.central.repository.location.LocationDetailRepository;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.service.location.LocationService;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationDetailRepository locationDetailRepository;

    private static LocationDao getLocationDao(LocationDto locationDto) {
        LocationDao locationDao = new LocationDao();
        locationDao.setName(locationDto.getName());
        locationDao.setLatitude(BigDecimal.valueOf(locationDto.getCoordinates().getLatitude()).floatValue());
        locationDao.setLongitude(BigDecimal.valueOf(locationDto.getCoordinates().getLongitude()).floatValue());
        locationDao.setType(locationDto.getType());
        return locationDao;
    }

    @Override
    public PaginatedResponseDto<LocationDto> getLocations() {
        PaginatedResponseDto<LocationDto> result = PaginatedResponseDto.<LocationDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<LocationDao> locationDaos = locationRepository.findAll();
        List<Integer> locationDetailsIds = locationDaos.stream().map(LocationDao::getLocationDetailsId).collect(Collectors.toList());
        List<LocationDetailDao> locationDetailDaoList = locationDetailRepository.findAllByIds(locationDetailsIds);
        Map<Integer, LocationDetailDao> locationDetailIdToLocationDaoMap = locationDetailDaoList.stream().collect(Collectors.toMap(LocationDetailDao::getId, Function.identity()));
        if (CollectionUtils.isEmpty(locationDaos))
            return result;

        for (LocationDao locationDao : locationDaos) {
            result.getElements().add(getLocationDto(locationDao, locationDetailIdToLocationDaoMap.get(locationDao.getLocationDetailsId())));
        }
        return result;
    }

    private LocationDto getLocationDto(LocationDao locationDao, LocationDetailDao locationDetailDao) {
        LocationDto result = LocationDto.builder()
                .name(locationDao.getName())
                .coordinates(LatLongDto.builder()
                        .latitude(locationDao.getLatitude().doubleValue())
                        .longitude(locationDao.getLongitude().doubleValue())
                        .build())
                .type(locationDao.getType())
                .uuid(locationDao.getUuid())
                .build();

        if (locationDetailDao != null) {
            result.setAddress(locationDetailDao.getAddress());
            result.setOwnerDetail(ApplicationConstants.GSON.fromJson(locationDetailDao.getOwnerDetails(), OwnerDetailDto.class));
        }

        return result;
    }

    @Override
    public LocationDto createLocation(LocationDto locationDto) {

        LocationDao locationDao = getLocationDao(locationDto);
        locationDao = locationRepository.save(locationDao);
        if (locationDao == null)
            throw new ResponseException("Error in saving location");

        LocationDetailDao locationDetailDao = getLocationDetailDao(locationDto, locationDao);
        locationDetailDao = locationDetailRepository.save(locationDetailDao);
        locationDao.setLocationDetailsId(locationDetailDao.getId());
        locationRepository.update(locationDao);
        return getLocationDto(locationDao, locationDetailDao);
    }

    private LocationDetailDao getLocationDetailDao(LocationDto locationDto, LocationDao locationDao) {
        LocationDetailDao locationDetailDao = new LocationDetailDao();
        locationDetailDao.setLocationId(locationDao.getId());

        locationDao.setLatitude(locationDto.getCoordinates().getLatitude().floatValue());
        locationDao.setLongitude(locationDto.getCoordinates().getLongitude().floatValue());
        if (locationDto.getOwnerDetail() != null)
            locationDetailDao.setOwnerDetails(ApplicationConstants.GSON.toJson(locationDto.getOwnerDetail()));
        locationDetailDao.setAddress(locationDto.getAddress());
        return locationDetailDao;
    }

    @Override
    public LocationDto updateLocation(String locationUUID, LocationDto locationDto) {
        LocationDao locationDao = locationRepository.findByUUID(locationUUID);
        if (locationDao == null)
            throw new ResponseException("No location location found for Id :" + locationUUID);
        LocationDao newLocationDao = getLocationDao(locationDto);

        newLocationDao.setId(locationDao.getId());
        newLocationDao.setUuid(locationDao.getUuid());
        newLocationDao = locationRepository.update(newLocationDao);
        if (newLocationDao == null)
            throw new ResponseException("Error in updating  location details ");
        LocationDetailDao locationDetailDao = locationDetailRepository.findById(locationDao.getLocationDetailsId());

        LocationDetailDao newLocationDetailDao = getLocationDetailDao(locationDto, newLocationDao);
        newLocationDetailDao.setLocationId(locationDetailDao.getLocationId());
        newLocationDetailDao = locationDetailRepository.save(newLocationDetailDao);
        locationDetailRepository.delete(locationDetailDao.getId());
        newLocationDao.setLocationDetailsId(newLocationDetailDao.getId());
        newLocationDao = locationRepository.update(newLocationDao);


        return getLocationDto(newLocationDao, newLocationDetailDao);
    }

    @Override
    public LocationDto getLocationDetails(String locationUUID) {
        LocationDao locationDao = locationRepository.findByUUID(locationUUID);
        if (locationDao == null)
            throw new ResponseException("No location found for Id :" + locationUUID);
        LocationDetailDao locationDetailDao = locationDetailRepository.findById(locationDao.getId());
        return getLocationDto(locationDao, locationDetailDao);
    }

    @Override
    public Boolean deleteLocation(String locationUUID) {
        LocationDao locationDao = locationRepository.findByUUID(locationUUID);
        if (locationDao == null)
            throw new ResponseException("No location found for Id :" + locationUUID);
        locationRepository.delete(locationDao.getId());
        return true;
    }

    @Override
    public String getLocationQr(String locationUUID) {
        LocationDao locationDao = locationRepository.findByUUID(locationUUID);
        if (locationDao == null)
            throw new ResponseException("No location found for Id :" + locationUUID);
        return ApplicationConstants.GSON.toJson(QrValueDto.builder()
                .entity("LOCATION")
                .timestamp(DateTime.now())
                .uuid(locationUUID)
                .name(locationDao.getName())
                .build());
    }
}
