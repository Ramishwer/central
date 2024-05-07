package com.goev.central.service.user.detail.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dao.user.detail.UserDetailDao;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.dto.user.detail.UserDetailDto;
import com.goev.central.repository.user.detail.UserDetailRepository;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.service.user.detail.UserDetailService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    @Override
    public UserDetailDto createUser(UserDetailDto userDto) {
        UserDao existingUser = userRepository.findByPhoneNumber(userDto.getUser().getPhoneNumber());

        if (existingUser != null) {
            throw new ResponseException("Error in saving user: User with Phone Number :" + userDto.getUser().getPhoneNumber() + " already exist");
        }
        UserDao userDao = new UserDao();

        userDao.setPhoneNumber(userDto.getUser().getPhoneNumber());
        UserDao user = userRepository.save(userDao);

        if (user == null)
            throw new ResponseException("Error in saving details");

        UserDetailDao userDetails = getUserDetailDao(userDto);
        userDetails.setUserId(user.getId());
        userDetails = userDetailRepository.save(userDetails);
        if (userDetails == null)
            throw new ResponseException("Error in saving user details");

        user.setUserDetailsId(userDetails.getId());
        userRepository.update(user);

        UserDetailDto result = UserDetailDto.builder().build();
        setUserDetails(result, user, userDetails);
        return result;
    }


    @Override
    public UserDetailDto getUserDetails(String userUUID) {
        UserDao user = userRepository.findByUUID(userUUID);
        if (user == null)
            throw new ResponseException("No user found for Id :" + userUUID);

        UserDetailDao userDetailDao = userDetailRepository.findById(user.getUserDetailsId());

        if (userDetailDao == null)
            throw new ResponseException("No user details found for Id :" + userUUID);


        UserDetailDto result = UserDetailDto.builder().build();
        setUserDetails(result, user, userDetailDao);
        return result;
    }

    @Override
    public UserDetailDto updateUser(String userUUID, UserDetailDto userDetailDto) {
        UserDao user = userRepository.findByUUID(userUUID);
        if (user == null)
            throw new ResponseException("No user found for Id :" + userUUID);
        UserDetailDao userDetailDao = userDetailRepository.findById(user.getUserDetailsId());

        if (userDetailDao == null)
            throw new ResponseException("No user details found for Id :" + userUUID);

        UserDetailDao userDetails = getUserDetailDao(userDetailDto);
        userDetails.setUserId(user.getId());
        userDetails = userDetailRepository.save(userDetails);
        if (userDetails == null)
            throw new ResponseException("Error in saving user details");

        user.setUserDetailsId(userDetails.getId());
        user.setViewInfo(ApplicationConstants.GSON.toJson(getUserViewDto(userDetails, user)));
        userRepository.update(user);
        UserDetailDto result = UserDetailDto.builder().build();
        setUserDetails(result, user, userDetailDao);
        return result;
    }


    private UserDetailDao getUserDetailDao(UserDetailDto userDto) {
        UserDetailDao newUserDetails = new UserDetailDao();

        if (userDto.getUser() != null) {
            newUserDetails.setFirstName(userDto.getFirstName());
            newUserDetails.setLastName(userDto.getLastName());
            newUserDetails.setEmail(userDto.getUser().getEmail());
            newUserDetails.setProfileUrl(userDto.getProfileUrl());
        }

        return newUserDetails;
    }

    private void setUserDetails(UserDetailDto result, UserDao userDao, UserDetailDao userDetails) {
        UserViewDto userDto = new UserViewDto();
        userDto.setUuid(userDao.getUuid());
        userDto.setPhoneNumber(userDao.getPhoneNumber());
        userDto.setAuthUUID(userDao.getAuthUuid());

        result.setUser(userDto);


        if (userDetails == null)
            return;

        result.setState(userDetails.getState());
        result.setUuid(userDao.getUuid());
        result.setFirstName(userDetails.getFirstName());
        result.setLastName(userDetails.getLastName());
    }

    private UserViewDto getUserViewDto(UserDetailDao userDetails, UserDao userDao) {
        return UserViewDto.builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .phoneNumber(userDetails.getPhoneNumber())
                .role(userDao.getRole())
                .profileUrl(userDao.getProfileUrl())
                .uuid(userDao.getUuid())
                .build();
    }

}
