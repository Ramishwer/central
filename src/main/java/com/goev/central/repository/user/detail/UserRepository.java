package com.goev.central.repository.user.detail;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dto.user.authorization.UserRoleDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserRepository {
    UserDao save(UserDao partner);

    UserDao update(UserDao partner);

    void delete(Integer id);

    UserDao findByUUID(String uuid);

    UserDao findById(Integer id);

    List<UserDao> findAllByIds(List<Integer> ids);

    List<UserDao> findAllActive();

    UserDao findByEmail(String username);

    UserDao findByAuthUUID(String authUUID);

    UserDao findByPhoneNumber(String phoneNumber);

    List<UserDao> findAllByOnboardingStatus(String onboardingStatus);

    void updateRole(Integer roleId, String userRoleDto);

    Map<String, Integer> findAllUserDetailsIdsByUUID(Set<String> createdByUuids);

}