package com.goev.central.repository.earning.impl;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.engine.EngineRuleDao;
import com.goev.central.dao.engine.EngineRuleFieldDao;
import com.goev.central.dto.earning.EarningRuleDto;
import com.goev.central.repository.earning.EarningRuleRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.record.central.tables.EarningRule;
import com.goev.record.central.tables.records.EarningRuleRecord;
import com.goev.record.central.tables.records.EngineRuleFieldsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.goev.record.central.tables.EarningRule.EARNING_RULE;
import static com.goev.record.central.tables.EngineRuleFields.ENGINE_RULE_FIELDS;
import static com.goev.record.central.tables.EngineRules.ENGINE_RULES;

@Slf4j
@Repository
@AllArgsConstructor
public class EarningRuleRepositoryImpl implements EarningRuleRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public EarningRuleDao save(EarningRuleDao earningRuleDao){
        EarningRuleRecord earningRuleRecord = context.newRecord(EARNING_RULE, earningRuleDao);
        earningRuleRecord.setRuleId(earningRuleDao.getRuleId());
        earningRuleRecord.store();
        earningRuleDao.setUuid(earningRuleRecord.getUuid());
        earningRuleDao.setRuleId(earningRuleRecord.getRuleId());
        earningRuleDao.setCity(earningRuleRecord.getCity());
        earningRuleDao.setBusinessType(earningRuleRecord.getBusinessType());
        earningRuleDao.setClientName(earningRuleRecord.getClientName());
        earningRuleDao.setFixedIncome(earningRuleRecord.getFixedIncome());
        earningRuleDao.setVariableIncome(earningRuleRecord.getVariableIncome());
        earningRuleDao.setChecks(earningRuleRecord.getChecks());
        earningRuleDao.setCheckValue(earningRuleRecord.getCheckValue());
        earningRuleDao.setValidTill(earningRuleRecord.getValidTill());
        earningRuleDao.setIsActive(earningRuleRecord.getIsActive());
        earningRuleDao.setCreatedOn(earningRuleRecord.getCreatedOn());
        earningRuleDao.setCreatedBy(earningRuleRecord.getCreatedBy());
        return earningRuleDao;
    }

    @Override
    public EarningRuleDao update(EarningRuleDao newEarningRuleDao){
        EarningRuleRecord earningRuleRecord = context.newRecord(EARNING_RULE, newEarningRuleDao);
        earningRuleRecord.update();
        newEarningRuleDao.setUuid(earningRuleRecord.getUuid());
        newEarningRuleDao.setRuleId(earningRuleRecord.getRuleId());
        newEarningRuleDao.setCity(earningRuleRecord.getCity());
        newEarningRuleDao.setBusinessType(earningRuleRecord.getBusinessType());
        newEarningRuleDao.setClientName(earningRuleRecord.getClientName());
        newEarningRuleDao.setFixedIncome(earningRuleRecord.getFixedIncome());
        newEarningRuleDao.setVariableIncome(earningRuleRecord.getVariableIncome());
        newEarningRuleDao.setChecks(earningRuleRecord.getChecks());
        newEarningRuleDao.setCheckValue(earningRuleRecord.getCheckValue());
        newEarningRuleDao.setValidTill(earningRuleRecord.getValidTill());
        newEarningRuleDao.setIsActive(earningRuleRecord.getIsActive());
        newEarningRuleDao.setCreatedOn(earningRuleRecord.getCreatedOn());
        newEarningRuleDao.setCreatedBy(earningRuleRecord.getCreatedBy());
        return newEarningRuleDao;
    }

    @Override
    public EarningRuleDao findByUUID(String uuid){
        return context.selectFrom(EARNING_RULE).where(EARNING_RULE.UUID.eq(uuid))
                .fetchAnyInto(EarningRuleDao.class);

    }
    @Override
    public Optional<EarningRuleDao> findLastRuleIdInEarningRule (){
        return context.selectFrom(EARNING_RULE)
                .orderBy(EARNING_RULE.ID.desc())
                .limit(1)
                .fetchOptionalInto(EarningRuleDao.class);
    }

    @Override
    public List<EarningRuleDao> findAll(){
        return context.selectFrom(EARNING_RULE).fetchInto(EarningRuleDao.class);
    }

    @Override
    public void delete(Integer id){
        context.update(EARNING_RULE)
                .set(EARNING_RULE.IS_ACTIVE, false)
                .where(EARNING_RULE.ID.eq(id))
                .execute();
    }
}
