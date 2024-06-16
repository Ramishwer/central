package com.goev.central.dto.partner.ticket;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.partner.PartnerViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerTicketDto {

    private String uuid;
    private PartnerViewDto partner;
    private String ticketType;
    private String ticketId;
    private String status;
    private String description;
    private String message;
    private String ticketDetails;

}
