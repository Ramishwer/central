package com.goev.central.service.earning.impl;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.earning.EarningRuleDto;
import com.goev.central.repository.earning.EarningRuleRepository;
import com.goev.central.repository.user.detail.UserDetailRepository;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.service.earning.EarningRuleService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EarningRuleServiceImpl implements EarningRuleService {
    private final EarningRuleRepository earningRuleRepository;
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    @Override
    public PaginatedResponseDto<EarningRuleDto> getEarningRules(String status, DateTime from, DateTime to){
        PaginatedResponseDto<EarningRuleDto> result = PaginatedResponseDto.<EarningRuleDto>builder().elements(new ArrayList<>()).build();
        List<EarningRuleDao> earningRuleDaos = earningRuleRepository.findAllBySatusAndDateRange(status, from, to);
        if (CollectionUtils.isEmpty(earningRuleDaos))
            return result;

        Set<String> createdByUuids = earningRuleDaos.stream()
                .map(EarningRuleDao::getCreatedBy)
                .collect(Collectors.toSet());

        Map<String, Integer> uuidToDetailIdMap = userRepository.findAllUserDetailsIdsByUUID(createdByUuids);
        List<Integer> userDetailsIds = new ArrayList<>(uuidToDetailIdMap.values());
        List<String> userNames = userDetailRepository.findUserNameByUserDetailsIds(userDetailsIds);

        Map<Integer, String> detailIdToUserNameMap = new HashMap<>();
        for (int i = 0; i < userDetailsIds.size(); i++) {
            detailIdToUserNameMap.put(userDetailsIds.get(i), userNames.get(i));
        }

        Map<String, String> uuidToUserNameMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : uuidToDetailIdMap.entrySet()) {
            String uuid = entry.getKey();
            Integer userDetailsId = entry.getValue();
            String userName = detailIdToUserNameMap.get(userDetailsId);
            uuidToUserNameMap.put(uuid, userName);
        }
        for (EarningRuleDao engineRuleDao :earningRuleDaos ) {
            String createdByUuid = engineRuleDao.getCreatedBy();
            if (uuidToUserNameMap.containsKey(createdByUuid)) {
                String createdByName = uuidToUserNameMap.get(createdByUuid);
                engineRuleDao.setCreatedBy(createdByName);
            }
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
