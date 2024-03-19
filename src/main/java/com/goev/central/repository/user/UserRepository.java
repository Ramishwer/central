package com.goev.central.repository.user;

import com.goev.central.dao.user.UserDao;

import java.util.List;

public interface UserRepository {
    UserDao save(UserDao user);
    UserDao update(UserDao user);
    void delete(Integer id);
    UserDao findByUUID(String uuid);
    UserDao findById(Integer id);
    List<UserDao> findAllByIds(List<Integer> ids);
    List<UserDao> findAll();
}
