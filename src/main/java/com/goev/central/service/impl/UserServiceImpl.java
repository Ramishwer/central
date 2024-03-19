package com.goev.central.service.impl;


import com.goev.central.dao.user.UserDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.UserDetailsDto;
import com.goev.central.dto.user.UserDto;
import com.goev.central.repository.user.UserRepository;
import com.goev.central.service.UserService;
import com.goev.lib.exceptions.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetailsDto createUser(UserDetailsDto userDto) {
        UserDao user = userRepository.save(new UserDao().fromDto(userDto.getDetails()));
        if(user == null)
            throw new ResponseException("Error in saving details");
        return UserDetailsDto.builder().details(user.toDto()).uuid(user.getUuid()).build();
    }

    @Override
    public UserDetailsDto updateUser(String userUUID, UserDetailsDto userDto) {
        UserDao user = userRepository.findByUUID(userUUID);
        if(user == null)
            throw new ResponseException("No user found for Id :"+userUUID);
        user = user.fromDto(userDto.getDetails());
        userRepository.update(user);
        if(user == null)
            throw new ResponseException("Error in saving details");
        return UserDetailsDto.builder().details(user.toDto()).uuid(user.getUuid()).build();
    }

    @Override
    public UserDetailsDto getUserDetails(String userUUID) {
        UserDao user = userRepository.findByUUID(userUUID);
        if(user == null)
            throw new ResponseException("No user found for Id :"+userUUID);
        return UserDetailsDto.builder().details(user.toDto()).uuid(user.getUuid()).build();
    }

    @Override
    public Boolean deleteUser(String userUUID) {
        UserDao user = userRepository.findByUUID(userUUID);
        if(user == null)
            throw new ResponseException("No user found for Id :"+userUUID);

        userRepository.delete(user.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<UserDto> getUsers() {

        PaginatedResponseDto<UserDto> result = PaginatedResponseDto.<UserDto>builder().elements(new ArrayList<>()).build();
        userRepository.findAll().forEach(x->{
            result.getElements().add(x.toDto());
        });
        return result;
    }
}
