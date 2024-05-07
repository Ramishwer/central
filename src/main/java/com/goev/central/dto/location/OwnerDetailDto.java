package com.goev.central.dto.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerDetailDto {

    private String name;
    private String phoneNumber;
    private String email;
}
