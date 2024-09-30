package com.goev.central.dto.user.detail;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.user.detail.UserDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String email;
    private String phoneNumber;
    private String role;
    private String authUUID;
    private String uuid;
    private String profileUrl;
    private String onboardingStatus;

    public static UserDto fromDao(UserDao userDao) {
        if(userDao == null)
            return null;
        return UserDto.builder()
                .email(userDao.getEmail())
                .phoneNumber(userDao.getPhoneNumber())
                .profileUrl(userDao.getProfileUrl())
                .onboardingStatus(userDao.getOnboardingStatus())
                .uuid(userDao.getUuid())
                .build();
    }
}
