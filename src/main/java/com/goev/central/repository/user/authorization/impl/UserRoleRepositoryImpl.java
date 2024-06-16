package com.goev.central.repository.user.authorization.impl;

import com.goev.central.dao.user.authorization.UserRoleDao;
import com.goev.central.repository.user.authorization.UserRoleRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserRolesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserRoles.USER_ROLES;

@Repository
@AllArgsConstructor
@Slf4j
public class UserRoleRepositoryImpl implements UserRoleRepository {
    private final DSLContext context;


    @Override
    public UserRoleDao save(UserRoleDao assetTransferDetail) {
        UserRolesRecord userRolesRecord = context.newRecord(USER_ROLES, assetTransferDetail);
        userRolesRecord.store();
        assetTransferDetail.setId(userRolesRecord.getId());
        assetTransferDetail.setUuid(userRolesRecord.getUuid());
        assetTransferDetail.setCreatedBy(userRolesRecord.getCreatedBy());
        assetTransferDetail.setUpdatedBy(userRolesRecord.getUpdatedBy());
        assetTransferDetail.setCreatedOn(userRolesRecord.getCreatedOn());
        assetTransferDetail.setUpdatedOn(userRolesRecord.getUpdatedOn());
        assetTransferDetail.setIsActive(userRolesRecord.getIsActive());
        assetTransferDetail.setState(userRolesRecord.getState());
        assetTransferDetail.setApiSource(userRolesRecord.getApiSource());
        assetTransferDetail.setNotes(userRolesRecord.getNotes());
        return assetTransferDetail;
    }

    @Override
    public UserRoleDao update(UserRoleDao assetTransferDetail) {
        UserRolesRecord userRolesRecord = context.newRecord(USER_ROLES, assetTransferDetail);
        userRolesRecord.update();


        assetTransferDetail.setCreatedBy(userRolesRecord.getCreatedBy());
        assetTransferDetail.setUpdatedBy(userRolesRecord.getUpdatedBy());
        assetTransferDetail.setCreatedOn(userRolesRecord.getCreatedOn());
        assetTransferDetail.setUpdatedOn(userRolesRecord.getUpdatedOn());
        assetTransferDetail.setIsActive(userRolesRecord.getIsActive());
        assetTransferDetail.setState(userRolesRecord.getState());
        assetTransferDetail.setApiSource(userRolesRecord.getApiSource());
        assetTransferDetail.setNotes(userRolesRecord.getNotes());
        return assetTransferDetail;
    }

    @Override
    public void delete(Integer id) {
     context.update(USER_ROLES)
     .set(USER_ROLES.STATE,RecordState.DELETED.name())
     .where(USER_ROLES.ID.eq(id))
     .and(USER_ROLES.STATE.eq(RecordState.ACTIVE.name()))
     .and(USER_ROLES.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public UserRoleDao findByUUID(String uuid) {
        return context.selectFrom(USER_ROLES).where(USER_ROLES.UUID.eq(uuid))
                .and(USER_ROLES.IS_ACTIVE.eq(true))
                .fetchAnyInto(UserRoleDao.class);
    }

    @Override
    public UserRoleDao findById(Integer id) {
        return context.selectFrom(USER_ROLES).where(USER_ROLES.ID.eq(id))
                .and(USER_ROLES.IS_ACTIVE.eq(true))
                .fetchAnyInto(UserRoleDao.class);
    }

    @Override
    public List<UserRoleDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_ROLES).where(USER_ROLES.ID.in(ids)).fetchInto(UserRoleDao.class);
    }

    @Override
    public List<UserRoleDao> findAllActive() {
        return context.selectFrom(USER_ROLES).fetchInto(UserRoleDao.class);
    }
}
