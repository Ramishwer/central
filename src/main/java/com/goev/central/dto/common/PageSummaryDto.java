package com.goev.central.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageSummaryDto {
    private Integer totalPages;
    private Integer currentPage;
    private Integer totalElements;
}
