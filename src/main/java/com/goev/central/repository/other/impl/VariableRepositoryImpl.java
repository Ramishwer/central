package com.goev.central.repository.other.impl;

import com.goev.central.dao.VariableDao;
import com.goev.central.repository.other.VariableRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VariablesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Variables.VARIABLES;

@Slf4j
@Repository
@AllArgsConstructor
public class VariableRepositoryImpl implements VariableRepository {

    private final DSLContext context;

    @Override
    public VariableDao save(VariableDao variable) {
        VariablesRecord variablesRecord = context.newRecord(VARIABLES, variable);
        variablesRecord.store();
        variable.setId(variablesRecord.getId());
        variable.setUuid(variablesRecord.getUuid());
        return variable;
    }

    @Override
    public VariableDao update(VariableDao variable) {
        VariablesRecord variablesRecord = context.newRecord(VARIABLES, variable);
        variablesRecord.update();
        return variable;
    }

    @Override
    public void delete(Integer id) {
        context.update(VARIABLES).set(VARIABLES.STATE, RecordState.DELETED.name()).where(VARIABLES.ID.eq(id)).execute();
    }

    @Override
    public VariableDao findByUUID(String uuid) {
        return context.selectFrom(VARIABLES).where(VARIABLES.UUID.eq(uuid)).fetchAnyInto(VariableDao.class);
    }

    @Override
    public VariableDao findById(Integer id) {
        return context.selectFrom(VARIABLES).where(VARIABLES.ID.eq(id)).fetchAnyInto(VariableDao.class);
    }

    @Override
    public List<VariableDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VARIABLES).where(VARIABLES.ID.in(ids)).fetchInto(VariableDao.class);
    }

    @Override
    public List<VariableDao> findAll() {
        return context.selectFrom(VARIABLES).fetchInto(VariableDao.class);
    }
}
