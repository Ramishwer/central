package com.goev.central.dto.vehicle.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.vehicle.detail.VehicleSegmentDao;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleSegmentDto {
    private String name;
    private String uuid;
    private String description;

    public static VehicleSegmentDto fromDao(VehicleSegmentDao vehicleSegmentDao){
        return VehicleSegmentDto.builder()
                .uuid(vehicleSegmentDao.getUuid())
                .description(vehicleSegmentDao.getDescription())
                .name(vehicleSegmentDao.getName())
                .build();
    }
}
