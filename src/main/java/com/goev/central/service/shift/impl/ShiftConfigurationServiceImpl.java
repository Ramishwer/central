package com.goev.central.service.shift.impl;

import com.goev.central.dao.shift.ShiftConfigurationDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.repository.shift.ShiftConfigurationRepository;
import com.goev.central.service.shift.ShiftConfigurationService;
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
public class ShiftConfigurationServiceImpl implements ShiftConfigurationService {

    private final ShiftConfigurationRepository shiftConfigurationRepository;

    @Override
    public PaginatedResponseDto<ShiftConfigurationDto> getShiftConfigurations(String customerUUID) {
        PaginatedResponseDto<ShiftConfigurationDto> result = PaginatedResponseDto.<ShiftConfigurationDto>builder().elements(new ArrayList<>()).build();
        List<ShiftConfigurationDao> shiftConfigurationDaos = shiftConfigurationRepository.findAllActive();
        if (CollectionUtils.isEmpty(shiftConfigurationDaos))
            return result;

        for (ShiftConfigurationDao shiftConfigurationDao : shiftConfigurationDaos) {
            result.getElements().add(ShiftConfigurationDto.builder()
                    .uuid(shiftConfigurationDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public ShiftConfigurationDto createShiftConfiguration(String customerUUID, ShiftConfigurationDto shiftConfigurationDto) {

        ShiftConfigurationDao shiftConfigurationDao = new ShiftConfigurationDao();
        shiftConfigurationDao = shiftConfigurationRepository.save(shiftConfigurationDao);
        if (shiftConfigurationDao == null)
            throw new ResponseException("Error in saving shiftConfiguration shiftConfiguration");
        return ShiftConfigurationDto.builder()
                .uuid(shiftConfigurationDao.getUuid()).build();
    }

    @Override
    public ShiftConfigurationDto updateShiftConfiguration(String customerUUID, String shiftConfigurationUUID, ShiftConfigurationDto shiftConfigurationDto) {
        ShiftConfigurationDao shiftConfigurationDao = shiftConfigurationRepository.findByUUID(shiftConfigurationUUID);
        if (shiftConfigurationDao == null)
            throw new ResponseException("No shiftConfiguration  found for Id :" + shiftConfigurationUUID);
        ShiftConfigurationDao newShiftConfigurationDao = new ShiftConfigurationDao();


        newShiftConfigurationDao.setId(shiftConfigurationDao.getId());
        newShiftConfigurationDao.setUuid(shiftConfigurationDao.getUuid());
        shiftConfigurationDao = shiftConfigurationRepository.update(newShiftConfigurationDao);
        if (shiftConfigurationDao == null)
            throw new ResponseException("Error in updating details shiftConfiguration ");
        return ShiftConfigurationDto.builder()
                .uuid(shiftConfigurationDao.getUuid()).build();
    }

    @Override
    public ShiftConfigurationDto getShiftConfigurationDetails(String customerUUID, String shiftConfigurationUUID) {
        ShiftConfigurationDao shiftConfigurationDao = shiftConfigurationRepository.findByUUID(shiftConfigurationUUID);
        if (shiftConfigurationDao == null)
            throw new ResponseException("No shiftConfiguration  found for Id :" + shiftConfigurationUUID);
        return ShiftConfigurationDto.builder()
                .uuid(shiftConfigurationDao.getUuid()).build();
    }

    @Override
    public Boolean deleteShiftConfiguration(String customerUUID, String shiftConfigurationUUID) {
        ShiftConfigurationDao shiftConfigurationDao = shiftConfigurationRepository.findByUUID(shiftConfigurationUUID);
        if (shiftConfigurationDao == null)
            throw new ResponseException("No shiftConfiguration  found for Id :" + shiftConfigurationUUID);
        shiftConfigurationRepository.delete(shiftConfigurationDao.getId());
        return true;
    }
}
