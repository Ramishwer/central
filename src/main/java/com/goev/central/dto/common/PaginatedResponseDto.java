package com.goev.central.dto.common;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaginatedResponseDto <T>{
    private PageDto pagination;
    private List<T> elements;
}
