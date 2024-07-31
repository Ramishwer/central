package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.partner.PartnerOnboardingStatus;
import com.goev.central.enums.vehicle.VehicleOnboardingStatus;
import com.goev.central.service.vehicle.detail.VehicleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleController {


    private final VehicleService vehicleService;


    @GetMapping("/vehicles")
    public ResponseDto<PaginatedResponseDto<VehicleViewDto>> getVehicles(@RequestParam(value = "onboardingStatus", required = false)String onboardingStatus) {
        if (onboardingStatus == null)
            return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200,vehicleService.getVehicles());
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleService.getVehicles(onboardingStatus));

    }

    @PostMapping("/vehicles/{vehicle-uuid}")
    public ResponseDto<Boolean> updateVehicle(@PathVariable("vehicle-uuid") String vehicleUUID, @RequestBody VehicleOnboardingStatus status) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleService.updateVehicleOnboardingStatus(vehicleUUID, status));
    }

    @DeleteMapping("/vehicles/{vehicle-uuid}")
    public ResponseDto<Boolean> deleteVehicle(@PathVariable(value = "vehicle-uuid") String vehicleUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleService.deleteVehicle(vehicleUUID));
    }
}