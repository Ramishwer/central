package com.goev.central.controller.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
import com.goev.central.service.vehicle.detail.VehicleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleStatusController {

    private final VehicleService vehicleService;


    @GetMapping("/vehicles/status")
    public ResponseDto<PaginatedResponseDto<VehicleDto>> getVehicleStatus(@RequestParam("status")String status,@RequestParam(value = "recommendationsFor",required = false)String recommendationForPartnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleService.getVehicleStatus(status));
    }
}
