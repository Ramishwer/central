package com.goev.central.repository.user.authorization;

import com.goev.central.dao.user.authorization.UserRolePermissionMappingDao;

import java.util.List;

public interface UserRolePermissionMappingRepository {
    UserRolePermissionMappingDao save(UserRolePermissionMappingDao partner);

    UserRolePermissionMappingDao update(UserRolePermissionMappingDao partner);

    void delete(Integer id);

    UserRolePermissionMappingDao findByUUID(String uuid);

    UserRolePermissionMappingDao findById(Integer id);

    List<UserRolePermissionMappingDao> findAllByIds(List<Integer> ids);

    List<UserRolePermissionMappingDao> findAllActive();
}
