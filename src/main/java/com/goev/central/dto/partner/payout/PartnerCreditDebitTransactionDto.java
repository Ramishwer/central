package com.goev.central.dto.partner.payout;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.payout.PartnerCreditDebitTransactionDao;
import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.payout.PayoutElementDto;
import com.google.common.reflect.TypeToken;
import lombok.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerCreditDebitTransactionDto {

    private String uuid;
    private PartnerViewDto partnerDetails;
    private PartnerPayoutDto payoutDetails;
    private Integer amount;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime applicableTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime transactionTime;
    private PartnerCreditDebitDetailDto details;
    private String transactionType;
    private String status;


    public static PartnerCreditDebitTransactionDto fromDao(PartnerCreditDebitTransactionDao partnerCreditDebitTransactionDao, PartnerPayoutDto partnerPayoutDto, PartnerViewDto partnerViewDto) {
        if (partnerCreditDebitTransactionDao == null)
            return null;
        PartnerCreditDebitTransactionDto result =  PartnerCreditDebitTransactionDto.builder()
                .uuid(partnerCreditDebitTransactionDao.getUuid())
                .transactionTime(partnerCreditDebitTransactionDao.getTransactionTime())
                .applicableTime(partnerCreditDebitTransactionDao.getApplicableTime())
                .amount(partnerCreditDebitTransactionDao.getAmount())
                .payoutDetails(partnerPayoutDto)
                .transactionType(partnerCreditDebitTransactionDao.getTransactionType())
                .partnerDetails(partnerViewDto)
                .build();

        if(partnerCreditDebitTransactionDao.getDetails()!=null)
            result.setDetails(ApplicationConstants.GSON.fromJson(partnerCreditDebitTransactionDao.getDetails(), PartnerCreditDebitDetailDto.class));
        return result;
    }
}
