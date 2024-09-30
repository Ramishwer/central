package com.goev.central.dto.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dto.partner.PartnerViewDto;
import lombok.*;
import org.joda.time.DateTime;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserViewDto {
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String role;
    private String authUUID;
    private String uuid;
    private String profileUrl;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime onboardingDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime deboardingDate;
    private String remark;
    private Map<String, Object> fields;
    private String state;

    public static UserViewDto fromDao(UserDao userDao) {
        if (userDao == null)
            return null;
        UserViewDto result = ApplicationConstants.GSON.fromJson(userDao.getViewInfo(), UserViewDto.class);
        result.setUuid(userDao.getUuid());
        result.setState(userDao.getOnboardingStatus());
        return result;
    }
}
