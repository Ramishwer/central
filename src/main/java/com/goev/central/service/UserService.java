package com.goev.central.service;

import com.goev.central.dto.user.UserDetailsDto;

public interface UserService {
    UserDetailsDto createUser(UserDetailsDto userDto);
    UserDetailsDto updateUser(String userUUID, UserDetailsDto credentials);
    UserDetailsDto getUserDetails(String userUUID);
    Boolean deleteUser(String userUUID);

}
