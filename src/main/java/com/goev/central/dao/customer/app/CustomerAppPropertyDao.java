


package com.goev.central.dao.customer.app;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerAppPropertyDao extends BaseDao {
    private String propertyName;
    private String propertyDescription;
    private String propertyType;
    private String propertyValue;
}


