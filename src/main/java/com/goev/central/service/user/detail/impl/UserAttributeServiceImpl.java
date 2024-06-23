package com.goev.central.service.user.detail.impl;

import com.goev.central.dao.user.detail.UserAttributeDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserAttributeDto;
import com.goev.central.repository.user.detail.UserAttributeRepository;
import com.goev.central.service.user.detail.UserAttributeService;
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
public class UserAttributeServiceImpl implements UserAttributeService {

    private final UserAttributeRepository userAttributeRepository;

    @Override
    public PaginatedResponseDto<UserAttributeDto> getUserAttributes(String userUUID) {
        PaginatedResponseDto<UserAttributeDto> result = PaginatedResponseDto.<UserAttributeDto>builder().elements(new ArrayList<>()).build();
        List<UserAttributeDao> userAttributeDaos = userAttributeRepository.findAllActive();
        if (CollectionUtils.isEmpty(userAttributeDaos))
            return result;

        for (UserAttributeDao userAttributeDao : userAttributeDaos) {
            result.getElements().add(UserAttributeDto.builder()
                    .uuid(userAttributeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public UserAttributeDto createUserAttribute(String userUUID, UserAttributeDto userAttributeDto) {

        UserAttributeDao userAttributeDao = new UserAttributeDao();
        userAttributeDao = userAttributeRepository.save(userAttributeDao);
        if (userAttributeDao == null)
            throw new ResponseException("Error in saving userAttribute userAttribute");
        return UserAttributeDto.builder()
                .uuid(userAttributeDao.getUuid()).build();
    }

    @Override
    public UserAttributeDto updateUserAttribute(String userUUID, String userAttributeUUID, UserAttributeDto userAttributeDto) {
        UserAttributeDao userAttributeDao = userAttributeRepository.findByUUID(userAttributeUUID);
        if (userAttributeDao == null)
            throw new ResponseException("No userAttribute  found for Id :" + userAttributeUUID);
        UserAttributeDao newUserAttributeDao = new UserAttributeDao();


        newUserAttributeDao.setId(userAttributeDao.getId());
        newUserAttributeDao.setUuid(userAttributeDao.getUuid());
        userAttributeDao = userAttributeRepository.update(newUserAttributeDao);
        if (userAttributeDao == null)
            throw new ResponseException("Error in updating details userAttribute ");
        return UserAttributeDto.builder()
                .uuid(userAttributeDao.getUuid()).build();
    }

    @Override
    public UserAttributeDto getUserAttributeDetails(String userUUID, String userAttributeUUID) {
        UserAttributeDao userAttributeDao = userAttributeRepository.findByUUID(userAttributeUUID);
        if (userAttributeDao == null)
            throw new ResponseException("No userAttribute  found for Id :" + userAttributeUUID);
        return UserAttributeDto.builder()
                .uuid(userAttributeDao.getUuid()).build();
    }

    @Override
    public Boolean deleteUserAttribute(String userUUID, String userAttributeUUID) {
        UserAttributeDao userAttributeDao = userAttributeRepository.findByUUID(userAttributeUUID);
        if (userAttributeDao == null)
            throw new ResponseException("No userAttribute  found for Id :" + userAttributeUUID);
        userAttributeRepository.delete(userAttributeDao.getId());
        return true;
    }
}
