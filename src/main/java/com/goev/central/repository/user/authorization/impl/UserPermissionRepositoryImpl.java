package com.goev.central.repository.user.authorization.impl;

import com.goev.central.dao.user.authorization.UserPermissionDao;
import com.goev.central.repository.user.authorization.UserPermissionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserPermissionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserPermissions.USER_PERMISSIONS;

@Repository
@AllArgsConstructor
@Slf4j
public class UserPermissionRepositoryImpl implements UserPermissionRepository {
    private final DSLContext context;


    @Override
    public UserPermissionDao save(UserPermissionDao assetTransferDetail) {
        UserPermissionsRecord userPermissionsRecord = context.newRecord(USER_PERMISSIONS, assetTransferDetail);
        userPermissionsRecord.store();
        assetTransferDetail.setId(userPermissionsRecord.getId());
        assetTransferDetail.setUuid(userPermissionsRecord.getUuid());
        return assetTransferDetail;
    }

    @Override
    public UserPermissionDao update(UserPermissionDao assetTransferDetail) {
        UserPermissionsRecord userPermissionsRecord = context.newRecord(USER_PERMISSIONS, assetTransferDetail);
        userPermissionsRecord.update();
        return assetTransferDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(USER_PERMISSIONS).set(USER_PERMISSIONS.STATE, RecordState.DELETED.name()).where(USER_PERMISSIONS.ID.eq(id)).execute();
    }

    @Override
    public UserPermissionDao findByUUID(String uuid) {
        return context.selectFrom(USER_PERMISSIONS).where(USER_PERMISSIONS.UUID.eq(uuid)).fetchAnyInto(UserPermissionDao.class);
    }

    @Override
    public UserPermissionDao findById(Integer id) {
        return context.selectFrom(USER_PERMISSIONS).where(USER_PERMISSIONS.ID.eq(id)).fetchAnyInto(UserPermissionDao.class);
    }

    @Override
    public List<UserPermissionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_PERMISSIONS).where(USER_PERMISSIONS.ID.in(ids)).fetchInto(UserPermissionDao.class);
    }

    @Override
    public List<UserPermissionDao> findAll() {
        return context.selectFrom(USER_PERMISSIONS).fetchInto(UserPermissionDao.class);
    }
}
