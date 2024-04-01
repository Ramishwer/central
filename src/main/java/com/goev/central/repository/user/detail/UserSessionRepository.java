
package com.goev.central.repository.user.detail;

import com.goev.central.dao.user.detail.UserSessionDao;

import java.util.List;

public interface UserSessionRepository {
    UserSessionDao save(UserSessionDao partner);
    UserSessionDao update(UserSessionDao partner);
    void delete(Integer id);
    UserSessionDao findByUUID(String uuid);
    UserSessionDao findById(Integer id);
    List<UserSessionDao> findAllByIds(List<Integer> ids);
    List<UserSessionDao> findAll();
}