package com.goev.central.dao.partner;

import com.goev.central.dao.customer.CustomerDao;
import com.goev.central.dto.customer.CustomerDto;
import com.goev.central.dto.partner.PartnerDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerDao extends BaseDao<Integer, PartnerDto> {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String state;

    @Override
    public PartnerDao fromDto(PartnerDto partnerDto) {
        return null;
    }
}
