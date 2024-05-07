package com.goev.central.dao.customer.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerDao extends BaseDao {
    private String phoneNumber;
    private String profileUrl;
    private String authUuid;
    private String status;
    private String bookingDetails;
    private Integer customerDetailsId;
    private String viewInfo;
}
