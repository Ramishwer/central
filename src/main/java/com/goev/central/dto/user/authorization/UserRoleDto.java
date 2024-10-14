package com.goev.central.dto.user.authorization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.authorization.UserRoleDao;
import com.google.common.reflect.TypeToken;
import lombok.*;

import java.lang.reflect.Type;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleDto {

    private String uuid;
    private String name;
    private String description;
    private Map<String, Map<String,String>> permissions;

    public static UserRoleDto fromDao(UserRoleDao userRoleDao) {
        if(userRoleDao == null)
            return null;
       UserRoleDto role = UserRoleDto.builder()
                .name(userRoleDao.getName())
                .description(userRoleDao.getDescription())
                .build();

       if(userRoleDao.getPermissions()!=null){
           Type t = new TypeToken<Map<String,Map<String,String>>>(){}.getType();
           role.setPermissions(ApplicationConstants.GSON.fromJson(userRoleDao.getPermissions(),t));
       }
       return role;
    }
}
