package com.goev.central.service.customer.app;

import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppEventDto;

public interface CustomerAppEventService {
    PaginatedResponseDto<CustomerAppEventDto> getCustomerAppEvents(String customerUUID);

    CustomerAppEventDto getCustomerAppEventDetails(String customerUUID, String appEventUUID);
}
