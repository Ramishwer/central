package com.goev.central.dto.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.payout.PayoutElementDao;
import com.goev.central.dto.VariableDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayoutElementDto {
    private String name;
    private String type;
    private String description;
    private String rules;
    private String title;
    private String subtitle;
    private String message;
    private String triggerEvent;
    private PayoutCategoryDto categoryDetails;
    private Integer sortingOrder;
    private Integer value;
    private Boolean isVisible;
    private String uuid;
    private List<VariableDto> variables;


    public static PayoutElementDto fromDao(PayoutElementDao elementDao, PayoutCategoryDto categoryDetails){
        if(elementDao == null)
            return null;
        return PayoutElementDto.builder()
                .categoryDetails(categoryDetails)
                .isVisible(elementDao.getIsVisible())
                .uuid(elementDao.getUuid())
                .name(elementDao.getName())
                .description(elementDao.getDescription())
                .message(elementDao.getMessage())
                .sortingOrder(elementDao.getSortingOrder())
                .rules(elementDao.getRules())
                .subtitle(elementDao.getSubtitle())
                .triggerEvent(elementDao.getTriggerEvent())
                .type(elementDao.getType())
                .title(elementDao.getTitle())
                .build();
    }
}
