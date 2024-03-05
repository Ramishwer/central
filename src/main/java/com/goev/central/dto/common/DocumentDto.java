package com.goev.central.dto.common;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DocumentDto {
    private String uuid;
    private String url;
    private String type;
    private String name;
    private String desciption;
    private List<AttributeDto> attributes;
    private String state;
}
