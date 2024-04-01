package com.goev.central.repository.user.authorization;

import com.goev.central.dao.user.authorization.UserPermissionDao;

import java.util.List;

public interface UserPermissionRepository {
    UserPermissionDao save(UserPermissionDao partner);
    UserPermissionDao update(UserPermissionDao partner);
    void delete(Integer id);
    UserPermissionDao findByUUID(String uuid);
    UserPermissionDao findById(Integer id);
    List<UserPermissionDao> findAllByIds(List<Integer> ids);
    List<UserPermissionDao> findAll();
}
