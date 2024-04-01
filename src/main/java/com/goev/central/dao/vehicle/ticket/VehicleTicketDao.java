package com.goev.central.dao.vehicle.ticket;

import com.goev.central.dto.user.detail.UserDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleTicketDao extends BaseDao {
    private Integer vehicleId;
    private String ticketType;
    private String ticketId;
    private String status;
    private String description;
    private String message;
    private String ticketDetails;
}
