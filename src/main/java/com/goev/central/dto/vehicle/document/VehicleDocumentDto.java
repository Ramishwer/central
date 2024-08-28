package com.goev.central.dto.vehicle.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.vehicle.document.VehicleDocumentDao;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDocumentDto {
    private String uuid;
    private String url;
    private VehicleDocumentTypeDto type;
    private String fileName;
    private String description;
    private String status;
    private Map<String, Object> data;

    public static  VehicleDocumentDto fromDao(VehicleDocumentDao vehicleDocumentDao, VehicleDocumentTypeDto vehicleDocumentTypeDto){
        if(vehicleDocumentDao==null)
            return null;
        return VehicleDocumentDto.builder()
                .uuid(vehicleDocumentDao.getUuid())
                .type(vehicleDocumentTypeDto)
                .fileName(vehicleDocumentDao.getFileName())
                .description(vehicleDocumentDao.getDescription())
                .status(vehicleDocumentDao.getStatus())
                .url(vehicleDocumentDao.getUrl())
                .build();
    }
}
