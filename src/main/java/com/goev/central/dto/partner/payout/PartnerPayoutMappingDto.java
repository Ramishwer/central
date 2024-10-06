package com.goev.central.dto.partner.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.payout.PartnerPayoutMappingDao;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.payout.PayoutConfigDto;
import com.goev.central.dto.payout.PayoutElementDto;
import com.goev.central.dto.payout.PayoutModelDto;
import com.google.gson.reflect.TypeToken;
import lombok.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerPayoutMappingDto {

    private String uuid;
    private String triggerType;
    private PartnerViewDto partnerDetails;
    private Map<String, PayoutModelDto> config;
    private Map<String , PayoutConfigDto> payoutConfig;

    public static PartnerPayoutMappingDto fromDao(PartnerPayoutMappingDao mappingDao,PartnerViewDto partner){
        if(mappingDao==null)
            return null;
        Type t1 = new TypeToken<Map<String,PayoutModelDto>>(){}.getType();
        Type t2 = new TypeToken<Map<String, PayoutConfigDto>>(){}.getType();
        return PartnerPayoutMappingDto.builder()
                .partnerDetails(partner)
                .triggerType(mappingDao.getTriggerType())
                .config(ApplicationConstants.GSON.fromJson(mappingDao.getConfig(),t1))
                .payoutConfig(ApplicationConstants.GSON.fromJson(mappingDao.getPayoutConfig(),t2))
                .uuid(mappingDao.getUuid())
                .build();

    }
}
