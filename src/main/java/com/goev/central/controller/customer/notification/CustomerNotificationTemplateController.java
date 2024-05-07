package com.goev.central.controller.customer.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.notification.CustomerNotificationTemplateDto;
import com.goev.central.service.customer.notification.CustomerNotificationTemplateService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerNotificationTemplateController {

    private final CustomerNotificationTemplateService customerNotificationTemplateService;

    @GetMapping("/notification-templates")
    public ResponseDto<PaginatedResponseDto<CustomerNotificationTemplateDto>> getCustomerNotificationTemplates(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerNotificationTemplateService.getCustomerNotificationTemplates());
    }

    

    @GetMapping("/notification-templates/{template-uuid}")
    public ResponseDto<CustomerNotificationTemplateDto> getCustomerNotificationTemplateDetails(@PathVariable(value = "template-uuid")String notificationTemplateUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerNotificationTemplateService.getCustomerNotificationTemplateDetails(notificationTemplateUUID));
    }


    @PostMapping("/notification-templates")
    public ResponseDto<CustomerNotificationTemplateDto> createCustomerNotificationTemplate(@RequestBody CustomerNotificationTemplateDto customerNotificationTemplateDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerNotificationTemplateService.createCustomerNotificationTemplate(customerNotificationTemplateDto));
    }

    @PutMapping("/notification-templates/{template-uuid}")
    public ResponseDto<CustomerNotificationTemplateDto> updateCustomerNotificationTemplate(@PathVariable(value = "template-uuid")String notificationTemplateUUID, @RequestBody CustomerNotificationTemplateDto customerNotificationTemplateDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerNotificationTemplateService.updateCustomerNotificationTemplate(notificationTemplateUUID,customerNotificationTemplateDto));
    }
    @DeleteMapping("/notification-templates/{template-uuid}")
    public ResponseDto<Boolean> deleteCustomerNotificationTemplate(@PathVariable(value = "template-uuid")String notificationTemplateUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, customerNotificationTemplateService.deleteCustomerNotificationTemplate(notificationTemplateUUID));
    }
}
