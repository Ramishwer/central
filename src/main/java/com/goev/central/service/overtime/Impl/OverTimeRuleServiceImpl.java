package com.goev.central.service.overtime.Impl;
import com.goev.central.dao.overtime.OverTimeRuleDao;
import com.goev.central.dto.overtime.OverTimeRuleDto;
import com.goev.central.repository.overtime.OverTimeRuleRepository;
import com.goev.central.service.overtime.OverTimeRuleService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OverTimeRuleServiceImpl implements OverTimeRuleService {

    private final OverTimeRuleRepository overTimeRuleRepository;

    @Override
    public OverTimeRuleDto createOverTimeRule(OverTimeRuleDto overTimeRuleDto){
        OverTimeRuleDao overTimeRuleDao = new OverTimeRuleDao();
        overTimeRuleDao.setRuleId(generateNextSerialNumber());
        overTimeRuleDao.setCity(overTimeRuleDto.getCity());
        overTimeRuleDao.setBusinessType(overTimeRuleDto.getBusinessType());
        overTimeRuleDao.setClientName(overTimeRuleDto.getClientName());
        overTimeRuleDao.setOvertimeAmount(overTimeRuleDto.getOverTimeAmount());
        overTimeRuleDao.setChecks(overTimeRuleDto.getChecks());
        overTimeRuleDao.setCheckValue(overTimeRuleDto.getCheckValue());
        overTimeRuleDao.setValidTill(overTimeRuleDto.getValidTill());
        overTimeRuleDao.setCreatedOn(overTimeRuleDto.getCreatedOn());
        overTimeRuleDao.setCreatedBy(overTimeRuleDto.getCreatedBy());
        overTimeRuleDao=overTimeRuleRepository.save(overTimeRuleDao);
        if (overTimeRuleDao == null) {
            throw new ResponseException("Error in saving EarningRule");
        }
        return OverTimeRuleDto.fromDao(overTimeRuleDao);
    }


    public String generateNextSerialNumber() {
        Optional<OverTimeRuleDao> latestRule = overTimeRuleRepository.findLastRuleIdInOverTimeRule();

        String newSerialNumber;
        System.out.println("latestRule"+latestRule);
        if (latestRule.isPresent()) {
            String lastSerial = latestRule.get().getRuleId();
            int lastNumber = Integer.parseInt(lastSerial.substring(2));
            int nextNumber = lastNumber + 1;
            DecimalFormat formatter = new DecimalFormat("RU000");
            newSerialNumber = formatter.format(nextNumber);
        } else {
            newSerialNumber = "RU001";
        }
        return newSerialNumber;
    }

}
