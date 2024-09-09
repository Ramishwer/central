package com.goev.central.dto.partner.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.goev.central.dao.partner.document.PartnerDocumentDao;
import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.common.FileDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;
import com.goev.central.dto.partner.document.PartnerDocumentTypeDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.enums.DocumentStatus;
import lombok.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDetailDto {
    private PartnerViewDto partner;
    private String uuid;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime onboardingDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime deboardingDate;
    private String dlNumber;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime joiningDate;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime dlExpiryDate;
    private LocationDto homeLocation;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime interviewDate;
    private String sourceOfLeadType;
    private String sourceOfLead;
    private PartnerShiftDto shiftDetails;
    private BusinessSegmentDto businessSegment;
    private BusinessClientDto businessClient;
    private String drivingTestStatus;
    private String selectionStatus;
    private String remark;
    private Boolean isVerified;
    private String email;
    private String firstName;
    private String lastName;
    private String state;
    private String fathersName;
    private String aadhaarCardNumber;
    private String localAddress;
    private String permanentAddress;
    private String profileUrl;
    private FileDto profile;
    private PartnerCategoryDto partnerCategory;
    private Boolean inTraining;
    private String trainingStatus;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime trainingDate;


    public static List<PartnerDocumentDto> getPartnerDocumentDtoList(Map<Integer, PartnerDocumentTypeDao> documentTypeIdToDocumentTypeMap, Map<Integer, PartnerDocumentDao> existingDocumentMap) {
        Map<Integer, PartnerDocumentDto> finalDocumentResultMap = new HashMap<>();
        for (Map.Entry<Integer, PartnerDocumentTypeDao> entry : documentTypeIdToDocumentTypeMap.entrySet()) {
            PartnerDocumentTypeDao type = entry.getValue();
            PartnerDocumentDto partnerDocumentDto = PartnerDocumentDto.builder()
                    .type(PartnerDocumentTypeDto.builder()
                            .name(type.getName())
                            .label(type.getLabel())
                            .groupKey(type.getGroupKey())
                            .groupDescription(type.getGroupDescription())
                            .uuid(type.getUuid())
                            .build())
                    .status(DocumentStatus.PENDING.name())
                    .build();
            if (existingDocumentMap.containsKey(entry.getKey())) {
                PartnerDocumentDao existingDoc = existingDocumentMap.get(entry.getKey());
                partnerDocumentDto.setUrl(existingDoc.getUrl());
                partnerDocumentDto.setDescription(existingDoc.getDescription());
                partnerDocumentDto.setStatus(existingDoc.getStatus());
                partnerDocumentDto.setUuid(existingDoc.getUuid());
            }
            finalDocumentResultMap.put(entry.getKey(), partnerDocumentDto);
        }
        return new ArrayList<>(finalDocumentResultMap.values());
    }
}
