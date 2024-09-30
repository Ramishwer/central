package com.goev.central.dto.vehicle.ticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.vehicle.VehicleViewDto;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleTicketDto {
    private VehicleViewDto vehicleDetails;
    private String ticketType;
    private String ticketId;
    private String status;
    private String description;
    private String message;
    private String ticketDetails;
    private String uuid;
}
