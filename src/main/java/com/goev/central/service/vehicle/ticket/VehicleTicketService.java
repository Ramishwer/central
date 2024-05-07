package com.goev.central.service.vehicle.ticket;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.ticket.VehicleTicketDto;

public interface VehicleTicketService {
    PaginatedResponseDto<VehicleTicketDto> getVehicleTickets(String vehicleUUID);

    VehicleTicketDto createVehicleTicket(String vehicleUUID, VehicleTicketDto vehicleTicketDto);

    VehicleTicketDto updateVehicleTicket(String vehicleUUID, String vehicleTicketUUID, VehicleTicketDto vehicleTicketDto);

    VehicleTicketDto getVehicleTicketDetails(String vehicleUUID, String vehicleTicketUUID);

    Boolean deleteVehicleTicket(String vehicleUUID, String vehicleTicketUUID);
}
