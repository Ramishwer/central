package com.goev.central.service.payout;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutElementDto;

public interface PayoutElementService {
    PaginatedResponseDto<PayoutElementDto> getPayoutElements();

    PayoutElementDto createPayoutElement(PayoutElementDto payoutElementDto);

    PayoutElementDto updatePayoutElement(String payoutElementUUID, PayoutElementDto payoutElementDto);

    PayoutElementDto getPayoutElementDetails(String payoutElementUUID);

    Boolean deletePayoutElement(String payoutElementUUID);
}
