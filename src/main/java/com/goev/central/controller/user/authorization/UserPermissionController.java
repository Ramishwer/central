package com.goev.central.controller.user.authorization;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.authorization.UserPermissionDto;
import com.goev.central.service.user.authorization.UserPermissionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-management")
@AllArgsConstructor
public class UserPermissionController {

    private final UserPermissionService userPermissionService;

    @GetMapping("/permissions")
    public ResponseDto<PaginatedResponseDto<UserPermissionDto>> getPermissions() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userPermissionService.getPermissions());
    }

    @PostMapping("/permissions")
    public ResponseDto<UserPermissionDto> createPermission(@RequestBody UserPermissionDto userPermissionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userPermissionService.createPermission(userPermissionDto));
    }

    @PutMapping("/permissions/{permission-uuid}")
    public ResponseDto<UserPermissionDto> updatePermission(@PathVariable(value = "permission-uuid") String permissionUUID, @RequestBody UserPermissionDto userPermissionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userPermissionService.updatePermission(permissionUUID, userPermissionDto));
    }

    @GetMapping("/permissions/{permission-uuid}")
    public ResponseDto<UserPermissionDto> getPermissionDetails(@PathVariable(value = "permission-uuid") String permissionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userPermissionService.getPermissionDetails(permissionUUID));
    }

    @DeleteMapping("/permissions/{permission-uuid}")
    public ResponseDto<Boolean> deletePermission(@PathVariable(value = "permission-uuid") String permissionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userPermissionService.deletePermission(permissionUUID));
    }
}
