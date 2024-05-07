package com.goev.central.dto.customer.ticket;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerTicketDto {

    private String uuid;
    private CustomerViewDto customer;
    private String ticketType;
    private String ticketId;
    private String status;
    private String description;
    private String message;
    private String ticketDetails;

}
