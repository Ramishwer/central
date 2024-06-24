package com.goev.central.repository.engine.impl;

import com.goev.central.dao.engine.EngineRuleFieldDao;
import com.goev.central.repository.engine.EngineRuleFieldRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.EngineRuleFieldsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.EngineRuleFields.ENGINE_RULE_FIELDS;

@Slf4j
@Repository
@AllArgsConstructor
public class EngineRuleFieldRepositoryImpl implements EngineRuleFieldRepository {

    private final DSLContext context;

    @Override
    public EngineRuleFieldDao save(EngineRuleFieldDao engineRuleField) {
        EngineRuleFieldsRecord engineRuleFieldsRecord = context.newRecord(ENGINE_RULE_FIELDS, engineRuleField);
        engineRuleFieldsRecord.store();
        engineRuleField.setId(engineRuleFieldsRecord.getId());
        engineRuleField.setUuid(engineRuleFieldsRecord.getUuid());
        engineRuleField.setCreatedBy(engineRuleFieldsRecord.getCreatedBy());
        engineRuleField.setUpdatedBy(engineRuleFieldsRecord.getUpdatedBy());
        engineRuleField.setCreatedOn(engineRuleFieldsRecord.getCreatedOn());
        engineRuleField.setUpdatedOn(engineRuleFieldsRecord.getUpdatedOn());
        engineRuleField.setIsActive(engineRuleFieldsRecord.getIsActive());
        engineRuleField.setState(engineRuleFieldsRecord.getState());
        engineRuleField.setApiSource(engineRuleFieldsRecord.getApiSource());
        engineRuleField.setNotes(engineRuleFieldsRecord.getNotes());
        return engineRuleField;
    }

    @Override
    public EngineRuleFieldDao update(EngineRuleFieldDao engineRuleField) {
        EngineRuleFieldsRecord engineRuleFieldsRecord = context.newRecord(ENGINE_RULE_FIELDS, engineRuleField);
        engineRuleFieldsRecord.update();


        engineRuleField.setCreatedBy(engineRuleFieldsRecord.getCreatedBy());
        engineRuleField.setUpdatedBy(engineRuleFieldsRecord.getUpdatedBy());
        engineRuleField.setCreatedOn(engineRuleFieldsRecord.getCreatedOn());
        engineRuleField.setUpdatedOn(engineRuleFieldsRecord.getUpdatedOn());
        engineRuleField.setIsActive(engineRuleFieldsRecord.getIsActive());
        engineRuleField.setState(engineRuleFieldsRecord.getState());
        engineRuleField.setApiSource(engineRuleFieldsRecord.getApiSource());
        engineRuleField.setNotes(engineRuleFieldsRecord.getNotes());
        return engineRuleField;
    }

    @Override
    public void delete(Integer id) {
        context.update(ENGINE_RULE_FIELDS)
                .set(ENGINE_RULE_FIELDS.STATE, RecordState.DELETED.name())
                .where(ENGINE_RULE_FIELDS.ID.eq(id))
                .and(ENGINE_RULE_FIELDS.STATE.eq(RecordState.ACTIVE.name()))
                .and(ENGINE_RULE_FIELDS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public EngineRuleFieldDao findByUUID(String uuid) {
        return context.selectFrom(ENGINE_RULE_FIELDS).where(ENGINE_RULE_FIELDS.UUID.eq(uuid))
                .and(ENGINE_RULE_FIELDS.IS_ACTIVE.eq(true))
                .fetchAnyInto(EngineRuleFieldDao.class);
    }

    @Override
    public EngineRuleFieldDao findById(Integer id) {
        return context.selectFrom(ENGINE_RULE_FIELDS).where(ENGINE_RULE_FIELDS.ID.eq(id))
                .and(ENGINE_RULE_FIELDS.IS_ACTIVE.eq(true))
                .fetchAnyInto(EngineRuleFieldDao.class);
    }

    @Override
    public List<EngineRuleFieldDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ENGINE_RULE_FIELDS).where(ENGINE_RULE_FIELDS.ID.in(ids)).fetchInto(EngineRuleFieldDao.class);
    }

    @Override
    public List<EngineRuleFieldDao> findAllActive() {
        return context.selectFrom(ENGINE_RULE_FIELDS).fetchInto(EngineRuleFieldDao.class);
    }
}
