package com.goev.central.dto.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.business.BusinessSegmentMappingDao;
import com.goev.central.dao.vehicle.detail.VehicleSegmentMappingDao;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
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
public class BusinessSegmentMappingDto {
    private String uuid;
    private BusinessSegmentDto segment;
    private VehicleSegmentDto vehicleSegment;
    private PartnerSegmentDto partnerSegment;



    public static BusinessSegmentMappingDto fromDao(BusinessSegmentMappingDao businessSegmentMappingDao, BusinessSegmentDto businessSegmentDto, VehicleSegmentDto vehicleSegmentDto,PartnerSegmentDto partnerSegmentDto){
          return BusinessSegmentMappingDto.builder()
                  .uuid(businessSegmentMappingDao.getUuid())
                  .segment(businessSegmentDto)
                  .vehicleSegment(vehicleSegmentDto)
                  .partnerSegment(partnerSegmentDto)
                  .build();
    }
}
