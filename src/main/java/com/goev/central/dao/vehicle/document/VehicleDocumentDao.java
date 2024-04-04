package com.goev.central.dao.vehicle.document;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDocumentDao extends BaseDao {
    private Integer vehicleId;
    private Integer vehicleDocumentTypeId;
    private String url;
    private String fileName;
    private String description;
    private String extension;
    private String status;
    private String data;
}
