package com.goev.central.service.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleLeasingAgencyDto;

public interface VehicleLeasingAgencyService {
    PaginatedResponseDto<VehicleLeasingAgencyDto> getLeasingAgencies();
    VehicleLeasingAgencyDto createLeasingAgency(VehicleLeasingAgencyDto vehicleLeasingAgencyDto);
    VehicleLeasingAgencyDto updateLeasingAgency(String leasingAgencyUUID, VehicleLeasingAgencyDto vehicleLeasingAgencyDto);
    VehicleLeasingAgencyDto getLeasingAgencyDetails(String leasingAgencyUUID);
    Boolean deleteLeasingAgency(String leasingAgencyUUID);

}
