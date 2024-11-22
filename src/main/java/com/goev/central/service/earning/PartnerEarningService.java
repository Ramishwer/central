package com.goev.central.service.earning;

import com.goev.central.dto.earning.PartnerEarningDto;
import com.goev.central.dto.earning.PartnerFixedEarningTransactionDto;

import java.util.List;

public interface PartnerEarningService {

    PartnerEarningDto getPartnerEarningDetails(String partnerUuid);

    List<PartnerFixedEarningTransactionDto> getPartnerEarningTransactions(String partnerUuid);
}
