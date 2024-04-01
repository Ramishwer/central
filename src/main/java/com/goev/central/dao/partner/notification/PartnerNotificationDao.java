


package com.goev.central.dao.partner.notification;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerNotificationDao extends BaseDao {
    private Integer partnerId;
    private String status;
    private Integer notificationTemplateId;
    private String requestMetadata;
    private String responseMetadata;
}


