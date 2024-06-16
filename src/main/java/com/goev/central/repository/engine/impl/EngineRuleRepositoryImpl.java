package com.goev.central.repository.engine.impl;

import com.goev.central.dao.engine.EngineRuleDao;
import com.goev.central.repository.engine.EngineRuleRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.EngineRulesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.EngineRules.ENGINE_RULES;

@Slf4j
@Repository
@AllArgsConstructor
public class EngineRuleRepositoryImpl implements EngineRuleRepository {

    private final DSLContext context;

    @Override
    public EngineRuleDao save(EngineRuleDao engineRule) {
        EngineRulesRecord engineRulesRecord = context.newRecord(ENGINE_RULES, engineRule);
        engineRulesRecord.store();
        engineRule.setId(engineRulesRecord.getId());
        engineRule.setUuid(engineRule.getUuid());
        engineRule.setCreatedBy(engineRule.getCreatedBy());
        engineRule.setUpdatedBy(engineRule.getUpdatedBy());
        engineRule.setCreatedOn(engineRule.getCreatedOn());
        engineRule.setUpdatedOn(engineRule.getUpdatedOn());
        engineRule.setIsActive(engineRule.getIsActive());
        engineRule.setState(engineRule.getState());
        engineRule.setApiSource(engineRule.getApiSource());
        engineRule.setNotes(engineRule.getNotes());
        return engineRule;
    }

    @Override
    public EngineRuleDao update(EngineRuleDao engineRule) {
        EngineRulesRecord engineRulesRecord = context.newRecord(ENGINE_RULES, engineRule);
        engineRulesRecord.update();


        engineRule.setCreatedBy(engineRulesRecord.getCreatedBy());
        engineRule.setUpdatedBy(engineRulesRecord.getUpdatedBy());
        engineRule.setCreatedOn(engineRulesRecord.getCreatedOn());
        engineRule.setUpdatedOn(engineRulesRecord.getUpdatedOn());
        engineRule.setIsActive(engineRulesRecord.getIsActive());
        engineRule.setState(engineRulesRecord.getState());
        engineRule.setApiSource(engineRulesRecord.getApiSource());
        engineRule.setNotes(engineRulesRecord.getNotes());
        return engineRule;
    }

    @Override
    public void delete(Integer id) {
        context.update(ENGINE_RULES)
                .set(ENGINE_RULES.STATE, RecordState.DELETED.name())
                .where(ENGINE_RULES.ID.eq(id))
                .and(ENGINE_RULES.STATE.eq(RecordState.ACTIVE.name()))
                .and(ENGINE_RULES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public EngineRuleDao findByUUID(String uuid) {
        return context.selectFrom(ENGINE_RULES).where(ENGINE_RULES.UUID.eq(uuid))
                .and(ENGINE_RULES.IS_ACTIVE.eq(true))
                .fetchAnyInto(EngineRuleDao.class);
    }

    @Override
    public EngineRuleDao findById(Integer id) {
        return context.selectFrom(ENGINE_RULES).where(ENGINE_RULES.ID.eq(id))
                .and(ENGINE_RULES.IS_ACTIVE.eq(true))
                .fetchAnyInto(EngineRuleDao.class);
    }

    @Override
    public List<EngineRuleDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ENGINE_RULES).where(ENGINE_RULES.ID.in(ids)).fetchInto(EngineRuleDao.class);
    }

    @Override
    public List<EngineRuleDao> findAllActive() {
        return context.selectFrom(ENGINE_RULES).fetchInto(EngineRuleDao.class);
    }
}
