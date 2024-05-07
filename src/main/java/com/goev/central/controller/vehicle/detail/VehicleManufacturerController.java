package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleManufacturerDto;
import com.goev.central.service.vehicle.detail.VehicleManufacturerService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleManufacturerController {


    private final VehicleManufacturerService vehicleManufacturerService;

    @GetMapping("/manufacturers")
    public ResponseDto<PaginatedResponseDto<VehicleManufacturerDto>> getManufacturers(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleManufacturerService.getManufacturers());
    }
    @PostMapping("/manufacturers")
    public ResponseDto<VehicleManufacturerDto> createManufacturer(@RequestBody VehicleManufacturerDto vehicleManufacturerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleManufacturerService.createManufacturer(vehicleManufacturerDto));
    }

    @PutMapping("/manufacturers/{manufacturer-uuid}")
    public ResponseDto<VehicleManufacturerDto> updateManufacturer(@PathVariable(value = "manufacturer-uuid")String manufacturerUUID, @RequestBody VehicleManufacturerDto vehicleManufacturerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleManufacturerService.updateManufacturer(manufacturerUUID,vehicleManufacturerDto));
    }

    @GetMapping("/manufacturers/{manufacturer-uuid}")
    public ResponseDto<VehicleManufacturerDto> getManufacturerDetails(@PathVariable(value = "manufacturer-uuid")String manufacturerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleManufacturerService.getManufacturerDetails(manufacturerUUID));
    }

    @DeleteMapping("/manufacturers/{manufacturer-uuid}")
    public ResponseDto<Boolean> deleteManufacturer(@PathVariable(value = "manufacturer-uuid")String manufacturerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleManufacturerService.deleteManufacturer(manufacturerUUID));
    }
}
