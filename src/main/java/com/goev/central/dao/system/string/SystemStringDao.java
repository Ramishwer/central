package com.goev.central.dao.system.string;


import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SystemStringDao extends BaseDao {
    private String key;
    private String language;
    private String value;
    private Integer parentId;
}



