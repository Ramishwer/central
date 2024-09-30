package com.goev.central.service.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.dto.user.detail.UserActionDto;
import com.goev.central.dto.user.detail.UserDto;

public interface UserService {

    PaginatedResponseDto<UserViewDto> getUsers();
    PaginatedResponseDto<UserViewDto> getUsers(String onboardingStatus);

    UserViewDto getUserProfile();

    Boolean deleteUser(String userUUID);

    UserDto updateUser(String userUUID, UserActionDto actionDto);
}
