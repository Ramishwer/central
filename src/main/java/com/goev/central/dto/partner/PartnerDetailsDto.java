package com.goev.central.dto.partner;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goev.central.dto.client.BusinessClientDto;
import com.goev.central.dto.common.DocumentDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.account.PartnerAccountDto;
import com.goev.central.dto.partner.reference.PartnerReferenceDto;
import com.goev.central.dto.partner.shift.PartnerShiftDto;
import com.goev.lib.utilities.DateTimeSerializer;
import lombok.*;
import org.joda.time.DateTime;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerDetailsDto {
    private PartnerDto details;
    private String uuid;
    private List<DocumentDto> documents;
    private List<PartnerReferenceDto> references;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime onboardingDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime deboardingDate;
    private String fathersName;
    private String aadhaarCardNumber;
    private String dlNumber;
    private PartnerAccountDto partnerAccountDetails;
    private String address;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime dateOfJoining;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime dlExpiry;
    private LocationDto homeLocationId;
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime interviewDate;
    private String sourceOfLeadType;
    private String sourceOfLead;
    private PartnerShiftDto shiftDetails;
    private String businessSegment;
    private BusinessClientDto businessClient;
    private String driverTestStatus;
    private String selectionStatus;
    private String  remark;
    private Boolean isVerified;
}
