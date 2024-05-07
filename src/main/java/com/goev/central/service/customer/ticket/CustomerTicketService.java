package com.goev.central.service.customer.ticket;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.ticket.CustomerTicketDto;

public interface CustomerTicketService {
    PaginatedResponseDto<CustomerTicketDto> getCustomerTickets(String customerUUID);

    CustomerTicketDto createCustomerTicket(String customerUUID, CustomerTicketDto customerTicketDto);

    CustomerTicketDto updateCustomerTicket(String customerUUID, String customerTicketUUID, CustomerTicketDto customerTicketDto);

    CustomerTicketDto getCustomerTicketDetails(String customerUUID, String customerTicketUUID);

    Boolean deleteCustomerTicket(String customerUUID, String customerTicketUUID);
}
