package com.goev.central.service.user.detail;


import com.goev.central.dto.user.detail.UserDetailDto;

public interface UserDetailService {
    UserDetailDto createUser(UserDetailDto userDto);

    UserDetailDto getUserDetails(String userUUID);

    UserDetailDto updateUser(String userUUID, UserDetailDto userDetailDto);
}
