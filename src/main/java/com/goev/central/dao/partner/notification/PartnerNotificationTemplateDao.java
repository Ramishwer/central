


package com.goev.central.dao.partner.notification;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerNotificationTemplateDao extends BaseDao {
    private String title;
    private String body;
    private String templateMetadata;
    private String informationType;
    private String actionType;
    private String notificationType;
    private String templateKey;
}


