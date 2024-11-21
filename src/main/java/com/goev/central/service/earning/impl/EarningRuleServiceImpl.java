package com.goev.central.service.earning.impl;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.earning.EarningRuleDto;
import com.goev.central.repository.earning.EarningRuleRepository;
import com.goev.central.service.earning.EarningRuleService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class EarningRuleServiceImpl implements EarningRuleService {
    private final EarningRuleRepository earningRuleRepository;


    @Override
    public PaginatedResponseDto<EarningRuleDto> getEarningRules(){
        PaginatedResponseDto<EarningRuleDto> result = PaginatedResponseDto.<EarningRuleDto>builder().elements(new ArrayList<>()).build();
        List<EarningRuleDao> earningRuleDaos = earningRuleRepository.findAll();
        if (CollectionUtils.isEmpty(earningRuleDaos))
            return result;

        for (EarningRuleDao engineRuleDao :earningRuleDaos ) {
            result.getElements().add(EarningRuleDto.fromDao(engineRuleDao));
        }
        return result;
    }

    @Override
    public EarningRuleDto createEarningRule(EarningRuleDto earningRuleDto) {
        EarningRuleDao earningRuleDao = new EarningRuleDao();
        earningRuleDao.setRuleId(generateNextSerialNumber());
        earningRuleDao.setCity(earningRuleDto.getCity());
        earningRuleDao.setBusinessType(earningRuleDto.getBusinessType());
        earningRuleDao.setClientName(earningRuleDto.getClientName());
        earningRuleDao.setFixedIncome(earningRuleDto.getFixedIncome());
        earningRuleDao.setVariableIncome(earningRuleDto.getVariableIncome());
        earningRuleDao.setChecks(earningRuleDto.getChecks());
        earningRuleDao.setCheckValue(earningRuleDto.getCheckValue());
        earningRuleDao.setValidTill(earningRuleDto.getValidTill());
        earningRuleDao.setCreatedOn(earningRuleDto.getCreatedOn());
        earningRuleDao.setCreatedBy(earningRuleDto.getCreatedBy());
        earningRuleDao=earningRuleRepository.save(earningRuleDao);
        if (earningRuleDao == null) {
            throw new ResponseException("Error in saving EarningRule");
        }
        return EarningRuleDto.fromDao(earningRuleDao);
    }

    @Override
    public EarningRuleDto updateEarningRule (EarningRuleDto earningRuleDto , String earningRuleUUID){
        EarningRuleDao earningRuleDao = earningRuleRepository.findByUUID(earningRuleUUID);
        if (earningRuleDao == null)
            throw new ResponseException("No EarningRule  found for Id :" + earningRuleUUID);

        EarningRuleDao newEarningRuleDao = new EarningRuleDao();
        newEarningRuleDao.setId(earningRuleDao.getId());
        newEarningRuleDao.setUuid(earningRuleDao.getUuid());
        newEarningRuleDao.setRuleId(earningRuleDao.getRuleId());
        newEarningRuleDao.setCreatedBy(earningRuleDao.getCreatedBy());

        newEarningRuleDao.setCity(earningRuleDto.getCity());
        newEarningRuleDao.setBusinessType(earningRuleDto.getBusinessType());
        newEarningRuleDao.setClientName(earningRuleDto.getClientName());
        newEarningRuleDao.setFixedIncome(earningRuleDto.getFixedIncome());
        newEarningRuleDao.setVariableIncome(earningRuleDto.getVariableIncome());
        newEarningRuleDao.setChecks(earningRuleDto.getChecks());
        newEarningRuleDao.setCheckValue(earningRuleDto.getCheckValue());
        newEarningRuleDao.setValidTill(earningRuleDto.getValidTill());
        newEarningRuleDao.setUpdatedOn(earningRuleDto.getUpdatedOn());
        newEarningRuleDao.setUpdatedBy(earningRuleDto.getUpdatedBy());
        newEarningRuleDao.setIsActive(earningRuleDto.getIsActive());
        earningRuleDao = earningRuleRepository.update(newEarningRuleDao);

        if (earningRuleDao == null)
            throw new ResponseException("Error in updating details EarningRule");
        return EarningRuleDto.fromDao(earningRuleDao);
    }


    public String generateNextSerialNumber() {
        Optional<EarningRuleDao> latestRule = earningRuleRepository.findLastRuleIdInEarningRule();

        String newSerialNumber;
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

    @Override
    public EarningRuleDto getEarningRuleDetails(String earningRuleUUID){
        EarningRuleDao earningRuleDao = earningRuleRepository.findByUUID(earningRuleUUID);
        if (earningRuleDao == null)
            throw new ResponseException("No Earning Rule found for Id :" + earningRuleUUID);
        return EarningRuleDto.fromDao(earningRuleDao);
    }

    @Override
    public boolean inactiveEarningRule(String earningRuleUUID){
        EarningRuleDao earningRuleDao = earningRuleRepository.findByUUID(earningRuleUUID);
        if (earningRuleDao == null)
            throw new ResponseException("No Earning Rule found for Id :" + earningRuleUUID);
        earningRuleRepository.delete(earningRuleDao.getId());
        return true;
    }
}
