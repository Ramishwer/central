package com.goev.central.dto.partner.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.partner.detail.PartnerSegmentMappingDao;
import com.goev.central.dto.partner.PartnerViewDto;

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
    private PartnerSegmentDto segment;
    private PartnerViewDto partner;


    public static PartnerSegmentMappingDto fromDao(PartnerSegmentMappingDao partnerSegmentMappingDao,PartnerSegmentDto partnerSegmentDto,PartnerViewDto partner){
          return PartnerSegmentMappingDto.builder()
                  .uuid(partnerSegmentMappingDao.getUuid())
                  .segment(partnerSegmentDto)
                  .partner(partner)
                  .build();
    }
}
