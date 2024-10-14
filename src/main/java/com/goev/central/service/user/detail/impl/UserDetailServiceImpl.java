package com.goev.central.service.user.detail.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.authorization.UserRoleDao;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dao.user.detail.UserDetailDao;
import com.goev.central.dto.auth.AuthUserDto;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.dto.user.authorization.UserRoleDto;
import com.goev.central.dto.user.detail.UserDetailDto;
import com.goev.central.enums.user.UserOnboardingStatus;
import com.goev.central.repository.user.authorization.UserRoleRepository;
import com.goev.central.repository.user.detail.UserDetailRepository;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.service.auth.AuthService;
import com.goev.central.service.user.detail.UserDetailService;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.exceptions.ResponseException;
import com.goev.lib.utilities.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthService authService;

    @Override
    public UserDetailDto createUser(UserDetailDto userDto) {
        UserDao existingUser = userRepository.findByPhoneNumber(userDto.getUserDetails().getPhoneNumber());

        if (existingUser != null) {
            throw new ResponseException("Error in saving user: User with Phone Number :" + userDto.getUserDetails().getPhoneNumber() + " already exist");
        }
        UserDao userDao = new UserDao();

        userDao.setPhoneNumber(userDto.getUserDetails().getPhoneNumber());
        userDao.setEmail(userDto.getUserDetails().getEmail());
        userDao.setOnboardingStatus(UserOnboardingStatus.ONBOARDED.name());
        userDao.setDisplayCode("USR-" + SecretGenerationUtils.getCode());

        if (userDto.getRole() != null) {
            UserRoleDao userRoleDao = userRoleRepository.findByUUID(userDto.getRole().getUuid());
            if (userRoleDao != null) {
                userDao.setRole(ApplicationConstants.GSON.toJson(UserRoleDto.fromDao(userRoleDao)));
                userDao.setRoleId(userRoleDao.getId());
            }

        }
        UserDao user = userRepository.save(userDao);

        if (user == null)
            throw new ResponseException("Error in saving details");

        UserDetailDao userDetails = getUserDetailDao(userDto);
        userDetails.setUserId(user.getId());
        userDetails = userDetailRepository.save(userDetails);
        if (userDetails == null)
            throw new ResponseException("Error in saving user details");

        user.setUserDetailsId(userDetails.getId());
        user.setAuthUuid(authService.createUser(AuthUserDto.builder()
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getPhoneNumber())
                .organizationUUID(ApplicationContext.getOrganizationUUID())
                .clientUUID(ApplicationConstants.CLIENT_UUID)
                .build()));
        user.setViewInfo(ApplicationConstants.GSON.toJson(getUserViewDto(userDetails, user)));
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

        userRepository.delete(userDetailDao.getId());
        user.setUserDetailsId(userDetails.getId());
        user.setViewInfo(ApplicationConstants.GSON.toJson(getUserViewDto(userDetails, user)));
        if (userDetailDto.getRole() != null) {
            UserRoleDao userRoleDao = userRoleRepository.findByUUID(userDetailDto.getRole().getUuid());
            if (userRoleDao != null) {
                user.setRole(ApplicationConstants.GSON.toJson(UserRoleDto.fromDao(userRoleDao)));
                user.setRoleId(userRoleDao.getId());
            }

        }
        userRepository.update(user);
        UserDetailDto result = UserDetailDto.builder().build();
        setUserDetails(result, user, userDetailDao);
        return result;
    }


    private UserDetailDao getUserDetailDao(UserDetailDto userDto) {
        UserDetailDao newUserDetails = new UserDetailDao();

        if (userDto.getUserDetails() != null) {
            newUserDetails.setFirstName(userDto.getFirstName());
            newUserDetails.setLastName(userDto.getLastName());
            newUserDetails.setEmail(userDto.getUserDetails().getEmail());
            newUserDetails.setProfileUrl(userDto.getProfileUrl());
            newUserDetails.setPhoneNumber(userDto.getUserDetails().getPhoneNumber());
        }

        return newUserDetails;
    }

    private void setUserDetails(UserDetailDto result, UserDao userDao, UserDetailDao userDetails) {
        UserViewDto userDto = UserViewDto.fromDao(userDao);
        result.setUserDetails(userDto);


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
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getPhoneNumber())
                .role(ApplicationConstants.GSON.fromJson(userDao.getRole(), UserRoleDto.class))
                .profileUrl(userDao.getProfileUrl())
                .uuid(userDao.getUuid())
                .build();
    }

}
