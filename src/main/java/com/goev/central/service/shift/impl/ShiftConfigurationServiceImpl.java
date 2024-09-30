package com.goev.central.service.shift.impl;

import com.goev.central.dao.shift.ShiftConfigurationDao;
import com.goev.central.dao.shift.ShiftDao;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.dto.shift.ShiftDto;
import com.goev.central.repository.payout.PayoutModelRepository;
import com.goev.central.repository.shift.ShiftConfigurationRepository;
import com.goev.central.repository.shift.ShiftRepository;
import com.goev.central.service.shift.ShiftConfigurationService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ShiftConfigurationServiceImpl implements ShiftConfigurationService {

    private final ShiftConfigurationRepository shiftConfigurationRepository;
    private final ShiftRepository shiftRepository;
    private final PayoutModelRepository payoutModelRepository;

    @Override
    public Map<String, ShiftConfigurationDto> getShiftConfigurations(String shiftUUID) {
        ShiftDao shiftDao = shiftRepository.findByUUID(shiftUUID);
        if (shiftDao == null)
            throw new ResponseException("No shift  found for Id :" + shiftUUID);

        List<ShiftConfigurationDao> shiftConfigurationDaoList = shiftConfigurationRepository.findByShiftId(shiftDao.getId());
        if (CollectionUtils.isEmpty(shiftConfigurationDaoList))
            return Collections.emptyMap();

        List<ShiftConfigurationDto> shiftConfigurationDtoList = shiftConfigurationDaoList.stream().map(x -> ShiftConfigurationDto.fromDao(x, ShiftDto.fromDao(shiftDao))).toList();
        return shiftConfigurationDtoList.stream().collect(Collectors.toMap(ShiftConfigurationDto::getDay, Function.identity()));

    }

    @Override
    public Map<String, ShiftConfigurationDto> createShiftConfiguration(String shiftUUID, Map<String, ShiftConfigurationDto> shiftConfiguration) {

        ShiftDao shiftDao = shiftRepository.findByUUID(shiftUUID);
        if (shiftDao == null)
            throw new ResponseException("No shift  found for Id :" + shiftUUID);

        List<ShiftConfigurationDao> shiftConfigurationDaoList = new ArrayList<>();

        for (Map.Entry<String, ShiftConfigurationDto> entry : shiftConfiguration.entrySet()) {
            ShiftConfigurationDto configurationDto = entry.getValue();
//            PayoutModelDao payoutModelDao = payoutModelRepository.findByUUID(configurationDto.getPayoutModel().getUuid());

            ShiftConfigurationDao shiftConfigurationDao = ShiftConfigurationDao.fromDto(configurationDto, shiftDao.getId(), null);
            shiftConfigurationDao.setDay(entry.getKey());
            shiftConfigurationDao = shiftConfigurationRepository.save(shiftConfigurationDao);
            if (shiftConfigurationDao == null) {
                log.error("Error in saving shiftConfiguration shiftConfiguration");
                continue;
            }
            shiftConfigurationDaoList.add(shiftConfigurationDao);

        }
        List<ShiftConfigurationDto> shiftConfigurationDtoList = shiftConfigurationDaoList.stream().map(x -> ShiftConfigurationDto.fromDao(x, ShiftDto.fromDao(shiftDao))).toList();
        return shiftConfigurationDtoList.stream().collect(Collectors.toMap(ShiftConfigurationDto::getDay, Function.identity()));

    }

    @Override
    public ShiftConfigurationDto updateShiftConfiguration(String shiftUUID, String shiftConfigurationUUID, ShiftConfigurationDto shiftConfigurationDto) {
        ShiftDao shiftDao = shiftRepository.findByUUID(shiftUUID);
        if (shiftDao == null)
            throw new ResponseException("No shift  found for Id :" + shiftUUID);

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
    public Boolean deleteShiftConfiguration(String shiftUUID, String shiftConfigurationUUID) {
        ShiftDao shiftDao = shiftRepository.findByUUID(shiftUUID);
        if (shiftDao == null)
            throw new ResponseException("No shift  found for Id :" + shiftUUID);

        ShiftConfigurationDao shiftConfigurationDao = shiftConfigurationRepository.findByUUID(shiftConfigurationUUID);
        if (shiftConfigurationDao == null)
            throw new ResponseException("No shiftConfiguration  found for Id :" + shiftConfigurationUUID);
        shiftConfigurationRepository.delete(shiftConfigurationDao.getId());
        return true;
    }
}
