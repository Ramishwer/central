package com.goev.central.controller;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.dto.user.UserDetailsDto;
import com.goev.central.dto.user.UserDto;
import com.goev.central.service.UserService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-management")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseDto<PaginatedResponseDto<UserDto>> getUsers(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userService.getUsers());
    }
    @PostMapping("/users")
    public ResponseDto<UserDetailsDto> createUser(@RequestBody UserDetailsDto userDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userService.createUser(userDto));
    }

    @PutMapping("/users/{user-uuid}")
    public ResponseDto<UserDetailsDto> updateUser(@PathVariable(value = "user-uuid")String userUUID, @RequestBody UserDetailsDto userDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userService.updateUser(userUUID,userDto));
    }

    @GetMapping("/users/{user-uuid}")
    public ResponseDto<UserDetailsDto> getUserDetails(@PathVariable(value = "user-uuid")String userUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userService.getUserDetails(userUUID));
    }

    @DeleteMapping("/users/{user-uuid}")
    public ResponseDto<Boolean> deleteUser(@PathVariable(value = "user-uuid")String userUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userService.deleteUser(userUUID));
    }
}
