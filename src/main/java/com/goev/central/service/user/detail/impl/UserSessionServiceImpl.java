package com.goev.central.service.user.detail.impl;

import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserSessionDto;
import com.goev.central.repository.user.detail.UserSessionRepository;
import com.goev.central.service.user.detail.UserSessionService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository userSessionRepository;

    @Override
    public PaginatedResponseDto<UserSessionDto> getUserSessions(String userUUID) {
        PaginatedResponseDto<UserSessionDto> result = PaginatedResponseDto.<UserSessionDto>builder().elements(new ArrayList<>()).build();
        List<UserSessionDao> userSessionDaos = userSessionRepository.findAllActive();
        if (CollectionUtils.isEmpty(userSessionDaos))
            return result;

        for (UserSessionDao userSessionDao : userSessionDaos) {
            result.getElements().add(UserSessionDto.builder()
                    .uuid(userSessionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public UserSessionDto getUserSessionDetails(String userUUID, String userSessionUUID) {
        UserSessionDao userSessionDao = userSessionRepository.findByUUID(userSessionUUID);
        if (userSessionDao == null)
            throw new ResponseException("No userSession  found for Id :" + userSessionUUID);
        return UserSessionDto.builder()
                .uuid(userSessionDao.getUuid()).build();
    }

    @Override
    public Boolean deleteUserSession(String userUUID, String userSessionUUID) {
        UserSessionDao userSessionDao = userSessionRepository.findByUUID(userSessionUUID);
        if (userSessionDao == null)
            throw new ResponseException("No userSession  found for Id :" + userSessionUUID);
        userSessionRepository.delete(userSessionDao.getId());
        return true;
    }
}
