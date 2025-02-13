package com.goev.central.repository.user.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.enums.user.UserOnboardingStatus;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.lib.utilities.ApplicationContext;
import com.goev.record.central.tables.records.UsersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.goev.record.central.tables.Partners.PARTNERS;
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


    @Override
    public void updateRole(Integer roleId, String userRoleDto){
        context.update(USERS)
                .set(USERS.ROLE,userRoleDto)
                .set(USERS.UPDATED_BY, ApplicationContext.getAuthUUID())
                .set(USERS.UPDATED_ON, DateTime.now())
                .set(USERS.API_SOURCE, ApplicationContext.getApplicationSource())
                .where(USERS.ROLE_ID.eq(roleId))
                .and(USERS.ONBOARDING_STATUS.eq(UserOnboardingStatus.ONBOARDED.name()))
                .and(USERS.IS_ACTIVE.eq(true)).execute();
    }

    @Override
    public Map<String, Integer> findAllUserDetailsIdsByUUID(Set<String> createdByUuids) {
        return context.select(USERS.UUID, USERS.USER_DETAILS_ID)
                .from(USERS)
                .where(USERS.UUID.in(createdByUuids))
                .fetch()
                .intoMap(record -> record.get(USERS.UUID), record -> record.get(USERS.USER_DETAILS_ID));
    }

}
