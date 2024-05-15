package com.goev.central.controller.customer.ticket;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.ticket.CustomerTicketDto;
import com.goev.central.service.customer.ticket.CustomerTicketService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerTicketController {

    private final CustomerTicketService customerTicketService;

    @GetMapping("/customers/{customer-uuid}/tickets")
    public ResponseDto<PaginatedResponseDto<CustomerTicketDto>> getCustomerTickets(
            @PathVariable(value = "customer-uuid") String customerUUID,
            @RequestParam(value = "count",required = false) Integer count,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "from", required = false) Long from,
            @RequestParam(value = "to", required = false) Long to,
            @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerTicketService.getCustomerTickets(customerUUID));
    }


    @GetMapping("/customers/{customer-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<CustomerTicketDto> getCustomerTicketDetails(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "ticket-uuid") String ticketUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerTicketService.getCustomerTicketDetails(customerUUID, ticketUUID));
    }


    @PostMapping("/customers/{customer-uuid}/tickets")
    public ResponseDto<CustomerTicketDto> createCustomerTicket(@PathVariable(value = "customer-uuid") String customerUUID, @RequestBody CustomerTicketDto customerTicketDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerTicketService.createCustomerTicket(customerUUID, customerTicketDto));
    }

    @PutMapping("/customers/{customer-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<CustomerTicketDto> updateCustomerTicket(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "ticket-uuid") String ticketUUID, @RequestBody CustomerTicketDto customerTicketDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerTicketService.updateCustomerTicket(customerUUID, ticketUUID, customerTicketDto));
    }

    @DeleteMapping("/customers/{customer-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<Boolean> deleteCustomerTicket(@PathVariable(value = "customer-uuid") String customerUUID, @PathVariable(value = "ticket-uuid") String ticketUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerTicketService.deleteCustomerTicket(customerUUID, ticketUUID));
    }
}
