package com.goev.central.controller.vehicle.transfer;

import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.transfer.VehicleTransferDto;
import com.goev.central.service.vehicle.transfer.VehicleTransferService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleTransferController {

    private final VehicleTransferService vehicleTransferService;


    @GetMapping("/vehicles/transfers")
    public ResponseDto<PaginatedResponseDto<VehicleTransferDto>> getTransfers(@RequestParam("status")String status, PageDto page) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleTransferService.getTransfers(status,page));
    }

    @GetMapping("/vehicles/{vehicle-uuid}/transfers")
    public ResponseDto<PaginatedResponseDto<VehicleTransferDto>> getTransfersForVehicle(@PathVariable("vehicle-uuid") String vehicleUUID,
                                                                                        @RequestParam("count") Integer count,
                                                                                        @RequestParam("start") Integer start) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleTransferService.getTransfersForVehicle(vehicleUUID));
    }

}
