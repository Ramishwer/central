package com.goev.central.service.user.authorization;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.authorization.UserRoleDto;

public interface UserRoleService {
    PaginatedResponseDto<UserRoleDto> getRoles();

    UserRoleDto createRole(UserRoleDto userRoleDto);

    UserRoleDto updateRole(String roleUUID, UserRoleDto userRoleDto);

    UserRoleDto getRoleDetails(String roleUUID);

    Boolean deleteRole(String roleUUID);

}
