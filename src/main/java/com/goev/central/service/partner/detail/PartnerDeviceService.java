package com.goev.central.service.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerDeviceDto;

public interface PartnerDeviceService {
    PaginatedResponseDto<PartnerDeviceDto> getPartnerDevices(String partnerUUID);

    PartnerDeviceDto getPartnerDeviceDetails(String partnerUUID, String partnerDeviceUUID);

    Boolean deletePartnerDevice(String partnerUUID, String partnerDeviceUUID);
}
