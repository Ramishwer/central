package com.goev.central.dto.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.location.LocationDao;
import com.goev.lib.dto.LatLongDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {
    private String uuid;
    private String name;
    private String type;
    private LatLongDto coordinates;
    private String address;
    private OwnerDetailDto ownerDetail;

    public  static LocationDto fromDao(LocationDao locationDao){
        if(locationDao == null)
            return null;
        return LocationDto.builder()
                .name(locationDao.getName())
                .uuid(locationDao.getUuid())
                .build();

    }

}
