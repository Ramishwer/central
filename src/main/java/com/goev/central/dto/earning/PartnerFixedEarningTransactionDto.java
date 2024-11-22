package com.goev.central.dto.earning;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.earning.PartnerFixedEarningTransactionDao;
import lombok.*;
import org.joda.time.DateTime;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerFixedEarningTransactionDto {
    private Integer id;
    private String uuid;
    private String ruleId;
    private Integer partnerId;
    private String earningRuleId;
    private String businessType;
    private String clientName;
    private Float transaction_amount;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime transaction_date;
    private String transactionType;


    public static PartnerFixedEarningTransactionDto fromDao(PartnerFixedEarningTransactionDao partnerFixedEarningTransactionDao){
        if(partnerFixedEarningTransactionDao==null){
            return null;
        }
        return PartnerFixedEarningTransactionDto.builder()
                .id(partnerFixedEarningTransactionDao.getId())
                .uuid(partnerFixedEarningTransactionDao.getUuid())
                .partnerId(partnerFixedEarningTransactionDao.getPartnerId())
                .ruleId(partnerFixedEarningTransactionDao.getEarningRuleId())
                .businessType(partnerFixedEarningTransactionDao.getBusinessType())
                .clientName(partnerFixedEarningTransactionDao.getClientName())
                .transaction_amount(partnerFixedEarningTransactionDao.getTransactionAmount())
                .transactionType(partnerFixedEarningTransactionDao.getTransactionType())
                .transaction_date(partnerFixedEarningTransactionDao.getTransactionDate())
                .build();
    }



}
