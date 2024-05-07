package com.goev.central.service.vehicle.detail;


import com.goev.central.dto.vehicle.detail.VehicleDetailDto;

public interface VehicleDetailService {
    VehicleDetailDto createVehicle(VehicleDetailDto vehicleDto);
    VehicleDetailDto getVehicleDetails(String vehicleUUID);

    VehicleDetailDto updateVehicle(String vehicleUUID,VehicleDetailDto vehicleDetailDto);

    String getVehicleQr(String vehicleUUID);
}
