package com.goev.central.dto.customer.detail;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.booking.BookingViewDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

    private String uuid;
    private String phoneNumber;
    private String profileUrl;
    private String authUuid;
    private String status;
    private List<BookingViewDto> bookingDetails;
}
