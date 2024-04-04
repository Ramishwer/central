package com.goev.central.dto.vehicle.document;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleDocumentDto {
    private String uuid;
    private String url;
    private VehicleDocumentTypeDto type;
    private String fileName;
    private String description;
    private String status;
    private Map<String,Object> data;
}
