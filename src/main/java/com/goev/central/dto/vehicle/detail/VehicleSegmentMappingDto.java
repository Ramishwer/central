package com.goev.central.dto.vehicle.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.vehicle.detail.VehicleSegmentMappingDao;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleSegmentMappingDto {
    private String uuid;
    private VehicleSegmentDto segment;
    private VehicleViewDto vehicleDetails;


    public static VehicleSegmentMappingDto fromDao(VehicleSegmentMappingDao vehicleSegmentMappingDao, VehicleSegmentDto vehicleSegmentDto, VehicleViewDto vehicle){
          return VehicleSegmentMappingDto.builder()
                  .uuid(vehicleSegmentMappingDao.getUuid())
                  .segment(vehicleSegmentDto)
                  .vehicleDetails(vehicle)
                  .build();
    }
}
