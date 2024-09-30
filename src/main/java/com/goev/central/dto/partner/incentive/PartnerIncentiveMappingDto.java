package com.goev.central.dto.partner.incentive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.partner.incentive.PartnerIncentiveMappingDao;
import com.goev.central.dto.incentive.IncentiveModelDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.payout.PayoutModelDto;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerIncentiveMappingDto {

    private String uuid;
    private String triggerType;
    private PartnerViewDto partnerDetails;
    private IncentiveModelDto incentiveModel;

    public static PartnerIncentiveMappingDto fromDao(PartnerIncentiveMappingDao partnerIncentiveMappingDao, PartnerViewDto partnerViewDto, IncentiveModelDto incentiveModelDto) {
        if(partnerIncentiveMappingDao == null)
            return null;
        return PartnerIncentiveMappingDto.builder()
                .incentiveModel(incentiveModelDto)
                .partnerDetails(partnerViewDto)
                .triggerType(partnerIncentiveMappingDao.getTriggerType())
                .uuid(partnerIncentiveMappingDao.getUuid())
                .build();
    }
}
