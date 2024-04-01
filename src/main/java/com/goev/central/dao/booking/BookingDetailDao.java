package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingDetailDao extends BaseDao {
    private Integer bookingId;
    private Integer customerId;
    private Integer partnerId;
    private Integer vehicleId;
    private Integer paymentId;
    private Integer pricingModelId;
    private Integer bookingTypeId;
    private Integer bookingPricingDetailId;
    private Integer requestedVehicleCategoryId;
    private Integer customerPromotionId;
    private String startGeohash;
    private String endGeohash;
    private Integer startRegionId;
    private Integer endRegionId;
    private Integer plannedAmount;
    private Integer actualAmount;
    private DateTime plannedAssignmentTime;
    private DateTime plannedEnrouteTime;
    private DateTime plannedArrivalTime;
    private DateTime plannedStartTime;
    private DateTime plannedEndTime;
    private Integer plannedAssignmentToEnrouteDistance;
    private Integer plannedEnrouteToArrivalDistance;
    private Integer plannedArrivalToStartDistance;
    private Integer plannedStartToEndDistance;
    private String plannedAssignmentLocationDetails;
    private String plannedEnrouteLocationDetails;
    private String plannedArrivalLocationDetails;
    private String plannedStartLocationDetails;
    private String plannedEndLocationDetails;
    private Integer plannedAssignmentSoc;
    private Integer plannedEnrouteSoc;
    private Integer plannedArrivalSoc;
    private Integer plannedStartSoc;
    private Integer plannedEndSoc;
    private DateTime actualAssignmentTime;
    private DateTime actualEnrouteTime;
    private DateTime actualArrivalTime;
    private DateTime actualStartTime;
    private DateTime actualEndTime;
    private Integer actualAssignmentToEnrouteDistance;
    private Integer actualEnrouteToArrivalDistance;
    private Integer actualArrivalToStartDistance;
    private Integer actualStartToEndDistance;
    private String actualAssignmentLocationDetails;
    private String actualEnrouteLocationDetails;
    private String actualArrivalLocationDetails;
    private String actualStartLocationDetails;
    private String actualEndLocationDetails;
    private Integer actualAssignmentSoc;
    private Integer actualEnrouteSoc;
    private Integer actualArrivalSoc;
    private Integer actualStartSoc;
    private Integer actualEndSoc;
    private Integer bookingFeedbackId;
    private Integer bookingInvoicingDetailId;
}
