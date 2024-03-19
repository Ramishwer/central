package com.goev.central.dao.partner;

import com.goev.central.dao.customer.CustomerDao;
import com.goev.central.dto.customer.CustomerDto;
import com.goev.central.dto.partner.PartnerDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

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
        PartnerDao result = new PartnerDao();
        result.setEmail(partnerDto.getEmail());
        result.setPhone(partnerDto.getPhone());
        result.setFirstName(partnerDto.getFirstName());
        result.setLastName(partnerDto.getLastName());
        result.setState(partnerDto.getState());
        result.setUuid(partnerDto.getUuid());
        return result;
    }

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

    @Override
    public PartnerDto toDto() {
        return GSON.fromJson(this.toJson(),PartnerDto.class);
    }
}
