package com.goev.central.controller.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
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
    public ResponseDto<PaginatedResponseDto<UserViewDto>> getUsers() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userService.getUsers());
    }


    @DeleteMapping("/users/{user-uuid}")
    public ResponseDto<Boolean> deleteAccount(@PathVariable(value = "user-uuid") String userUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userService.deleteUser(userUUID));
    }
}
