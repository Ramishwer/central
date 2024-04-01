
package com.goev.central.repository.user.detail;

import com.goev.central.dao.user.detail.UserDetailDao;

import java.util.List;

public interface UserDetailRepository {
    UserDetailDao save(UserDetailDao partner);
    UserDetailDao update(UserDetailDao partner);
    void delete(Integer id);
    UserDetailDao findByUUID(String uuid);
    UserDetailDao findById(Integer id);
    List<UserDetailDao> findAllByIds(List<Integer> ids);
    List<UserDetailDao> findAll();
}