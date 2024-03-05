package com.goev.central.dto.session;

import com.goev.lib.dto.AuthorisationDetailsDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SessionDetailsDto {
    private SessionDto details;
    private String uuid;
}
