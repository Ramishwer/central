package com.goev.central.dto.customer;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerViewDto {

    private String uuid;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String profileUrl;
    private String status;
}
