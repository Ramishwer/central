package com.goev.central.dto.vehicle.document;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleDocumentTypeDto {
    private String uuid;
    private String name;
    private String s3Key;
    private String label;
}
