package com.goev.central.controller.vehicle.ticket;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.ticket.VehicleTicketDto;
import com.goev.central.service.vehicle.ticket.VehicleTicketService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/vehicle-management")
@AllArgsConstructor
public class VehicleTicketController {

    private final VehicleTicketService vehicleTicketService;

    @GetMapping("/vehicles/{vehicle-uuid}/tickets")
    public ResponseDto<PaginatedResponseDto<VehicleTicketDto>> getVehicleTickets(
            @PathVariable(value = "vehicle-uuid") String vehicleUUID,
            @RequestParam("count") Integer count,
            @RequestParam("start") Integer start,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to,
            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleTicketService.getVehicleTickets(vehicleUUID));
    }


    @GetMapping("/vehicles/{vehicle-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<VehicleTicketDto> getVehicleTicketDetails(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @PathVariable(value = "ticket-uuid") String ticketUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleTicketService.getVehicleTicketDetails(vehicleUUID, ticketUUID));
    }


    @PostMapping("/vehicles/{vehicle-uuid}/tickets")
    public ResponseDto<VehicleTicketDto> createVehicleTicket(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @RequestBody VehicleTicketDto vehicleTicketDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleTicketService.createVehicleTicket(vehicleUUID, vehicleTicketDto));
    }

    @PutMapping("/vehicles/{vehicle-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<VehicleTicketDto> updateVehicleTicket(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @PathVariable(value = "ticket-uuid") String ticketUUID, @RequestBody VehicleTicketDto vehicleTicketDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleTicketService.updateVehicleTicket(vehicleUUID, ticketUUID, vehicleTicketDto));
    }

    @DeleteMapping("/vehicles/{vehicle-uuid}/tickets/{ticket-uuid}")
    public ResponseDto<Boolean> deleteVehicleTicket(@PathVariable(value = "vehicle-uuid") String vehicleUUID, @PathVariable(value = "ticket-uuid") String ticketUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, vehicleTicketService.deleteVehicleTicket(vehicleUUID, ticketUUID));
    }
}
