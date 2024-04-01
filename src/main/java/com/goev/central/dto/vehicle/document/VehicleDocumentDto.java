package com.goev.central.dto.vehicle.document;

import lombok.*;

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
}
