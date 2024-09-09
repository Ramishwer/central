package com.goev.central.dao.business;

import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.lib.dao.BaseDao;
import com.goev.lib.dto.ContactDetailsDto;
import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BusinessClientDao extends BaseDao {
    private String name;
    private String description;
    private Integer businessClientDetailsId;
    private String displayCode;

}
