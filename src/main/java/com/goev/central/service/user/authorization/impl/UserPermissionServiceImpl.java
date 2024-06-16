package com.goev.central.service.user.authorization.impl;

import com.goev.central.dao.user.authorization.UserPermissionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.authorization.UserPermissionDto;
import com.goev.central.repository.user.authorization.UserPermissionRepository;
import com.goev.central.service.user.authorization.UserPermissionService;
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
public class UserPermissionServiceImpl implements UserPermissionService {

    private final UserPermissionRepository userPermissionRepository;

    @Override
    public PaginatedResponseDto<UserPermissionDto> getPermissions() {
        PaginatedResponseDto<UserPermissionDto> result = PaginatedResponseDto.<UserPermissionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<UserPermissionDao> userPermissionDaos = userPermissionRepository.findAllActive();
        if (CollectionUtils.isEmpty(userPermissionDaos))
            return result;

        for (UserPermissionDao userPermissionDao : userPermissionDaos) {
            result.getElements().add(UserPermissionDto.builder()
                    .name(userPermissionDao.getName())
                    .uuid(userPermissionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public UserPermissionDto createPermission(UserPermissionDto userPermissionDto) {

        UserPermissionDao userPermissionDao = new UserPermissionDao();
        userPermissionDao.setName(userPermissionDto.getName());
        userPermissionDao = userPermissionRepository.save(userPermissionDao);
        if (userPermissionDao == null)
            throw new ResponseException("Error in saving user permission");
        return UserPermissionDto.builder().name(userPermissionDao.getName()).uuid(userPermissionDao.getUuid()).build();
    }

    @Override
    public UserPermissionDto updatePermission(String permissionUUID, UserPermissionDto userPermissionDto) {
        UserPermissionDao userPermissionDao = userPermissionRepository.findByUUID(permissionUUID);
        if (userPermissionDao == null)
            throw new ResponseException("No user permission found for Id :" + permissionUUID);
        UserPermissionDao newUserPermissionDao = new UserPermissionDao();
        newUserPermissionDao.setName(userPermissionDto.getName());

        newUserPermissionDao.setId(userPermissionDao.getId());
        newUserPermissionDao.setUuid(userPermissionDao.getUuid());
        userPermissionDao = userPermissionRepository.update(newUserPermissionDao);
        if (userPermissionDao == null)
            throw new ResponseException("Error in updating details user permission");
        return UserPermissionDto.builder()
                .name(userPermissionDao.getName())
                .uuid(userPermissionDao.getUuid()).build();
    }

    @Override
    public UserPermissionDto getPermissionDetails(String permissionUUID) {
        UserPermissionDao userPermissionDao = userPermissionRepository.findByUUID(permissionUUID);
        if (userPermissionDao == null)
            throw new ResponseException("No user permission found for Id :" + permissionUUID);
        return UserPermissionDto.builder()
                .name(userPermissionDao.getName())
                .uuid(userPermissionDao.getUuid()).build();
    }

    @Override
    public Boolean deletePermission(String permissionUUID) {
        UserPermissionDao userPermissionDao = userPermissionRepository.findByUUID(permissionUUID);
        if (userPermissionDao == null)
            throw new ResponseException("No user permission found for Id :" + permissionUUID);
        userPermissionRepository.delete(userPermissionDao.getId());
        return true;
    }
}
