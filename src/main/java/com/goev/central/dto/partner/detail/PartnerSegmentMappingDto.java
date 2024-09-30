package com.goev.central.dto.partner.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.partner.detail.PartnerSegmentMappingDao;
import com.goev.central.dto.partner.PartnerViewDto;

import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerSegmentMappingDto {
    private String uuid;
    private PartnerSegmentDto segmentDetails;
    private PartnerViewDto partnerDetails;
    private VehicleSegmentDto vehicleSegment;


    public static PartnerSegmentMappingDto fromDao(PartnerSegmentMappingDao partnerSegmentMappingDao,PartnerSegmentDto partnerSegmentDto,PartnerViewDto partner,VehicleSegmentDto vehicleSegmentDto){
          return PartnerSegmentMappingDto.builder()
                  .uuid(partnerSegmentMappingDao.getUuid())
                  .segmentDetails(partnerSegmentDto)
                  .partnerDetails(partner)
                  .vehicleSegment(vehicleSegmentDto)
                  .build();
    }
}
