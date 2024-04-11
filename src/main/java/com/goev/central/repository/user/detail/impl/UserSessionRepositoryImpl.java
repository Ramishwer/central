package com.goev.central.repository.user.detail.impl;

import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.repository.user.detail.UserSessionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserSessionsRecord;
import com.goev.record.central.tables.records.UsersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserSessions.USER_SESSIONS;
import static com.goev.record.central.tables.Users.USERS;

@Slf4j
@Repository
@AllArgsConstructor
public class UserSessionRepositoryImpl implements UserSessionRepository {

    private final DSLContext context;

    @Override
    public UserSessionDao save(UserSessionDao userSessionDao) {
        UserSessionsRecord userSessionsRecord = context.newRecord(USER_SESSIONS, userSessionDao);
        userSessionsRecord.store();
        userSessionDao.setId(userSessionsRecord.getId());
        userSessionDao.setUuid(userSessionsRecord.getUuid());
        return userSessionDao;
    }

    @Override
    public UserSessionDao update(UserSessionDao userSessionDao) {
        UserSessionsRecord userSessionsRecord = context.newRecord(USER_SESSIONS, userSessionDao);
        userSessionsRecord.update();
        return userSessionDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(USER_SESSIONS).set(USER_SESSIONS.STATE, RecordState.DELETED.name()).where(USER_SESSIONS.ID.eq(id)).execute();
    }

    @Override
    public UserSessionDao findByUUID(String uuid) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.UUID.eq(uuid)).fetchAnyInto(UserSessionDao.class);
    }

    @Override
    public UserSessionDao findById(Integer id) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.ID.eq(id)).fetchAnyInto(UserSessionDao.class);
    }

    @Override
    public List<UserSessionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.ID.in(ids)).fetchInto(UserSessionDao.class);

    }

    @Override
    public List<UserSessionDao> findAll() {
        return context.selectFrom(USER_SESSIONS).fetchInto(UserSessionDao.class);
    }

    @Override
    public List<UserSessionDao> findAllByUserId(Integer userId) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.USER_ID.eq(userId)).fetchInto(UserSessionDao.class);
    }
}
