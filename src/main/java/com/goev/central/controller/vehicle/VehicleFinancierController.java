package com.goev.central.controller.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleFinancierDto;
import com.goev.central.service.vehicle.VehicleFinancierService;
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
public class VehicleFinancierController {

    private final VehicleFinancierService vehicleFinancierService;

    @GetMapping("/financiers")
    public ResponseDto<PaginatedResponseDto<VehicleFinancierDto>> getFinanciers(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancierService.getFinanciers());
    }
    @PostMapping("/financiers")
    public ResponseDto<VehicleFinancierDto> createFinancier(@RequestBody VehicleFinancierDto vehicleFinancierDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancierService.createFinancier(vehicleFinancierDto));
    }

    @PutMapping("/financiers/{financier-uuid}")
    public ResponseDto<VehicleFinancierDto> updateFinancier(@PathVariable(value = "financier-uuid")String financierUUID, @RequestBody VehicleFinancierDto vehicleFinancierDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancierService.updateFinancier(financierUUID,vehicleFinancierDto));
    }

    @GetMapping("/financiers/{financier-uuid}")
    public ResponseDto<VehicleFinancierDto> getFinancierDetails(@PathVariable(value = "financier-uuid")String financierUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancierService.getFinancierDetails(financierUUID));
    }

    @DeleteMapping("/financiers/{financier-uuid}")
    public ResponseDto<Boolean> deleteFinancier(@PathVariable(value = "financier-uuid")String financierUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancierService.deleteFinancier(financierUUID));
    }
}
