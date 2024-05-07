package com.goev.central.service.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerDeviceDto;

public interface CustomerDeviceService {
    PaginatedResponseDto<CustomerDeviceDto> getCustomerDevices(String customerUUID);

    CustomerDeviceDto getCustomerDeviceDetails(String customerUUID, String customerDeviceUUID);

    Boolean deleteCustomerDevice(String customerUUID, String customerDeviceUUID);
}
