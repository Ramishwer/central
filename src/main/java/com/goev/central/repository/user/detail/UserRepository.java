package com.goev.central.repository.user.detail;

import com.goev.central.dao.user.detail.UserDao;

import java.util.List;

public interface UserRepository {
    UserDao save(UserDao partner);
    UserDao update(UserDao partner);
    void delete(Integer id);
    UserDao findByUUID(String uuid);
    UserDao findById(Integer id);
    List<UserDao> findAllByIds(List<Integer> ids);
    List<UserDao> findAll();

    UserDao findByEmail(String username);

    UserDao findByAuthUUID(String authUUID);

    UserDao findByPhoneNumber(String phoneNumber);
}