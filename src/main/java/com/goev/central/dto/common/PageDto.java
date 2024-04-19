package com.goev.central.dto.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PageDto {
    private Integer totalPages;
    private Integer currentPage;
    private Integer totalElements;
    private String lastElement;
}
