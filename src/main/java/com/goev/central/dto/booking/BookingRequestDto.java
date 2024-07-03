package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.customer.detail.CustomerDetailDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
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
    private BookingTypeDto bookingType;
    private LatLongDto startLocationDetails;
    private LatLongDto endLocationDetails;
    private VehicleCategoryDto requestedVehicleCategory;
    private BusinessSegmentDto businessSegment;
    private BusinessClientDto businessClient;
    private BookingSlabGroupDto slabDetails;
    private SchedulingDetailDto scheduleDetails;
    private CustomerDetailDto customerDetails;
}
