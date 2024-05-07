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
        engineRuleField.setUuid(engineRuleField.getUuid());
        return engineRuleField;
    }

    @Override
    public EngineRuleFieldDao update(EngineRuleFieldDao engineRuleField) {
        EngineRuleFieldsRecord engineRuleFieldsRecord = context.newRecord(ENGINE_RULE_FIELDS, engineRuleField);
        engineRuleFieldsRecord.update();
        return engineRuleField;
    }

    @Override
    public void delete(Integer id) {
        context.update(ENGINE_RULE_FIELDS).set(ENGINE_RULE_FIELDS.STATE, RecordState.DELETED.name()).where(ENGINE_RULE_FIELDS.ID.eq(id)).execute();
    }

    @Override
    public EngineRuleFieldDao findByUUID(String uuid) {
        return context.selectFrom(ENGINE_RULE_FIELDS).where(ENGINE_RULE_FIELDS.UUID.eq(uuid)).fetchAnyInto(EngineRuleFieldDao.class);
    }

    @Override
    public EngineRuleFieldDao findById(Integer id) {
        return context.selectFrom(ENGINE_RULE_FIELDS).where(ENGINE_RULE_FIELDS.ID.eq(id)).fetchAnyInto(EngineRuleFieldDao.class);
    }

    @Override
    public List<EngineRuleFieldDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ENGINE_RULE_FIELDS).where(ENGINE_RULE_FIELDS.ID.in(ids)).fetchInto(EngineRuleFieldDao.class);
    }

    @Override
    public List<EngineRuleFieldDao> findAll() {
        return context.selectFrom(ENGINE_RULE_FIELDS).fetchInto(EngineRuleFieldDao.class);
    }
}
