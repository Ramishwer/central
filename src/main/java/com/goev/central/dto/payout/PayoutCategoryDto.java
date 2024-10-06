package com.goev.central.dto.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.payout.PayoutCategoryDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutCategoryDto {
    private String uuid;
    private String name;
    private String description;
    private String label;
    private String type;
    private Boolean isVisible;


    public static PayoutCategoryDto fromDao(PayoutCategoryDao category) {
        if(category == null)
            return null;
        return PayoutCategoryDto.builder()
                .uuid(category.getUuid())
                .name(category.getName())
                .description(category.getDescription())
                .label(category.getLabel())
                .type(category.getType())
                .isVisible(category.getIsVisible())
                .build();
    }
}
