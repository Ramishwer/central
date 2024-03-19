package com.goev.central.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.goev.central.dto.common.DocumentDto;
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
public class UserDetailsDto {
    private UserDto details;
    private String uuid;
    private List<DocumentDto> documents;
}
