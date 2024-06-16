package com.goev.central.controller;

import com.goev.central.dto.user.UserViewDto;
import com.goev.central.service.user.detail.UserService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/api/v1/profile-management/profile")
    public ResponseDto<UserViewDto> getUserProfile() {

        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userService.getUserProfile());
    }
}
