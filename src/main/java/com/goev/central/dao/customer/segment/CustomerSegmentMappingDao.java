package com.goev.central.dao.customer.segment;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerSegmentMappingDao extends BaseDao {
    private Integer customerId;
    private Integer customerSegmentId;
}


