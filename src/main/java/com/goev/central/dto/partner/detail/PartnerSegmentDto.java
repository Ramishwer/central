package com.goev.central.dto.partner.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.partner.detail.PartnerSegmentDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerSegmentDto {
    private String name;
    private String uuid;
    private String description;

    public static PartnerSegmentDto fromDao(PartnerSegmentDao partnerSegmentDao){
        return PartnerSegmentDto.builder()
                .uuid(partnerSegmentDao.getUuid())
                .description(partnerSegmentDao.getDescription())
                .name(partnerSegmentDao.getName())
                .build();
    }
}
