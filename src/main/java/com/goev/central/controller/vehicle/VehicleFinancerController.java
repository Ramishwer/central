package com.goev.central.controller.vehicle;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleFinancerDto;
import com.goev.central.service.vehicle.VehicleFinancerService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleFinancerController {

    private final VehicleFinancerService vehicleFinancerService;

    @GetMapping("/financers")
    public ResponseDto<PaginatedResponseDto<VehicleFinancerDto>> getFinancers(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancerService.getFinancers());
    }
    @PostMapping("/financers")
    public ResponseDto<VehicleFinancerDto> createFinancer(@RequestBody VehicleFinancerDto vehicleFinancerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancerService.createFinancer(vehicleFinancerDto));
    }

    @PutMapping("/financers/{financer-uuid}")
    public ResponseDto<VehicleFinancerDto> updateFinancer(@PathVariable(value = "financer-uuid")String financerUUID, @RequestBody VehicleFinancerDto vehicleFinancerDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancerService.updateFinancer(financerUUID,vehicleFinancerDto));
    }

    @GetMapping("/financers/{financer-uuid}")
    public ResponseDto<VehicleFinancerDto> getFinancerDetails(@PathVariable(value = "financer-uuid")String financerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancerService.getFinancerDetails(financerUUID));
    }

    @DeleteMapping("/financers/{financer-uuid}")
    public ResponseDto<Boolean> deleteFinancer(@PathVariable(value = "financer-uuid")String financerUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleFinancerService.deleteFinancer(financerUUID));
    }
}
