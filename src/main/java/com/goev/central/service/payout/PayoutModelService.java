package com.goev.central.service.payout;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutModelDto;

public interface PayoutModelService {
    PaginatedResponseDto<PayoutModelDto> getPayoutModels();

    PayoutModelDto createPayoutModel(PayoutModelDto payoutModelDto);

    PayoutModelDto updatePayoutModel(String payoutModelUUID, PayoutModelDto payoutModelDto);

    PayoutModelDto getPayoutModelDetails(String payoutModelUUID);

    Boolean deletePayoutModel(String payoutModelUUID);
}
