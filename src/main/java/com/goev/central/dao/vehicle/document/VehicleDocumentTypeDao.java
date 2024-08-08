package com.goev.central.dao.vehicle.document;

import com.goev.central.dto.vehicle.document.VehicleDocumentTypeDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDocumentTypeDao extends BaseDao {
    private String name;
    private String s3Key;
    private String label;
    private String groupKey;
    private String groupDescription;
    private Boolean isMandatory = false;
    private Boolean needsVerification = false;

    public static VehicleDocumentTypeDao fromDto(VehicleDocumentTypeDto vehicleDocumentTypeDto) {
        VehicleDocumentTypeDao vehicleDocumentTypeDao = new VehicleDocumentTypeDao();

        vehicleDocumentTypeDao.setName(vehicleDocumentTypeDto.getName());
        vehicleDocumentTypeDao.setS3Key(vehicleDocumentTypeDto.getS3Key());
        vehicleDocumentTypeDao.setLabel(vehicleDocumentTypeDto.getLabel());
        vehicleDocumentTypeDao.setUuid(vehicleDocumentTypeDto.getUuid());

        vehicleDocumentTypeDao.setGroupKey(vehicleDocumentTypeDto.getGroupKey());
        vehicleDocumentTypeDao.setGroupDescription(vehicleDocumentTypeDto.getGroupDescription());
        vehicleDocumentTypeDao.setIsMandatory(vehicleDocumentTypeDto.getIsMandatory());
        vehicleDocumentTypeDao.setNeedsVerification(vehicleDocumentTypeDto.getNeedsVerification());

        return vehicleDocumentTypeDao;
    }


}
