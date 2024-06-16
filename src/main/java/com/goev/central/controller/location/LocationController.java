package com.goev.central.controller.location;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.service.location.LocationService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/location-management")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/locations")
    public ResponseDto<PaginatedResponseDto<LocationDto>> getLocations() {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationService.getLocations());
    }

    @PostMapping("/locations")
    public ResponseDto<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationService.createLocation(locationDto));
    }

    @PutMapping("/locations/{location-uuid}")
    public ResponseDto<LocationDto> updateLocation(@PathVariable(value = "location-uuid") String locationUUID, @RequestBody LocationDto locationDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationService.updateLocation(locationUUID, locationDto));
    }

    @GetMapping("/locations/{location-uuid}")
    public ResponseDto<LocationDto> getLocationDetails(@PathVariable(value = "location-uuid") String locationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationService.getLocationDetails(locationUUID));
    }

    @GetMapping("/locations/{location-uuid}/qr")
    public ResponseDto<String> getLocationQr(@PathVariable(value = "location-uuid") String locationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationService.getLocationQr(locationUUID));
    }


    @DeleteMapping("/locations/{location-uuid}")
    public ResponseDto<Boolean> deleteLocation(@PathVariable(value = "location-uuid") String locationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, locationService.deleteLocation(locationUUID));
    }
}
