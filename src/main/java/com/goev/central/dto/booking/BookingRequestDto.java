package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
import com.goev.central.enums.booking.BookingRequestAction;
import com.goev.lib.dto.ContactDetailsDto;
import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingRequestDto {
    private String uuid;
    private String status;
    private BookingRequestAction action;
    private BookingTypeDto bookingType;
    private LatLongDto startLocationDetails;
    private LatLongDto endLocationDetails;
    private SchedulingDetailDto scheduleDetails;
    private CustomerViewDto customerDetails;
    private BookingPaymentDto paymentDetails;
    private ContactDetailsDto startContact;
    private ContactDetailsDto endContact;
    private VehicleCategoryDto requestedVehicleCategory;
    private Long duration;
    private Long distance;
    private BookingPricingDetailDto pricingDetails;
    private BookingViewDto booking;
    private BusinessClientDto businessClient;
    private BusinessSegmentDto businessSegment;
}
