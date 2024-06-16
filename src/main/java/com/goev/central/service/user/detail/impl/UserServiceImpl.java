package com.goev.central.service.user.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.repository.user.detail.UserDetailRepository;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.service.user.detail.UserService;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.exceptions.ResponseException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public PaginatedResponseDto<UserViewDto> getUsers() {

        PaginatedResponseDto<UserViewDto> result = PaginatedResponseDto.<UserViewDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<UserDao> users = userRepository.findAllActive();
        if (CollectionUtils.isEmpty(users))
            return result;

        for (UserDao user : users) {
            UserViewDto userViewDto = ApplicationConstants.GSON.fromJson(user.getViewInfo(), UserViewDto.class);
            if (userViewDto == null)
                continue;
            userViewDto.setUuid(user.getUuid());
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
