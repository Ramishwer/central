package com.goev.central.dao.business;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BusinessSegmentDao extends BaseDao {
    private String name;
    private String description;
}
