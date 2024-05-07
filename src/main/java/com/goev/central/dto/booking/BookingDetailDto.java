package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.payment.PaymentDto;
import com.goev.central.dto.pricing.PricingModelDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDetailDto {
    private String uuid;
    private BookingViewDto booking;
    private CustomerViewDto customer;
    private PartnerViewDto partner;
    private VehicleViewDto vehicle;
    private PaymentDto payment;
    private PricingModelDto pricingModel;
    private BookingTypeDto bookingType;
    private BookingPricingDetailDto bookingPricingDetail;
    private VehicleCategoryDto requestedVehicleCategory;
    private String startGeohash;
    private String endGeohash;
    private String startRegions;
    private String endRegions;
    private Integer plannedAmount;
    private Integer actualAmount;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime plannedAssignmentTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime plannedEnrouteTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime plannedArrivalTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime plannedStartTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime plannedEndTime;
    private Integer plannedAssignmentToEnrouteDistance;
    private Integer plannedEnrouteToArrivalDistance;
    private Integer plannedArrivalToStartDistance;
    private Integer plannedStartToEndDistance;
    private LocationDto plannedAssignmentLocationDetails;
    private LocationDto plannedEnrouteLocationDetails;
    private LocationDto plannedArrivalLocationDetails;
    private LocationDto plannedStartLocationDetails;
    private LocationDto plannedEndLocationDetails;
    private Integer plannedAssignmentSoc;
    private Integer plannedEnrouteSoc;
    private Integer plannedArrivalSoc;
    private Integer plannedStartSoc;
    private Integer plannedEndSoc;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime actualAssignmentTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime actualEnrouteTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime actualArrivalTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime actualStartTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime actualEndTime;
    private Integer actualAssignmentToEnrouteDistance;
    private Integer actualEnrouteToArrivalDistance;
    private Integer actualArrivalToStartDistance;
    private Integer actualStartToEndDistance;
    private LocationDto actualAssignmentLocationDetails;
    private LocationDto actualEnrouteLocationDetails;
    private LocationDto actualArrivalLocationDetails;
    private LocationDto actualStartLocationDetails;
    private LocationDto actualEndLocationDetails;
    private Integer actualAssignmentSoc;
    private Integer actualEnrouteSoc;
    private Integer actualArrivalSoc;
    private Integer actualStartSoc;
    private Integer actualEndSoc;
    private BookingFeedbackDto bookingFeedback;
    private BookingInvoicingDetailDto bookingInvoicingDetail;
    private String platform;
    private String appVersion;
    private String deviceUUID;
    private String sessionUUID;
}
