package com.goev.central.repository.user.detail.impl;

import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UsersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Users.USERS;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DSLContext context;

    @Override
    public UserDao save(UserDao user) {
        UsersRecord usersRecord = context.newRecord(USERS, user);
        usersRecord.store();
        user.setId(usersRecord.getId());
        user.setUuid(usersRecord.getUuid());
        user.setCreatedBy(usersRecord.getCreatedBy());
        user.setUpdatedBy(usersRecord.getUpdatedBy());
        user.setCreatedOn(usersRecord.getCreatedOn());
        user.setUpdatedOn(usersRecord.getUpdatedOn());
        user.setIsActive(usersRecord.getIsActive());
        user.setState(usersRecord.getState());
        user.setApiSource(usersRecord.getApiSource());
        user.setNotes(usersRecord.getNotes());
        return user;
    }

    @Override
    public UserDao update(UserDao user) {
        UsersRecord usersRecord = context.newRecord(USERS, user);
        usersRecord.update();


        user.setCreatedBy(usersRecord.getCreatedBy());
        user.setUpdatedBy(usersRecord.getUpdatedBy());
        user.setCreatedOn(usersRecord.getCreatedOn());
        user.setUpdatedOn(usersRecord.getUpdatedOn());
        user.setIsActive(usersRecord.getIsActive());
        user.setState(usersRecord.getState());
        user.setApiSource(usersRecord.getApiSource());
        user.setNotes(usersRecord.getNotes());
        return user;
    }

    @Override
    public void delete(Integer id) {
        context.update(USERS)
                .set(USERS.STATE, RecordState.DELETED.name())
                .where(USERS.ID.eq(id))
                .and(USERS.STATE.eq(RecordState.ACTIVE.name()))
                .and(USERS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public UserDao findByUUID(String uuid) {
        return context.selectFrom(USERS).where(USERS.UUID.eq(uuid))
                .and(USERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(UserDao.class);
    }

    @Override
    public UserDao findById(Integer id) {
        return context.selectFrom(USERS).where(USERS.ID.eq(id))
                .and(USERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(UserDao.class);
    }

    @Override
    public List<UserDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USERS).where(USERS.ID.in(ids)).fetchInto(UserDao.class);
    }

    @Override
    public List<UserDao> findAllActive() {
        return context.selectFrom(USERS).where(USERS.IS_ACTIVE.eq(true)).fetchInto(UserDao.class);
    }

    @Override
    public UserDao findByEmail(String email) {
        return context.selectFrom(USERS).where(USERS.EMAIL.eq(email)).fetchAnyInto(UserDao.class);
    }

    @Override
    public UserDao findByAuthUUID(String authUUID) {
        return context.selectFrom(USERS).where(USERS.AUTH_UUID.eq(authUUID)).fetchAnyInto(UserDao.class);
    }

    @Override
    public UserDao findByPhoneNumber(String phoneNumber) {
        return context.selectFrom(USERS).where(USERS.PHONE_NUMBER.eq(phoneNumber)).fetchAnyInto(UserDao.class);
    }

    @Override
    public List<UserDao> findAllByOnboardingStatus(String onboardingStatus) {
            return context.selectFrom(USERS)
                    .where(USERS.ONBOARDING_STATUS.eq(onboardingStatus))
                    .and(USERS.IS_ACTIVE.eq(true))
                    .and(USERS.STATE.eq(RecordState.ACTIVE.name()))
                    .fetchInto(UserDao.class);
    }
}
