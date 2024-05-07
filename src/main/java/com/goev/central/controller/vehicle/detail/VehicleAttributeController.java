package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleAttributeDto;
import com.goev.central.service.vehicle.detail.VehicleAttributeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleAttributeController {

    private final VehicleAttributeService vehicleAttributeService;

    @GetMapping("/vehicles/{vehicle-uuid}/attributes")
    public ResponseDto<PaginatedResponseDto<VehicleAttributeDto>> getVehicleAttributes(
            @PathVariable(value = "vehicle-uuid") String vehicleUUID,
            @RequestParam("count") Integer count,
            @RequestParam("start") Integer start,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to,
            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAttributeService.getVehicleAttributes(vehicleUUID));
    }


    @GetMapping("/vehicles/{vehicle-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<VehicleAttributeDto> getVehicleAttributeDetails(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAttributeService.getVehicleAttributeDetails(vehicleUUID, attributeUUID));
    }


    @PostMapping("/vehicles/{vehicle-uuid}/attributes")
    public ResponseDto<VehicleAttributeDto> createVehicleAttribute(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @RequestBody VehicleAttributeDto vehicleAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAttributeService.createVehicleAttribute(vehicleUUID, vehicleAttributeDto));
    }

    @PutMapping("/vehicles/{vehicle-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<VehicleAttributeDto> updateVehicleAttribute(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @PathVariable(value = "attribute-uuid") String attributeUUID, @RequestBody VehicleAttributeDto vehicleAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAttributeService.updateVehicleAttribute(vehicleUUID, attributeUUID, vehicleAttributeDto));
    }

    @DeleteMapping("/vehicles/{vehicle-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<Boolean> deleteVehicleAttribute(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleAttributeService.deleteVehicleAttribute(vehicleUUID, attributeUUID));
    }
}
