


package com.goev.central.dao.customer.app;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerAppEventDao extends BaseDao {
    private Integer customerId;
    private String eventName;
    private String eventDescription;
    private String eventData;
}


