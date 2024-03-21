package com.goev.central.dto.partner;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goev.central.dto.common.AttributeDto;
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
public class PartnerDto {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String uuid;
    private String punchId;
    private String state;
    private List<AttributeDto> attributes;
}
