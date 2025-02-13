package com.goev.central.dto.user.authorization;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPermissionDto {
    private String uuid;
    private String name;
    private String description;
    private String type;
    private String permissionProperties;
}
