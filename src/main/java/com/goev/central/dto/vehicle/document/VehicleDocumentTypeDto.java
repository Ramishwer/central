package com.goev.central.dto.vehicle.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDocumentTypeDto {
    private String uuid;
    private String name;
    private String s3Key;
    private String label;
    private String groupKey;
    private String groupDescription;
    @Builder.Default
    private Boolean isMandatory=false;
    @Builder.Default
    private Boolean needsVerification=false;


    public static VehicleDocumentTypeDto fromDao(VehicleDocumentTypeDao vehicleDocumentTypeDao) {
        if(vehicleDocumentTypeDao==null)
            return null;
        return VehicleDocumentTypeDto.builder()
                .s3Key(vehicleDocumentTypeDao.getS3Key())
                .label(vehicleDocumentTypeDao.getLabel())
                .name(vehicleDocumentTypeDao.getName())
                .uuid(vehicleDocumentTypeDao.getUuid())
                .groupKey(vehicleDocumentTypeDao.getGroupKey())
                .groupDescription(vehicleDocumentTypeDao.getGroupDescription())
                .isMandatory(vehicleDocumentTypeDao.getIsMandatory())
                .needsVerification(vehicleDocumentTypeDao.getNeedsVerification())
                .build();
    }
}
