package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleModelDto;
import com.goev.central.service.vehicle.detail.VehicleModelService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleModelController {

    private final VehicleModelService vehicleModelService;

    @GetMapping("/models")
    public ResponseDto<PaginatedResponseDto<VehicleModelDto>> getModels(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleModelService.getModels());
    }
    @PostMapping("/models")
    public ResponseDto<VehicleModelDto> createModel(@RequestBody VehicleModelDto vehicleModelDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleModelService.createModel(vehicleModelDto));
    }

    @PutMapping("/models/{model-uuid}")
    public ResponseDto<VehicleModelDto> updateModel(@PathVariable(value = "model-uuid")String modelUUID, @RequestBody VehicleModelDto vehicleModelDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleModelService.updateModel(modelUUID,vehicleModelDto));
    }

    @GetMapping("/models/{model-uuid}")
    public ResponseDto<VehicleModelDto> getModelDetails(@PathVariable(value = "model-uuid")String modelUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleModelService.getModelDetails(modelUUID));
    }

    @DeleteMapping("/models/{model-uuid}")
    public ResponseDto<Boolean> deleteModel(@PathVariable(value = "model-uuid")String modelUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleModelService.deleteModel(modelUUID));
    }
}
