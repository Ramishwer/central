package com.goev.central.controller.user.authorization;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.authorization.UserRoleDto;
import com.goev.central.service.user.authorization.UserRoleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-management")
@AllArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping("/roles")
    public ResponseDto<PaginatedResponseDto<UserRoleDto>> getRoles() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userRoleService.getRoles());
    }

    @PostMapping("/roles")
    public ResponseDto<UserRoleDto> createRole(@RequestBody UserRoleDto userRoleDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userRoleService.createRole(userRoleDto));
    }

    @PutMapping("/roles/{role-uuid}")
    public ResponseDto<UserRoleDto> updateRole(@PathVariable(value = "role-uuid") String roleUUID, @RequestBody UserRoleDto userRoleDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userRoleService.updateRole(roleUUID, userRoleDto));
    }

    @GetMapping("/roles/{role-uuid}")
    public ResponseDto<UserRoleDto> getRoleDetails(@PathVariable(value = "role-uuid") String roleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userRoleService.getRoleDetails(roleUUID));
    }

    @DeleteMapping("/roles/{role-uuid}")
    public ResponseDto<Boolean> deleteRole(@PathVariable(value = "role-uuid") String roleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userRoleService.deleteRole(roleUUID));
    }
}
