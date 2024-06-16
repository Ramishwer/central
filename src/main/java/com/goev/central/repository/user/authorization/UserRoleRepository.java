package com.goev.central.repository.user.authorization;

import com.goev.central.dao.user.authorization.UserRoleDao;

import java.util.List;

public interface UserRoleRepository {
    UserRoleDao save(UserRoleDao partner);

    UserRoleDao update(UserRoleDao partner);

    void delete(Integer id);

    UserRoleDao findByUUID(String uuid);

    UserRoleDao findById(Integer id);

    List<UserRoleDao> findAllByIds(List<Integer> ids);

    List<UserRoleDao> findAllActive();
}