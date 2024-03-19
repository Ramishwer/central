package com.goev.central.dao.user;

import com.goev.central.dao.partner.PartnerDao;
import com.goev.central.dto.customer.CustomerDto;
import com.goev.central.dto.user.UserDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDao extends BaseDao<Integer, UserDto> {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String state;


    @Override
    public UserDao fromDto(UserDto userDto) {
        UserDao result = new UserDao();
        result.setEmail(userDto.getEmail());
        result.setPhone(userDto.getPhone());
        result.setFirstName(userDto.getFirstName());
        result.setLastName(userDto.getLastName());
        result.setState(userDto.getState());
        result.setUuid(userDto.getUuid());
        return result;
    }

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

    @Override
    public UserDto toDto() {
        return GSON.fromJson(this.toJson(),UserDto.class);
    }
}
