package com.goev.central.service.user.authorization.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.authorization.UserRoleDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.authorization.UserRoleDto;
import com.goev.central.repository.user.authorization.UserRoleRepository;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.service.user.authorization.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Override
    public PaginatedResponseDto<UserRoleDto> getRoles() {
        PaginatedResponseDto<UserRoleDto> result = PaginatedResponseDto.<UserRoleDto>builder().elements(new ArrayList<>()).build();
        List<UserRoleDao> userRoleDaos = userRoleRepository.findAllActive();
        if (CollectionUtils.isEmpty(userRoleDaos))
            return result;

        for (UserRoleDao userRoleDao : userRoleDaos) {
            result.getElements().add(UserRoleDto.builder()
                    .name(userRoleDao.getName())
                    .description(userRoleDao.getDescription())
                    .uuid(userRoleDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public UserRoleDto createRole(UserRoleDto userRoleDto) {

        UserRoleDao userRoleDao = new UserRoleDao();
        userRoleDao.setName(userRoleDto.getName());
        userRoleDao.setDescription(userRoleDto.getDescription());

        if (userRoleDto.getPermissions() != null)
            userRoleDao.setPermissions(ApplicationConstants.GSON.toJson(userRoleDto.getPermissions()));
        userRoleDao = userRoleRepository.save(userRoleDao);
        if (userRoleDao == null)
            throw new ResponseException("Error in saving user role");
        return UserRoleDto.fromDao(userRoleDao);
    }

    @Override
    public UserRoleDto updateRole(String roleUUID, UserRoleDto userRoleDto) {
        UserRoleDao userRoleDao = userRoleRepository.findByUUID(roleUUID);
        if (userRoleDao == null)
            throw new ResponseException("No user role found for Id :" + roleUUID);
        UserRoleDao newUserRoleDao = new UserRoleDao();
        newUserRoleDao.setName(userRoleDto.getName());
        newUserRoleDao.setDescription(userRoleDto.getDescription());
        if (userRoleDto.getPermissions() != null)
            newUserRoleDao.setPermissions(ApplicationConstants.GSON.toJson(userRoleDto.getPermissions()));

        newUserRoleDao.setId(userRoleDao.getId());
        newUserRoleDao.setUuid(userRoleDao.getUuid());
        userRoleDao = userRoleRepository.update(newUserRoleDao);


        if (userRoleDao == null)
            throw new ResponseException("Error in updating details user role");


        userRepository.updateRole(userRoleDao.getId(), ApplicationConstants.GSON.toJson(UserRoleDto.fromDao(userRoleDao)));
        return UserRoleDto.fromDao(userRoleDao);
    }

    @Override
    public UserRoleDto getRoleDetails(String roleUUID) {
        UserRoleDao userRoleDao = userRoleRepository.findByUUID(roleUUID);
        if (userRoleDao == null)
            throw new ResponseException("No user role found for Id :" + roleUUID);
        return UserRoleDto.fromDao(userRoleDao);
    }

    @Override
    public Boolean deleteRole(String roleUUID) {
        UserRoleDao userRoleDao = userRoleRepository.findByUUID(roleUUID);
        if (userRoleDao == null)
            throw new ResponseException("No user role found for Id :" + roleUUID);
        userRoleRepository.delete(userRoleDao.getId());
        return true;
    }
}
