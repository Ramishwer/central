package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.booking.BookingScheduleDao;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDetailDto;
import com.goev.central.dto.payment.PaymentDetailDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
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
public class BookingScheduleDto {
    private String uuid;
    private String displayCode;
    private String status;
    private String subStatus;
    private BookingTypeDto bookingTypeDetails;
    private LatLongDto startLocationDetails;
    private LatLongDto endLocationDetails;
    private VehicleCategoryDto requestedVehicleCategory;
    private BusinessSegmentDto businessSegment;
    private BusinessClientDto businessClient;
    private BookingSlabGroupDto slabDetails;
    private SchedulingDetailDto scheduleDetails;
    private CustomerViewDto customerDetails;
    private BookingPaymentDto paymentDetails;
    private BookingPricingDetailDto pricingDetails;
    private ContactDetailsDto startContact;
    private ContactDetailsDto endContact;
    private Long distance;
    private Long duration;

}
