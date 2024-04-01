package com.goev.central.dto.partner;

import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerViewDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String punchId;
    private DateTime onboardingDate;
    private String uuid;
    private String state;
    private String profileUrl;
}
