package com.goev.central.dto.partner.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.payout.PayoutElementDto;
import lombok.*;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerPayoutSummaryDto {
    private Integer totalPayoutAmount;
    private Integer totalCreditAmount;
    private Integer totalDebitAmount;
    private Integer totalBookingAmount;
    private Integer totalDeductionAmount;
    private Integer totalCashbookAmount;
    private Integer totalGstAmount;
    private Map<String,String> elements;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime payoutStartDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime payoutEndDate;
}
