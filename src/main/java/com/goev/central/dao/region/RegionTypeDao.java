package com.goev.central.dao.region;


import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegionTypeDao extends BaseDao {
    private String name;
    private String boundType;
}



