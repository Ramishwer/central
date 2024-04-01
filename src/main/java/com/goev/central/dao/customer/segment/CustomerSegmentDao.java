package com.goev.central.dao.customer.segment;

import com.goev.lib.dao.BaseDao;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerSegmentDao extends BaseDao {
    private String name;
    private String description;
}


