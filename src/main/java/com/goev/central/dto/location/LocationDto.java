package com.goev.central.dto.location;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LocationDto {
    private String uuid;
    private String name;
    private String type;
    private Float latitude;
    private Float longitude;
}
