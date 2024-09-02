package com.goev.central.dto.partner;

import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerStatsDto {
    private LatLongDto gps;
}
