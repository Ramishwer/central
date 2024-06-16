package com.goev.central.dao.customer.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerAttributeDao extends BaseDao {
    private Integer customerId;
    private String attributeKey;
    private String attributeValue;
}


