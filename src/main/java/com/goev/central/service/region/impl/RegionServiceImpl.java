package com.goev.central.service.region.impl;

import com.goev.central.dao.region.RegionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.region.RegionDto;
import com.goev.central.repository.region.RegionRepository;
import com.goev.central.service.region.RegionService;
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
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public PaginatedResponseDto<RegionDto> getRegions() {
        PaginatedResponseDto<RegionDto> result = PaginatedResponseDto.<RegionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<RegionDao> regionDaos = regionRepository.findAllActive();
        if (CollectionUtils.isEmpty(regionDaos))
            return result;

        for (RegionDao regionDao : regionDaos) {
            result.getElements().add(RegionDto.builder()
                    .uuid(regionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public RegionDto createRegion(RegionDto regionDto) {

        RegionDao regionDao = new RegionDao();
        regionDao = regionRepository.save(regionDao);
        if (regionDao == null)
            throw new ResponseException("Error in saving region region");
        return RegionDto.builder()
                .uuid(regionDao.getUuid()).build();
    }

    @Override
    public RegionDto updateRegion(String regionUUID, RegionDto regionDto) {
        RegionDao regionDao = regionRepository.findByUUID(regionUUID);
        if (regionDao == null)
            throw new ResponseException("No region  found for Id :" + regionUUID);
        RegionDao newRegionDao = new RegionDao();


        newRegionDao.setId(regionDao.getId());
        newRegionDao.setUuid(regionDao.getUuid());
        regionDao = regionRepository.update(newRegionDao);
        if (regionDao == null)
            throw new ResponseException("Error in updating details region ");
        return RegionDto.builder()
                .uuid(regionDao.getUuid()).build();
    }

    @Override
    public RegionDto getRegionDetails(String regionUUID) {
        RegionDao regionDao = regionRepository.findByUUID(regionUUID);
        if (regionDao == null)
            throw new ResponseException("No region  found for Id :" + regionUUID);
        return RegionDto.builder()
                .uuid(regionDao.getUuid()).build();
    }

    @Override
    public Boolean deleteRegion(String regionUUID) {
        RegionDao regionDao = regionRepository.findByUUID(regionUUID);
        if (regionDao == null)
            throw new ResponseException("No region  found for Id :" + regionUUID);
        regionRepository.delete(regionDao.getId());
        return true;
    }
}
