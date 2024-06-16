package com.goev.central.dao.customer.notification;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerNotificationDao extends BaseDao {
    private Integer customerId;
    private String status;
    private Integer notificationTemplateId;
    private String requestMetadata;
    private String responseMetadata;
}


