package com.goev.central.dto.partner.payout;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.constant.ApplicationConstants;
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
public class PartnerPayoutTransactionDto {

    private String uuid;
    private PartnerViewDto partnerDetails;
    private PartnerPayoutDto payoutDetails;
    private String date;
    private String day;
    private Integer amount;
    private String title;
    private String subtitle;
    private String message;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime transactionTime;
    private List<PayoutElementDto> calculatedPayoutElements;
    private String status;


    public static PartnerPayoutTransactionDto fromDao(PartnerPayoutTransactionDao partnerPayoutTransactionDao, PartnerPayoutDto partnerPayoutDto, PartnerViewDto partnerViewDto) {
        if (partnerPayoutTransactionDao == null)
            return null;
        PartnerPayoutTransactionDto result = PartnerPayoutTransactionDto.builder()
                .uuid(partnerPayoutTransactionDao.getUuid())
                .day(partnerPayoutTransactionDao.getDay())
                .date(partnerPayoutTransactionDao.getDate())
                .title(partnerPayoutTransactionDao.getTitle())
                .subtitle(partnerPayoutTransactionDao.getSubtitle())
                .message(partnerPayoutTransactionDao.getMessage())
                .transactionTime(partnerPayoutTransactionDao.getTransactionTime())
                .amount(partnerPayoutTransactionDao.getAmount())
                .payoutDetails(partnerPayoutDto)
                .partnerDetails(partnerViewDto)
                .build();

        if (partnerPayoutTransactionDao.getCalculatedPayoutElements() != null) {
            Type t = new TypeToken<List<PayoutElementDto>>() {
            }.getType();
            result.setCalculatedPayoutElements(ApplicationConstants.GSON.fromJson(partnerPayoutTransactionDao.getCalculatedPayoutElements(), t));
        }
        return result;

    }
}
