package com.goev.central.service.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.UserViewDto;

public interface UserService {

    PaginatedResponseDto<UserViewDto> getUsers();

    UserViewDto getUserProfile();

    Boolean deleteUser(String userUUID);
}
