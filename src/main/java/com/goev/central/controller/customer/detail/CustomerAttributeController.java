package com.goev.central.controller.customer.attribute;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerAttributeDto;
import com.goev.central.service.customer.detail.CustomerAttributeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerAttributeController {

    private final CustomerAttributeService customerAttributeService;

    @GetMapping("/customers/{customer-uuid}/attributes")
    public ResponseDto<PaginatedResponseDto<CustomerAttributeDto>> getCustomerAttributes(
            @PathVariable(value = "customer-uuid") String customerUUID,
            @RequestParam(value = "count",required = false) Integer count,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "from", required = false) Long from,
            @RequestParam(value = "to", required = false) Long to,
            @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAttributeService.getCustomerAttributes(customerUUID));
    }


    @GetMapping("/customers/{customer-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<CustomerAttributeDto> getCustomerAttributeDetails(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAttributeService.getCustomerAttributeDetails(customerUUID, attributeUUID));
    }


    @PostMapping("/customers/{customer-uuid}/attributes")
    public ResponseDto<CustomerAttributeDto> createCustomerAttribute(@PathVariable(value = "customer-uuid") String customerUUID, @RequestBody CustomerAttributeDto customerAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAttributeService.createCustomerAttribute(customerUUID, customerAttributeDto));
    }

    @PutMapping("/customers/{customer-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<CustomerAttributeDto> updateCustomerAttribute(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "attribute-uuid") String attributeUUID, @RequestBody CustomerAttributeDto customerAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAttributeService.updateCustomerAttribute(customerUUID, attributeUUID, customerAttributeDto));
    }

    @DeleteMapping("/customers/{customer-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<Boolean> deleteCustomerAttribute(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerAttributeService.deleteCustomerAttribute(customerUUID, attributeUUID));
    }
}
