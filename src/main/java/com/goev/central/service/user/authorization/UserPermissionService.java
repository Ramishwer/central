package com.goev.central.service.user.authorization;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.authorization.UserPermissionDto;

public interface UserPermissionService {
    PaginatedResponseDto<UserPermissionDto> getPermissions();
    UserPermissionDto createPermission(UserPermissionDto userPermissionDto);
    UserPermissionDto updatePermission(String permissionUUID, UserPermissionDto userPermissionDto);
    UserPermissionDto getPermissionDetails(String permissionUUID);
    Boolean deletePermission(String permissionUUID);
    
}
