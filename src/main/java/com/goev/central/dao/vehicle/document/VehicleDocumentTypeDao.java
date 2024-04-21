package com.goev.central.dao.vehicle.document;

import com.goev.central.dto.user.detail.UserDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDocumentTypeDao extends BaseDao {
    private String name;
    private String s3Key;
    private String label;
    private String groupKey;
    private String groupDescription;
}
