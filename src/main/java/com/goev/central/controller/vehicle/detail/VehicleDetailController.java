package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.vehicle.detail.VehicleDetailDto;
import com.goev.central.service.vehicle.detail.VehicleDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleDetailController {


    private final VehicleDetailService vehicleDetailService;

    @PostMapping("/vehicles/details")
    public ResponseDto<VehicleDetailDto> createVehicle(@RequestBody VehicleDetailDto vehicleDetailDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDetailService.createVehicle(vehicleDetailDto));
    }

    @PutMapping("/vehicles/{vehicle-uuid}/details")
    public ResponseDto<VehicleDetailDto> updateVehicle(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @RequestBody VehicleDetailDto vehicleDetailDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDetailService.updateVehicle(vehicleUUID, vehicleDetailDto));
    }

    @GetMapping("/vehicles/{vehicle-uuid}/details")
    public ResponseDto<VehicleDetailDto> getVehicleDetails(@PathVariable(value = "vehicle-uuid") String vehicleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDetailService.getVehicleDetails(vehicleUUID));
    }

    @GetMapping("/vehicles/{vehicle-uuid}/qr")
    public ResponseDto<String> getVehicleQr(@PathVariable(value = "vehicle-uuid") String vehicleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleDetailService.getVehicleQr(vehicleUUID));
    }

}
