package com.goev.central.controller.customer.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.app.CustomerAppPropertyDto;
import com.goev.central.service.customer.app.CustomerAppPropertyService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerAppPropertyController {

    private final CustomerAppPropertyService customerAppPropertyService;

    @GetMapping("/app/properties")
    public ResponseDto<PaginatedResponseDto<CustomerAppPropertyDto>> getCustomerAppProperties(@RequestParam("count") Integer count,
                                                                                              @RequestParam("start") Integer start,
                                                                                              @RequestParam("from") Long from,
                                                                                              @RequestParam("to") Long to,
                                                                                              @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppPropertyService.getCustomerAppProperties());
    }


    @GetMapping("/app/properties/{app-property-uuid}")
    public ResponseDto<CustomerAppPropertyDto> getCustomerAppPropertyDetails(@PathVariable(value = "app-property-uuid") String appPropertyUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppPropertyService.getCustomerAppPropertyDetails(appPropertyUUID));
    }


    @PostMapping("/app/properties")
    public ResponseDto<CustomerAppPropertyDto> createCustomerAppProperty(@RequestBody CustomerAppPropertyDto customerAppPropertyDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppPropertyService.createCustomerAppProperty(customerAppPropertyDto));
    }

    @PutMapping("/app/properties/{app-property-uuid}")
    public ResponseDto<CustomerAppPropertyDto> updateCustomerAppProperty(@PathVariable(value = "app-property-uuid") String appPropertyUUID, @RequestBody CustomerAppPropertyDto customerAppPropertyDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppPropertyService.updateCustomerAppProperty(appPropertyUUID, customerAppPropertyDto));
    }

    @DeleteMapping("/app/properties/{app-property-uuid}")
    public ResponseDto<Boolean> deleteCustomerAppProperty(@PathVariable(value = "app-property-uuid") String appPropertyUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAppPropertyService.deleteCustomerAppProperty(appPropertyUUID));
    }
}
