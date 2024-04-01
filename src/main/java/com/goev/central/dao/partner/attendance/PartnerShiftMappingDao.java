


package com.goev.central.dao.partner.attendance;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerShiftMappingDao extends BaseDao {
    private Integer partnerId;
    private Integer partnerShiftConfigurationId;
    private Boolean isDefault;
}


