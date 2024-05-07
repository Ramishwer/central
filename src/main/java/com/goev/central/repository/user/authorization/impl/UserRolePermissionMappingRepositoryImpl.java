package com.goev.central.repository.user.authorization.impl;

import com.goev.central.dao.user.authorization.UserRolePermissionMappingDao;
import com.goev.central.repository.user.authorization.UserRolePermissionMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserRolePermissionMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserRolePermissionMappings.USER_ROLE_PERMISSION_MAPPINGS;

@Repository
@AllArgsConstructor
@Slf4j
public class UserRolePermissionMappingRepositoryImpl implements UserRolePermissionMappingRepository {
    private final DSLContext context;


    @Override
    public UserRolePermissionMappingDao save(UserRolePermissionMappingDao assetTransferDetail) {
        UserRolePermissionMappingsRecord userRolePermissionMappingsRecord = context.newRecord(USER_ROLE_PERMISSION_MAPPINGS, assetTransferDetail);
        userRolePermissionMappingsRecord.store();
        assetTransferDetail.setId(userRolePermissionMappingsRecord.getId());
        assetTransferDetail.setUuid(userRolePermissionMappingsRecord.getUuid());
        return assetTransferDetail;
    }

    @Override
    public UserRolePermissionMappingDao update(UserRolePermissionMappingDao assetTransferDetail) {
        UserRolePermissionMappingsRecord userRolePermissionMappingsRecord = context.newRecord(USER_ROLE_PERMISSION_MAPPINGS, assetTransferDetail);
        userRolePermissionMappingsRecord.update();
        return assetTransferDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(USER_ROLE_PERMISSION_MAPPINGS).set(USER_ROLE_PERMISSION_MAPPINGS.STATE, RecordState.DELETED.name()).where(USER_ROLE_PERMISSION_MAPPINGS.ID.eq(id)).execute();
    }

    @Override
    public UserRolePermissionMappingDao findByUUID(String uuid) {
        return context.selectFrom(USER_ROLE_PERMISSION_MAPPINGS).where(USER_ROLE_PERMISSION_MAPPINGS.UUID.eq(uuid)).fetchAnyInto(UserRolePermissionMappingDao.class);
    }

    @Override
    public UserRolePermissionMappingDao findById(Integer id) {
        return context.selectFrom(USER_ROLE_PERMISSION_MAPPINGS).where(USER_ROLE_PERMISSION_MAPPINGS.ID.eq(id)).fetchAnyInto(UserRolePermissionMappingDao.class);
    }

    @Override
    public List<UserRolePermissionMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_ROLE_PERMISSION_MAPPINGS).where(USER_ROLE_PERMISSION_MAPPINGS.ID.in(ids)).fetchInto(UserRolePermissionMappingDao.class);
    }

    @Override
    public List<UserRolePermissionMappingDao> findAll() {
        return context.selectFrom(USER_ROLE_PERMISSION_MAPPINGS).fetchInto(UserRolePermissionMappingDao.class);
    }
}
