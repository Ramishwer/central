package com.goev.central.dto.business;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class BusinessClientDto {
    private String uuid;
    private String name;
    private String description;
    private ContactDetailsDto contactPersonDetails;
    private LatLongDto businessAddress;
    private String gstNumber;
    private String appType;
    private BusinessSegmentDto businessSegment;
}
