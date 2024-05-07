package com.goev.central.controller.partner.ticket;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.ticket.PartnerTicketDto;
import com.goev.central.service.partner.ticket.PartnerTicketService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerTicketController {

    private final PartnerTicketService partnerTicketService;

    @GetMapping("/partners/{partner-uuid}/tickets")
    public ResponseDto<PaginatedResponseDto<PartnerTicketDto>> getPartnerTickets(
            @PathVariable(value = "partner-uuid") String partnerUUID,
            @RequestParam("count") Integer count,
            @RequestParam("start") Integer start,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to,
            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerTicketService.getPartnerTickets(partnerUUID));
    }


    @GetMapping("/partners/{partner-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<PartnerTicketDto> getPartnerTicketDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "ticket-uuid") String ticketUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerTicketService.getPartnerTicketDetails(partnerUUID, ticketUUID));
    }


    @PostMapping("/partners/{partner-uuid}/tickets")
    public ResponseDto<PartnerTicketDto> createPartnerTicket(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerTicketDto partnerTicketDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerTicketService.createPartnerTicket(partnerUUID, partnerTicketDto));
    }

    @PutMapping("/partners/{partner-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<PartnerTicketDto> updatePartnerTicket(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "ticket-uuid") String ticketUUID, @RequestBody PartnerTicketDto partnerTicketDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerTicketService.updatePartnerTicket(partnerUUID, ticketUUID, partnerTicketDto));
    }

    @DeleteMapping("/partners/{partner-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<Boolean> deletePartnerTicket(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "ticket-uuid") String ticketUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerTicketService.deletePartnerTicket(partnerUUID, ticketUUID));
    }
}
