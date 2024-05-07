package com.goev.central.controller.vehicle.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleLeasingAgencyDto;
import com.goev.central.service.vehicle.detail.VehicleLeasingAgencyService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleLeasingAgencyController {
    private final VehicleLeasingAgencyService vehicleLeasingAgencyService;

    @GetMapping("/leasing-agencies")
    public ResponseDto<PaginatedResponseDto<VehicleLeasingAgencyDto>> getLeasingAgencies(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleLeasingAgencyService.getLeasingAgencies());
    }
    @PostMapping("/leasing-agencies")
    public ResponseDto<VehicleLeasingAgencyDto> createLeasingAgency(@RequestBody VehicleLeasingAgencyDto vehicleLeasingAgencyDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleLeasingAgencyService.createLeasingAgency(vehicleLeasingAgencyDto));
    }

    @PutMapping("/leasing-agencies/{leasingAgency-uuid}")
    public ResponseDto<VehicleLeasingAgencyDto> updateLeasingAgency(@PathVariable(value = "leasingAgency-uuid")String leasingAgencyUUID, @RequestBody VehicleLeasingAgencyDto vehicleLeasingAgencyDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleLeasingAgencyService.updateLeasingAgency(leasingAgencyUUID,vehicleLeasingAgencyDto));
    }

    @GetMapping("/leasing-agencies/{leasing-agency-uuid}")
    public ResponseDto<VehicleLeasingAgencyDto> getLeasingAgencyDetails(@PathVariable(value = "leasing-agency-uuid")String leasingAgencyUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleLeasingAgencyService.getLeasingAgencyDetails(leasingAgencyUUID));
    }

    @DeleteMapping("/leasing-agencies/{leasing-agency-uuid}")
    public ResponseDto<Boolean> deleteLeasingAgency(@PathVariable(value = "leasing-agency-uuid")String leasingAgencyUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, vehicleLeasingAgencyService.deleteLeasingAgency(leasingAgencyUUID));
    }
}
