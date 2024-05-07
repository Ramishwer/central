package com.goev.central.service.partner.ticket;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.ticket.PartnerTicketDto;

public interface PartnerTicketService {
    PaginatedResponseDto<PartnerTicketDto> getPartnerTickets(String partnerUUID);

    PartnerTicketDto createPartnerTicket(String partnerUUID, PartnerTicketDto partnerTicketDto);

    PartnerTicketDto updatePartnerTicket(String partnerUUID, String partnerTicketUUID, PartnerTicketDto partnerTicketDto);

    PartnerTicketDto getPartnerTicketDetails(String partnerUUID, String partnerTicketUUID);

    Boolean deletePartnerTicket(String partnerUUID, String partnerTicketUUID);
}
