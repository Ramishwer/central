package com.goev.central.service.user.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dao.user.detail.UserDetailDao;
import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.dto.user.detail.UserActionDto;
import com.goev.central.dto.user.detail.UserDto;
import com.goev.central.enums.user.UserOnboardingStatus;
import com.goev.central.repository.user.detail.UserDetailRepository;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.service.user.detail.UserService;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.exceptions.ResponseException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    @Override
    public Boolean deleteUser(String userUUID) {
        UserDao user = userRepository.findByUUID(userUUID);
        if (user == null)
            throw new ResponseException("No user found for Id :" + userUUID);
        userRepository.delete(user.getId());
        return true;
    }

    @Override
    public UserDto updateUser(String userUUID, UserActionDto actionDto) {
        UserDao userDao = userRepository.findByUUID(userUUID);
        if (userDao == null)
            throw new ResponseException("No user found for Id :" + userUUID);
        if (RequestContext.getUserSession() != null)
            log.info("Action By User {} For User : {} {}", RequestContext.getUserSession().getUserId(), userDao.getDisplayCode(), ApplicationConstants.GSON.toJson(actionDto));

        switch (actionDto.getAction()) {
            case DEBOARD -> {
                userDao = deboardUser(userDao, actionDto);
            }
            case SUSPEND -> {
                userDao = suspendUser(userDao, actionDto);
            }

        }
        return UserDto.fromDao(userDao);
    }

    private UserDao suspendUser(UserDao userDao, UserActionDto actionDto) {
        UserDetailDao userDetailDao = userDetailRepository.findById(userDao.getUserDetailsId());
        if (userDetailDao != null) {
            userDetailDao.setRemark(actionDto.getRemark());
            userDetailDao.setSuspensionDate(actionDto.getTimestamp() != null ? actionDto.getTimestamp() : DateTime.now());
            userDetailRepository.update(userDetailDao);
            UserViewDto viewDto = UserViewDto.fromDao(userDao);
            if (viewDto != null) {
                viewDto.setRemark(actionDto.getRemark());
                if (CollectionUtils.isEmpty(viewDto.getFields()))
                    viewDto.setFields(new HashMap<>());
                viewDto.getFields().put("suspensionDate", userDetailDao.getSuspensionDate());
                userDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
            }
        }
        return updateUserOnboardingStatus(userDao, UserOnboardingStatus.SUSPENDED);
    }

    private UserDao deboardUser(UserDao userDao, UserActionDto actionDto) {
        UserDetailDao userDetailDao = userDetailRepository.findById(userDao.getUserDetailsId());
        if (userDetailDao != null) {
            userDetailDao.setRemark(actionDto.getRemark());
            userDetailDao.setDeboardingDate(DateTime.now());
            userDetailRepository.update(userDetailDao);
            UserViewDto viewDto = UserViewDto.fromDao(userDao);
            if (viewDto != null) {
                viewDto.setRemark(actionDto.getRemark());
                userDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
            }
        }
        return updateUserOnboardingStatus(userDao, UserOnboardingStatus.DEBOARDED);
    }

    private UserDao updateUserOnboardingStatus(UserDao userDao, UserOnboardingStatus status) {
        userDao.setOnboardingStatus(status.name());
        userDao = userRepository.update(userDao);
        return userDao;
    }

    @Override
    public PaginatedResponseDto<UserViewDto> getUsers() {

        PaginatedResponseDto<UserViewDto> result = PaginatedResponseDto.<UserViewDto>builder().elements(new ArrayList<>()).build();
        List<UserDao> users = userRepository.findAllActive();
        return getUserViewDtoPaginatedResponseDto(users, result);
    }


    @Override
    public PaginatedResponseDto<UserViewDto> getUsers(String onboardingStatus) {
        PaginatedResponseDto<UserViewDto> result = PaginatedResponseDto.<UserViewDto>builder().elements(new ArrayList<>()).build();
        List<UserDao> users = userRepository.findAllByOnboardingStatus(onboardingStatus);
        return getUserViewDtoPaginatedResponseDto(users, result);
    }

    private PaginatedResponseDto<UserViewDto> getUserViewDtoPaginatedResponseDto(List<UserDao> users, PaginatedResponseDto<UserViewDto> result) {
        if (CollectionUtils.isEmpty(users))
            return result;
        for (UserDao userDao : users) {
            UserViewDto userViewDto = ApplicationConstants.GSON.fromJson(userDao.getViewInfo(), UserViewDto.class);
            if (userViewDto == null)
                continue;
            userViewDto.setUuid(userDao.getUuid());
            result.getElements().add(userViewDto);
        }
        return result;
    }

    @Override
    public UserViewDto getUserProfile() {
        UserSessionDao userSession = RequestContext.getUserSession();
        if (userSession == null)
            throw new ResponseException("No user session present");
        UserDao user = userRepository.findById(userSession.getUserId());
        if (user == null)
            throw new ResponseException("No user found");
        UserViewDto result = new Gson().fromJson(user.getViewInfo(), UserViewDto.class);
        result.setUuid(user.getUuid());
        return result;

    }
}
