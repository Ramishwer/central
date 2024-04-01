package com.goev.central.dao.partner.ticket;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerTicketDao extends BaseDao {
    private Integer partnerId;
    private String ticketType;
    private String ticketId;
    private String status;
    private String description;
    private String message;
    private String ticketDetails;
}


