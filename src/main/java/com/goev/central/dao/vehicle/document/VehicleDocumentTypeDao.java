package com.goev.central.dao.vehicle.document;

import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.user.detail.UserDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentTypeDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

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
    private Boolean isMandatory;
    private Boolean needsVerification;

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
