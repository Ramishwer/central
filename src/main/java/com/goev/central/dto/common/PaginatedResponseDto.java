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
    private Integer totalPages;
    private Integer currentPage;
    private List<T> elements;
}
