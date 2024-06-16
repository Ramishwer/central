package com.goev.central.service.region.impl;

import com.goev.central.dao.region.RegionTypeDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.region.RegionTypeDto;
import com.goev.central.repository.region.RegionTypeRepository;
import com.goev.central.service.region.RegionTypeService;
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
public class RegionTypeServiceImpl implements RegionTypeService {

    private final RegionTypeRepository regionTypeRepository;

    @Override
    public PaginatedResponseDto<RegionTypeDto> getRegionTypes() {
        PaginatedResponseDto<RegionTypeDto> result = PaginatedResponseDto.<RegionTypeDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<RegionTypeDao> regionTypeDaos = regionTypeRepository.findAllActive();
        if (CollectionUtils.isEmpty(regionTypeDaos))
            return result;

        for (RegionTypeDao regionTypeDao : regionTypeDaos) {
            result.getElements().add(RegionTypeDto.builder()
                    .uuid(regionTypeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public RegionTypeDto createRegionType(RegionTypeDto regionTypeDto) {

        RegionTypeDao regionTypeDao = new RegionTypeDao();
        regionTypeDao = regionTypeRepository.save(regionTypeDao);
        if (regionTypeDao == null)
            throw new ResponseException("Error in saving regionType regionType");
        return RegionTypeDto.builder()
                .uuid(regionTypeDao.getUuid()).build();
    }

    @Override
    public RegionTypeDto updateRegionType(String regionTypeUUID, RegionTypeDto regionTypeDto) {
        RegionTypeDao regionTypeDao = regionTypeRepository.findByUUID(regionTypeUUID);
        if (regionTypeDao == null)
            throw new ResponseException("No regionType  found for Id :" + regionTypeUUID);
        RegionTypeDao newRegionTypeDao = new RegionTypeDao();


        newRegionTypeDao.setId(regionTypeDao.getId());
        newRegionTypeDao.setUuid(regionTypeDao.getUuid());
        regionTypeDao = regionTypeRepository.update(newRegionTypeDao);
        if (regionTypeDao == null)
            throw new ResponseException("Error in updating details regionType ");
        return RegionTypeDto.builder()
                .uuid(regionTypeDao.getUuid()).build();
    }

    @Override
    public RegionTypeDto getRegionTypeDetails(String regionTypeUUID) {
        RegionTypeDao regionTypeDao = regionTypeRepository.findByUUID(regionTypeUUID);
        if (regionTypeDao == null)
            throw new ResponseException("No regionType  found for Id :" + regionTypeUUID);
        return RegionTypeDto.builder()
                .uuid(regionTypeDao.getUuid()).build();
    }

    @Override
    public Boolean deleteRegionType(String regionTypeUUID) {
        RegionTypeDao regionTypeDao = regionTypeRepository.findByUUID(regionTypeUUID);
        if (regionTypeDao == null)
            throw new ResponseException("No regionType  found for Id :" + regionTypeUUID);
        regionTypeRepository.delete(regionTypeDao.getId());
        return true;
    }
}
