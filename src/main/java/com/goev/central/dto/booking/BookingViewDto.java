package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingViewDto {
    private String uuid;
    private BookingTypeDto bookingTypeDetails;
    private String status;
    private LocationDto startLocationDetails;
    private LocationDto endLocationDetails;
    private PartnerViewDto partnerDetails;
    private VehicleViewDto vehicleDetails;
    private CustomerViewDto customerDetails;
}
