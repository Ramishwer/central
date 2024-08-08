package com.goev.central.dto.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.business.BusinessSegmentDao;
import com.goev.central.dao.partner.detail.PartnerSegmentDao;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessSegmentDto {
    private String uuid;
    private String name;
    private String description;

    public static BusinessSegmentDto fromDao(BusinessSegmentDao businessSegmentDao){
        return BusinessSegmentDto.builder()
                .uuid(businessSegmentDao.getUuid())
                .description(businessSegmentDao.getDescription())
                .name(businessSegmentDao.getName())
                .build();
    }
}
