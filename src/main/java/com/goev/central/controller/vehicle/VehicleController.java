package com.goev.central.controller.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDetailsDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
import com.goev.central.service.vehicle.VehicleService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleController {


    private final VehicleService vehicleService;

    @GetMapping("/vehicles")
    public ResponseDto<PaginatedResponseDto<VehicleViewDto>> getVehicles(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleService.getVehicles());
    }
    @PostMapping("/vehicles")
    public ResponseDto<VehicleDetailsDto> createVehicle(@RequestBody VehicleDetailsDto vehicleDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleService.createVehicle(vehicleDto));
    }

    @PutMapping("/vehicles/{vehicle-uuid}")
    public ResponseDto<VehicleDetailsDto> updateVehicle(@PathVariable(value = "vehicle-uuid")String vehicleUUID, @RequestBody VehicleDetailsDto vehicleDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleService.updateVehicle(vehicleUUID,vehicleDto));
    }

    @GetMapping("/vehicles/{vehicle-uuid}")
    public ResponseDto<VehicleDetailsDto> getVehicleDetails(@PathVariable(value = "vehicle-uuid")String vehicleUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleService.getVehicleDetails(vehicleUUID));
    }

    @DeleteMapping("/vehicles/{vehicle-uuid}")
    public ResponseDto<Boolean> deleteVehicle(@PathVariable(value = "vehicle-uuid")String vehicleUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleService.deleteVehicle(vehicleUUID));
    }
}

