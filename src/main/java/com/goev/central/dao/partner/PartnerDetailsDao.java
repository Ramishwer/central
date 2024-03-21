package com.goev.central.dao.partner;

import com.goev.central.dto.partner.PartnerDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerDetailsDao extends BaseDao<Integer, PartnerDto> {
    private Integer partnerId ;
    private String fathersName;
    private String aadhaarCardNumber;
    private String dlNumber;
    private Integer partnerAccountDetailsId;
    private String address;
    private DateTime dateOfJoining;
    private DateTime dlExpiry;
    private Integer homeLocationId;
    private DateTime interviewDate;
    private String sourceOfLeadType;
    private String sourceOfLead;
    private Integer shiftId;
    private Integer businessSegmentId;
    private Integer businessClientId;
    private String driverTestStatus;
    private String selectionStatus;
    private String  remark;
    private Integer partnerReferenceId;
    private Boolean isVerified;
    @Override
    public BaseDao<Integer, PartnerDto> fromDto(PartnerDto partnerDto) {
        return null;
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public PartnerDto toDto() {
        return null;
    }
}
