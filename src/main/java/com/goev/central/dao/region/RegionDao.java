package com.goev.central.dao.region;


import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegionDao extends BaseDao {
    private String name;
    private Integer regionTypeId;
    private String fileUrl;
    private String description;
}



