package com.goev.central.controller.system.property;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.system.property.SystemPropertyDto;
import com.goev.central.service.system.property.SystemPropertyService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/system-management")
@AllArgsConstructor
public class SystemPropertyController {

    private final SystemPropertyService systemPropertyService;

    @GetMapping("/system/properties")
    public ResponseDto<PaginatedResponseDto<SystemPropertyDto>> getSystemProperties(@RequestParam("count") Integer count,
                                                                                    @RequestParam("start") Integer start,
                                                                                    @RequestParam("from") Long from,
                                                                                    @RequestParam("to") Long to,
                                                                                    @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemPropertyService.getSystemProperties());
    }


    @GetMapping("/system/properties/{system-property-uuid}")
    public ResponseDto<SystemPropertyDto> getSystemPropertyDetails(@PathVariable(value = "system-property-uuid") String propertyUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemPropertyService.getSystemPropertyDetails(propertyUUID));
    }


    @PostMapping("/system/properties")
    public ResponseDto<SystemPropertyDto> createSystemProperty(@RequestBody SystemPropertyDto systemPropertyDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemPropertyService.createSystemProperty(systemPropertyDto));
    }

    @PutMapping("/system/properties/{system-property-uuid}")
    public ResponseDto<SystemPropertyDto> updateSystemProperty(@PathVariable(value = "system-property-uuid") String propertyUUID, @RequestBody SystemPropertyDto systemPropertyDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemPropertyService.updateSystemProperty(propertyUUID, systemPropertyDto));
    }

    @DeleteMapping("/system/properties/{system-property-uuid}")
    public ResponseDto<Boolean> deleteSystemProperty(@PathVariable(value = "system-property-uuid") String propertyUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, systemPropertyService.deleteSystemProperty(propertyUUID));
    }
}
