package com.goev.central.dto.vehicle;

import com.goev.lib.dto.LatLongDto;
import com.goev.lib.dto.StatsDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VehicleStatsDto {
    private LatLongDto gps;
    private StatsDto soc;
    private StatsDto dte;
    private StatsDto odometer;
    private Integer kmRange;
}
