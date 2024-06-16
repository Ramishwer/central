package com.goev.central.controller.user.detail;

import com.goev.central.dto.user.detail.UserDetailDto;
import com.goev.central.service.user.detail.UserDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-management")
@AllArgsConstructor
public class UserDetailController {


    private final UserDetailService userDetailService;

    @PostMapping("/users/details")
    public ResponseDto<UserDetailDto> createUser(@RequestBody UserDetailDto userDetailDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userDetailService.createUser(userDetailDto));
    }

    @GetMapping("/users/{user-uuid}/details")
    public ResponseDto<UserDetailDto> getUserDetails(@PathVariable(value = "user-uuid") String userUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userDetailService.getUserDetails(userUUID));
    }

    @PutMapping("/users/{vehicle-uuid}/details")
    public ResponseDto<UserDetailDto> updateVehicle(@PathVariable(value = "user-uuid") String userUUID, @RequestBody UserDetailDto userDetailDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userDetailService.updateUser(userUUID, userDetailDto));
    }

}
