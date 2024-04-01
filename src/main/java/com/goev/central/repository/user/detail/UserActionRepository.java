package com.goev.central.repository.user.detail;

import com.goev.central.dao.user.detail.UserActionDao;

import java.util.List;

public interface UserActionRepository {
    UserActionDao save(UserActionDao partner);
    UserActionDao update(UserActionDao partner);
    void delete(Integer id);
    UserActionDao findByUUID(String uuid);
    UserActionDao findById(Integer id);
    List<UserActionDao> findAllByIds(List<Integer> ids);
    List<UserActionDao> findAll();
}