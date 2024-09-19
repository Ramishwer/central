package com.goev.central.service.shift.impl;

import com.goev.central.dao.shift.ShiftDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.shift.ShiftDto;
import com.goev.central.repository.shift.ShiftRepository;
import com.goev.central.service.shift.ShiftService;
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
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Override
    public PaginatedResponseDto<ShiftDto> getShifts() {
        PaginatedResponseDto<ShiftDto> result = PaginatedResponseDto.<ShiftDto>builder().elements(new ArrayList<>()).build();
        List<ShiftDao> shiftDaos = shiftRepository.findAllActive();
        if (CollectionUtils.isEmpty(shiftDaos))
            return result;

        for (ShiftDao shiftDao : shiftDaos) {
            result.getElements().add(ShiftDto.fromDao(shiftDao));
        }
        return result;
    }

    @Override
    public ShiftDto createShift(ShiftDto shiftDto) {

        ShiftDao shiftDao = new ShiftDao();

        shiftDao.setName(shiftDto.getName());
        shiftDao.setDescription(shiftDto.getDescription());
        shiftDao.setShiftType(shiftDto.getShiftType());
        shiftDao = shiftRepository.save(shiftDao);
        if (shiftDao == null)
            throw new ResponseException("Error in saving shift shift");
        return ShiftDto.fromDao(shiftDao);
    }

    @Override
    public ShiftDto updateShift(String shiftUUID, ShiftDto shiftDto) {
        ShiftDao shiftDao = shiftRepository.findByUUID(shiftUUID);
        if (shiftDao == null)
            throw new ResponseException("No shift  found for Id :" + shiftUUID);
        ShiftDao newShiftDao = new ShiftDao();
        newShiftDao.setName(shiftDto.getName());
        newShiftDao.setDescription(shiftDto.getDescription());
        newShiftDao.setShiftType(shiftDto.getShiftType());

        newShiftDao.setId(shiftDao.getId());
        newShiftDao.setUuid(shiftDao.getUuid());
        shiftDao = shiftRepository.update(newShiftDao);
        if (shiftDao == null)
            throw new ResponseException("Error in updating details shift ");
        return ShiftDto.fromDao(shiftDao);
    }

    @Override
    public ShiftDto getShiftDetails(String shiftUUID) {
        ShiftDao shiftDao = shiftRepository.findByUUID(shiftUUID);
        if (shiftDao == null)
            throw new ResponseException("No shift  found for Id :" + shiftUUID);
        return ShiftDto.fromDao(shiftDao);
    }

    @Override
    public Boolean deleteShift(String shiftUUID) {
        ShiftDao shiftDao = shiftRepository.findByUUID(shiftUUID);
        if (shiftDao == null)
            throw new ResponseException("No shift  found for Id :" + shiftUUID);
        shiftRepository.delete(shiftDao.getId());
        return true;
    }
}
