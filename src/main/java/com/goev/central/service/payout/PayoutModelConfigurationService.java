package com.goev.central.service.payout;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutModelConfigurationDto;

public interface PayoutModelConfigurationService {
    PaginatedResponseDto<PayoutModelConfigurationDto> getPayoutModelConfigurations(String modelUUID);

    PayoutModelConfigurationDto createPayoutModelConfiguration(String modelUUID, PayoutModelConfigurationDto payoutModelConfigurationDto);

    PayoutModelConfigurationDto updatePayoutModelConfiguration(String modelUUID, String payoutModelConfigurationUUID, PayoutModelConfigurationDto payoutModelConfigurationDto);

    PayoutModelConfigurationDto getPayoutModelConfigurationDetails(String modelUUID, String payoutModelConfigurationUUID);

    Boolean deletePayoutModelConfiguration(String modelUUID, String payoutModelConfigurationUUID);
}
