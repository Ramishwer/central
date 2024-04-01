//package com.goev.central.service.impl;
//
//
//import com.goev.central.dao.user.detail.UserDao;
//import com.goev.central.dto.common.PaginatedResponseDto;
//import com.goev.central.dto.user.detail.UserDetailsDto;
//import com.goev.central.dto.user.detail.UserDto;
//import com.goev.central.repository.user.detail.UserRepository;
//import com.goev.central.service.user.UserService;
//import com.goev.lib.exceptions.ResponseException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Slf4j
//@Service

//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//public class UserServiceImpl  implements UserService {
//
//
//    private final UserRepository userRepository;
//    @Override
//    public UserDetailsDto createUser(UserDetailsDto userDto) {
//        UserDao user = userRepository.save(new UserDao().fromDto(userDto.getDetails()));
//        if(user == null)
//            throw new ResponseException("Error in saving details");
//        return UserDetailsDto.builder().details(user.toDto()).uuid(user.getUuid()).build();
//    }
//
//    @Override
//    public UserDetailsDto updateUser(String userUUID, UserDetailsDto userDto) {
//        UserDao user = userRepository.findByUUID(userUUID);
//        if(user == null)
//            throw new ResponseException("No user found for Id :"+userUUID);
//        UserDao newUser = user.fromDto(userDto.getDetails());
//        newUser.setId(user.getId());
//        newUser.setUuid(user.getUuid());
//        user =userRepository.update(newUser);
//        if(user == null)
//            throw new ResponseException("Error in saving details");
//        return UserDetailsDto.builder().details(user.toDto()).uuid(user.getUuid()).build();
//    }
//
//    @Override
//    public UserDetailsDto getUserDetails(String userUUID) {
//        UserDao user = userRepository.findByUUID(userUUID);
//        if(user == null)
//            throw new ResponseException("No user found for Id :"+userUUID);
//        return UserDetailsDto.builder().details(user.toDto()).uuid(user.getUuid()).build();
//    }
//
//    @Override
//    public Boolean deleteUser(String userUUID) {
//        UserDao user = userRepository.findByUUID(userUUID);
//        if(user == null)
//            throw new ResponseException("No user found for Id :"+userUUID);
//
//        userRepository.delete(user.getId());
//        return true;
//    }
//
//    @Override
//    public PaginatedResponseDto<UserDto> getUsers() {
//
//        PaginatedResponseDto<UserDto> result = PaginatedResponseDto.<UserDto>builder().elements(new ArrayList<>()).build();
//        userRepository.findAll().forEach(x->{
//            result.getElements().add(x.toDto());
//        });
//        return result;
//    }
//}
