package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingFeedbackDto {
    private String uuid;
    private BookingViewDto booking;
    private String ratingByPartner;
    private String feedbackByPartner;
    private String ratingByCustomer;
    private String feedbackByCustomer;
    private String vehicleRating;
    private String vehicleFeedback;
}
