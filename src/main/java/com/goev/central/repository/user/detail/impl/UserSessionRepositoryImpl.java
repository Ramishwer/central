package com.goev.central.repository.user.detail.impl;

import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.repository.user.detail.UserSessionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserSessionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserSessions.USER_SESSIONS;

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
        userSessionDao.setCreatedBy(userSessionsRecord.getCreatedBy());
        userSessionDao.setUpdatedBy(userSessionsRecord.getUpdatedBy());
        userSessionDao.setCreatedOn(userSessionsRecord.getCreatedOn());
        userSessionDao.setUpdatedOn(userSessionsRecord.getUpdatedOn());
        userSessionDao.setIsActive(userSessionsRecord.getIsActive());
        userSessionDao.setState(userSessionsRecord.getState());
        userSessionDao.setApiSource(userSessionsRecord.getApiSource());
        userSessionDao.setNotes(userSessionsRecord.getNotes());
        return userSessionDao;
    }

    @Override
    public UserSessionDao update(UserSessionDao userSessionDao) {
        UserSessionsRecord userSessionsRecord = context.newRecord(USER_SESSIONS, userSessionDao);
        userSessionsRecord.update();


        userSessionDao.setCreatedBy(userSessionsRecord.getCreatedBy());
        userSessionDao.setUpdatedBy(userSessionsRecord.getUpdatedBy());
        userSessionDao.setCreatedOn(userSessionsRecord.getCreatedOn());
        userSessionDao.setUpdatedOn(userSessionsRecord.getUpdatedOn());
        userSessionDao.setIsActive(userSessionsRecord.getIsActive());
        userSessionDao.setState(userSessionsRecord.getState());
        userSessionDao.setApiSource(userSessionsRecord.getApiSource());
        userSessionDao.setNotes(userSessionsRecord.getNotes());
        return userSessionDao;
    }

    @Override
    public void delete(Integer id) {
     context.update(USER_SESSIONS)
     .set(USER_SESSIONS.STATE,RecordState.DELETED.name())
     .where(USER_SESSIONS.ID.eq(id))
     .and(USER_SESSIONS.STATE.eq(RecordState.ACTIVE.name()))
     .and(USER_SESSIONS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public UserSessionDao findByUUID(String uuid) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.UUID.eq(uuid))
                .and(USER_SESSIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(UserSessionDao.class);
    }

    @Override
    public UserSessionDao findById(Integer id) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.ID.eq(id))
                .and(USER_SESSIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(UserSessionDao.class);
    }

    @Override
    public List<UserSessionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.ID.in(ids)).fetchInto(UserSessionDao.class);

    }

    @Override
    public List<UserSessionDao> findAllActive() {
        return context.selectFrom(USER_SESSIONS).fetchInto(UserSessionDao.class);
    }

    @Override
    public List<UserSessionDao> findAllByUserId(Integer userId) {
        return context.selectFrom(USER_SESSIONS).where(USER_SESSIONS.USER_ID.eq(userId)).fetchInto(UserSessionDao.class);
    }
}
