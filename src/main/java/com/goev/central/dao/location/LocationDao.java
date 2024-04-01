package com.goev.central.dao.location;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LocationDao extends BaseDao {
    private String name;
    private String type;
    private Float latitude;
    private Float longitude;
    private Integer locationDetailsId;
}




