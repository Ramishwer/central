package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDto {
    private String uuid;
    private BookingTypeDto bookingTypeDetails;
    private String status;
    private String subStatus;
    private LatLongDto startLocationDetails;
    private LatLongDto endLocationDetails;
    private PartnerViewDto partnerDetails;
    private VehicleViewDto vehicleDetails;
    private CustomerViewDto customerDetails;

    public static BookingDto fromDao(BookingDao bookingDao) {
        return BookingDto.builder().uuid(bookingDao.getUuid()).build();
    }
}
