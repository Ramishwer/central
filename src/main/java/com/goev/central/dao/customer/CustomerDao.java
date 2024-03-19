package com.goev.central.dao.customer;

import com.goev.central.dto.customer.CustomerDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDao extends BaseDao<Integer, CustomerDto> {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String state;

    @Override
    public CustomerDao fromDto(CustomerDto customerDto) {
        return null;
    }
}
