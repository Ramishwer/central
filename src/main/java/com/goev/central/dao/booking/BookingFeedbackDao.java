package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingFeedbackDao extends BaseDao {
    private Integer bookingId;
    private String ratingByPartner;
    private String feedbackByPartner;
    private String ratingByCustomer;
    private String feedbackByCustomer;
    private String vehicleRating;
    private String vehicleFeedback;
}
