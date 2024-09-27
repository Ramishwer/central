package com.goev.central.dto.partner.payout;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dto.partner.PartnerViewDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerPayoutDto {

    private String uuid;
    private PartnerViewDto partnerDetails;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime payoutStartDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime payoutEndDate;
    private String status;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime finalizationDate;
    private PartnerPayoutSummaryDto payoutSummary;
    private Integer payoutTotalBookingAmount;
    private Integer payoutTotalAmount;
    private Integer payoutTotalDeductionAmount;
    private Integer payoutTotalCreditAmount;
    private Integer payoutTotalDebitAmount;

    public static PartnerPayoutDto fromDao(PartnerPayoutDao payoutDao, PartnerViewDto partnerViewDto) {
       if(payoutDao == null)
        return null;
       return PartnerPayoutDto.builder()
               .partnerDetails(partnerViewDto)
               .payoutStartDate(payoutDao.getPayoutStartDate())
               .payoutEndDate(payoutDao.getPayoutEndDate())
               .payoutTotalAmount(payoutDao.getPayoutTotalAmount())
               .payoutTotalBookingAmount(payoutDao.getPayoutTotalBookingAmount())
               .payoutTotalDeductionAmount(payoutDao.getPayoutTotalDeductionAmount())
               .payoutTotalCreditAmount(payoutDao.getPayoutTotalCreditAmount())
               .finalizationDate(payoutDao.getFinalizationDate())
               .status(payoutDao.getStatus())
               .payoutSummary(ApplicationConstants.GSON.fromJson(payoutDao.getPayoutSummary(),PartnerPayoutSummaryDto.class))
               .build();
    }
}
