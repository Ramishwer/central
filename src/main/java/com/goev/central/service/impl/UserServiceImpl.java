package com.goev.central.service.impl;

import com.goev.central.dto.user.UserDetailsDto;
import com.goev.central.dto.user.UserDto;
import com.goev.central.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetailsDto createUser(UserDetailsDto userDto) {
        return null;
    }

    @Override
    public UserDetailsDto updateUser(String userUUID, UserDetailsDto credentials) {
        return null;
    }

    @Override
    public UserDetailsDto getUserDetails(String userUUID) {
        return null;
    }

    @Override
    public Boolean deleteUser(String userUUID) {
        return null;
    }

    @Override
    public List<UserDto> getUsers() {
        return new ArrayList<>();
    }
}
