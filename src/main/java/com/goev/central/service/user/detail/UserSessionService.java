package com.goev.central.service.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserSessionDto;

public interface UserSessionService {
    PaginatedResponseDto<UserSessionDto> getUserSessions(String userUUID);

    UserSessionDto getUserSessionDetails(String userUUID, String userSessionUUID);

    Boolean deleteUserSession(String userUUID, String userSessionUUID);
}
