package com.goev.central.repository.overtime.Impl;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.overtime.OverTimeRuleDao;
import com.goev.central.repository.overtime.OverTimeRuleRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.record.central.tables.records.OvertimeRuleRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.goev.record.central.tables.EarningRule.EARNING_RULE;
import static com.goev.record.central.tables.OvertimeRule.OVERTIME_RULE;

@Slf4j
@Repository
@AllArgsConstructor

public class OverTimeRuleRepositoryImpl implements OverTimeRuleRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public OverTimeRuleDao save(OverTimeRuleDao overTimeRuleDao){
        OvertimeRuleRecord overtimeRuleRecord = context.newRecord(OVERTIME_RULE, overTimeRuleDao);
        overtimeRuleRecord.setRuleId(overTimeRuleDao.getRuleId());
        overtimeRuleRecord.store();
        overTimeRuleDao.setUuid(overtimeRuleRecord.getUuid());
        overTimeRuleDao.setRuleId(overtimeRuleRecord.getRuleId());
        overTimeRuleDao.setCity(overtimeRuleRecord.getCity());
        overTimeRuleDao.setBusinessType(overtimeRuleRecord.getBusinessType());
        overTimeRuleDao.setClientName(overtimeRuleRecord.getClientName());
        overTimeRuleDao.setOvertimeAmount(overtimeRuleRecord.getOvertimeAmount());
        overTimeRuleDao.setChecks(overtimeRuleRecord.getChecks());
        overTimeRuleDao.setCheckValue(overtimeRuleRecord.getCheckValue());
        overTimeRuleDao.setValidTill(overtimeRuleRecord.getValidTill());
        overTimeRuleDao.setIsActive(overtimeRuleRecord.getIsActive());
        overTimeRuleDao.setCreatedOn(overtimeRuleRecord.getCreatedOn());
        overTimeRuleDao.setCreatedBy(overtimeRuleRecord.getCreatedBy());
       if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("OverTimeRuleSaveEvent", overTimeRuleDao);
        return overTimeRuleDao;
    }

    @Override
    public Optional<OverTimeRuleDao> findLastRuleIdInOverTimeRule (){
        return context.selectFrom(OVERTIME_RULE)
                .orderBy(OVERTIME_RULE.RULE_ID.desc())
                .limit(1)
                .fetchOptionalInto(OverTimeRuleDao.class);
    }
}
