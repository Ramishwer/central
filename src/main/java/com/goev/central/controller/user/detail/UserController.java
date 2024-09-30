package com.goev.central.controller.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserActionDto;
import com.goev.central.dto.user.detail.UserDto;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.service.user.detail.UserService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-management")
@AllArgsConstructor
public class UserController {


    private final UserService userService;


    @GetMapping("/users")
    public ResponseDto<PaginatedResponseDto<UserViewDto>> getUsers(@RequestParam(value = "onboardingStatus", required = false) String onboardingStatus) {
        if (onboardingStatus == null)
            return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userService.getUsers());
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userService.getUsers(onboardingStatus));
    }

    @PostMapping("/users/{user-uuid}")
    public ResponseDto<UserDto> updateUser(@PathVariable("user-uuid") String userUUID, @RequestBody UserActionDto actionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userService.updateUser(userUUID, actionDto));
    }

    @DeleteMapping("/users/{user-uuid}")
    public ResponseDto<Boolean> deleteAccount(@PathVariable(value = "user-uuid") String userUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userService.deleteUser(userUUID));
    }
}
