package com.goev.central.dao.partner.app;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerAppEventDao extends BaseDao {
    private Integer partnerId;
    private String eventName;
    private String eventDescription;
    private String eventData;
}


