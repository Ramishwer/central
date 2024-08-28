package com.goev.central.dto.partner.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.partner.document.PartnerDocumentDao;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDocumentDto {
    private String uuid;
    private String url;
    private PartnerDocumentTypeDto type;
    private String fileName;
    private String description;
    private String status;
    private Map<String, Object> data;

    public static PartnerDocumentDto fromDao(PartnerDocumentDao partnerDocumentDao, PartnerDocumentTypeDto partnerDocumentTypeDto){
        if(partnerDocumentDao==null)
            return null;
        return PartnerDocumentDto.builder()
                .uuid(partnerDocumentDao.getUuid())
                .type(partnerDocumentTypeDto)
                .fileName(partnerDocumentDao.getFileName())
                .description(partnerDocumentDao.getDescription())
                .status(partnerDocumentDao.getStatus())
                .url(partnerDocumentDao.getUrl())
                .build();
    }
}
