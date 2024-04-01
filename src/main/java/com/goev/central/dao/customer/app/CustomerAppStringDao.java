


package com.goev.central.dao.customer.app;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerAppStringDao extends BaseDao {
    private String key;
    private String language;
    private String value;
    private Integer parentId;
}


