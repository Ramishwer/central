package com.goev.central.controller.customer.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerDeviceDto;
import com.goev.central.service.customer.detail.CustomerDeviceService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerDeviceController {

    private final CustomerDeviceService customerDeviceService;

    @GetMapping("/customers/{customer-uuid}/devices")
    public ResponseDto<PaginatedResponseDto<CustomerDeviceDto>> getCustomerDevices(@PathVariable(value = "customer-uuid") String customerUUID,
                                                                                   @RequestParam(value = "count", required = false) Integer count,
                                                                                   @RequestParam(value = "start", required = false) Integer start,
                                                                                   @RequestParam(value = "from", required = false) Long from,
                                                                                   @RequestParam(value = "to", required = false) Long to,
                                                                                   @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerDeviceService.getCustomerDevices(customerUUID));
    }


    @GetMapping("/customers/{customer-uuid}/devices/{device-uuid}")
    public ResponseDto<CustomerDeviceDto> getCustomerDeviceDetails(@PathVariable(value = "customer-uuid") String customerUUID,
                                                                   @PathVariable(value = "device-uuid") String deviceUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerDeviceService.getCustomerDeviceDetails(customerUUID, deviceUUID));
    }

    @DeleteMapping("/customers/{customer-uuid}/devices/{device-uuid}")
    public ResponseDto<Boolean> deleteCustomerDevice(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "device-uuid") String deviceUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerDeviceService.deleteCustomerDevice(customerUUID, deviceUUID));
    }
}
