package com.goev.central.controller.customer.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.notification.CustomerNotificationDto;
import com.goev.central.service.customer.notification.CustomerNotificationService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerNotificationController {

    private final CustomerNotificationService customerNotificationService;

    @GetMapping("/customers/{customer-uuid}/notifications")
    public ResponseDto<PaginatedResponseDto<CustomerNotificationDto>> getCustomerNotifications(@PathVariable(value = "customer-uuid")String customerUUID,
            @RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerNotificationService.getCustomerNotifications(customerUUID));
    }

    

    @GetMapping("/customers/{customer-uuid}/notifications/{notification-uuid}")
    public ResponseDto<CustomerNotificationDto> getCustomerNotificationDetails(@PathVariable(value = "customer-uuid")String customerUUID,
                                                                   @PathVariable(value = "notification-uuid")String notificationUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerNotificationService.getCustomerNotificationDetails(customerUUID,notificationUUID));
    }

}
