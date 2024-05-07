package com.goev.central.service.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserAttributeDto;

public interface UserAttributeService {
    PaginatedResponseDto<UserAttributeDto> getUserAttributes(String userUUID);

    UserAttributeDto createUserAttribute(String userUUID, UserAttributeDto userAttributeDto);

    UserAttributeDto updateUserAttribute(String userUUID, String userAttributeUUID, UserAttributeDto userAttributeDto);

    UserAttributeDto getUserAttributeDetails(String userUUID, String userAttributeUUID);

    Boolean deleteUserAttribute(String userUUID, String userAttributeUUID);
}
