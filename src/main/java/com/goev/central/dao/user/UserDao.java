package com.goev.central.dao.user;

import com.goev.central.dto.user.UserDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

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
        return null;
    }
}
