package com.goev.central.repository.user.detail.impl;

import com.goev.central.dao.user.detail.UserAttributeDao;
import com.goev.central.repository.user.detail.UserAttributeRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserAttributesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserAttributes.USER_ATTRIBUTES;

@Repository
@AllArgsConstructor
@Slf4j
public class UserAttributeRepositoryImpl implements UserAttributeRepository {
    private final DSLContext context;


    @Override
    public UserAttributeDao save(UserAttributeDao attribute) {
        UserAttributesRecord userAttributesRecord = context.newRecord(USER_ATTRIBUTES, attribute);
        userAttributesRecord.store();
        attribute.setId(userAttributesRecord.getId());
        attribute.setUuid(userAttributesRecord.getUuid());
        return attribute;
    }

    @Override
    public UserAttributeDao update(UserAttributeDao attribute) {
        UserAttributesRecord userAttributesRecord = context.newRecord(USER_ATTRIBUTES, attribute);
        userAttributesRecord.update();
        return attribute;
    }

    @Override
    public void delete(Integer id) {
        context.update(USER_ATTRIBUTES).set(USER_ATTRIBUTES.STATE, RecordState.DELETED.name()).where(USER_ATTRIBUTES.ID.eq(id)).execute();
    }

    @Override
    public UserAttributeDao findByUUID(String uuid) {
        return context.selectFrom(USER_ATTRIBUTES).where(USER_ATTRIBUTES.UUID.eq(uuid)).fetchAnyInto(UserAttributeDao.class);
    }

    @Override
    public UserAttributeDao findById(Integer id) {
        return context.selectFrom(USER_ATTRIBUTES).where(USER_ATTRIBUTES.ID.eq(id)).fetchAnyInto(UserAttributeDao.class);
    }

    @Override
    public List<UserAttributeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_ATTRIBUTES).where(USER_ATTRIBUTES.ID.in(ids)).fetchInto(UserAttributeDao.class);
    }

    @Override
    public List<UserAttributeDao> findAll() {
        return context.selectFrom(USER_ATTRIBUTES).fetchInto(UserAttributeDao.class);
    }
}
