package com.goev.central.dao.partner.payout;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dto.partner.payout.PartnerCreditDebitTransactionDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerCreditDebitTransactionDao extends BaseDao {
    private Integer partnerPayoutId;
    private Integer partnerId;
    private Integer amount;
    private DateTime transactionTime;
    private DateTime applicableTime;
    private String details;
    private String status;
    private String transactionType;

    public static PartnerCreditDebitTransactionDao fromDto(PartnerCreditDebitTransactionDto transactionDto, Integer partnerId, Integer partnerPayoutId) {

        PartnerCreditDebitTransactionDao result = new PartnerCreditDebitTransactionDao();
        if (transactionDto == null)
            return result;
        result.setPartnerPayoutId(partnerPayoutId);
        result.setPartnerId(partnerId);
        result.setAmount(transactionDto.getAmount());
        result.setStatus(transactionDto.getStatus());
        result.setApplicableTime(transactionDto.getApplicableTime());
        result.setTransactionTime(transactionDto.getTransactionTime());
        result.setTransactionType(transactionDto.getTransactionType());
        if (transactionDto.getDetails() != null)
            result.setDetails(ApplicationConstants.GSON.toJson(transactionDto.getDetails()));
        return result;
    }
}


