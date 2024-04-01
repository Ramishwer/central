package com.goev.central.repository.user.detail;

import com.goev.central.dao.user.detail.UserAttributeDao;

import java.util.List;

public interface UserAttributeRepository {
    UserAttributeDao save(UserAttributeDao partner);
    UserAttributeDao update(UserAttributeDao partner);
    void delete(Integer id);
    UserAttributeDao findByUUID(String uuid);
    UserAttributeDao findById(Integer id);
    List<UserAttributeDao> findAllByIds(List<Integer> ids);
    List<UserAttributeDao> findAll();
}