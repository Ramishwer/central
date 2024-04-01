package com.goev.central.service.user;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserDetailsDto;
import com.goev.central.dto.user.detail.UserDto;

public interface UserService {
    UserDetailsDto createUser(UserDetailsDto userDto);
    UserDetailsDto updateUser(String userUUID, UserDetailsDto credentials);
    UserDetailsDto getUserDetails(String userUUID);
    Boolean deleteUser(String userUUID);

    PaginatedResponseDto<UserDto> getUsers();
}
