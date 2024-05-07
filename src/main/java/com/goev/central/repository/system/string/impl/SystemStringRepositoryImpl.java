package com.goev.central.repository.system.string.impl;

import com.goev.central.dao.system.string.SystemStringDao;
import com.goev.central.repository.system.string.SystemStringRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.SystemStringsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.goev.record.central.tables.SystemStrings.SYSTEM_STRINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class SystemStringRepositoryImpl implements SystemStringRepository {
    private final DSLContext context;

    @Override
    public SystemStringDao save(SystemStringDao string) {
        SystemStringsRecord systemStringsRecord = context.newRecord(SYSTEM_STRINGS, string);
        systemStringsRecord.store();
        string.setId(systemStringsRecord.getId());
        string.setUuid(systemStringsRecord.getUuid());
        return string;
    }

    @Override
    public SystemStringDao update(SystemStringDao string) {
        SystemStringsRecord systemStringsRecord = context.newRecord(SYSTEM_STRINGS, string);
        systemStringsRecord.update();
        return string;
    }

    @Override
    public void delete(Integer id) {
        context.update(SYSTEM_STRINGS).set(SYSTEM_STRINGS.STATE, RecordState.DELETED.name()).where(SYSTEM_STRINGS.ID.eq(id)).execute();
    }

    @Override
    public SystemStringDao findByUUID(String uuid) {
        return context.selectFrom(SYSTEM_STRINGS).where(SYSTEM_STRINGS.UUID.eq(uuid)).fetchAnyInto(SystemStringDao.class);
    }

    @Override
    public SystemStringDao findById(Integer id) {
        return context.selectFrom(SYSTEM_STRINGS).where(SYSTEM_STRINGS.ID.eq(id)).fetchAnyInto(SystemStringDao.class);
    }

    @Override
    public List<SystemStringDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(SYSTEM_STRINGS).where(SYSTEM_STRINGS.ID.in(ids)).fetchInto(SystemStringDao.class);
    }

    @Override
    public List<SystemStringDao> findAll() {
        return context.selectFrom(SYSTEM_STRINGS).fetchInto(SystemStringDao.class);
    }
}