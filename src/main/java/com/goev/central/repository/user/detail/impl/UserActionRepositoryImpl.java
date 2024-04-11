package com.goev.central.repository.user.detail.impl;

import com.goev.central.dao.user.detail.UserActionDao;
import com.goev.central.repository.user.detail.UserActionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserActionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserActions.USER_ACTIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class UserActionRepositoryImpl implements UserActionRepository {

    private final DSLContext context;

    @Override
    public UserActionDao save(UserActionDao action) {
        UserActionsRecord userActionsRecord = context.newRecord(USER_ACTIONS, action);
        userActionsRecord.store();
        action.setId(userActionsRecord.getId());
        action.setUuid(userActionsRecord.getUuid());
        return action;
    }

    @Override
    public UserActionDao update(UserActionDao action) {
        UserActionsRecord userActionsRecord = context.newRecord(USER_ACTIONS, action);
        userActionsRecord.update();
        return action;
    }

    @Override
    public void delete(Integer id) {
        context.update(USER_ACTIONS).set(USER_ACTIONS.STATE, RecordState.DELETED.name()).where(USER_ACTIONS.ID.eq(id)).execute();
    }

    @Override
    public UserActionDao findByUUID(String uuid) {
        return context.selectFrom(USER_ACTIONS).where(USER_ACTIONS.UUID.eq(uuid)).fetchAnyInto(UserActionDao.class);
    }

    @Override
    public UserActionDao findById(Integer id) {
        return context.selectFrom(USER_ACTIONS).where(USER_ACTIONS.ID.eq(id)).fetchAnyInto(UserActionDao.class);
    }

    @Override
    public List<UserActionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_ACTIONS).where(USER_ACTIONS.ID.in(ids)).fetchInto(UserActionDao.class);
    }

    @Override
    public List<UserActionDao> findAll() {
        return context.selectFrom(USER_ACTIONS).fetchInto(UserActionDao.class);
    }
}
