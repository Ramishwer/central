package com.goev.central.dao.location;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LocationAttributeDao extends BaseDao {
    private Integer locationId;
    private String attributeKey;
    private String attributeValue;
}




