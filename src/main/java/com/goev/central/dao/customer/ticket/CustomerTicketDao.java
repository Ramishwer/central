


package com.goev.central.dao.customer.ticket;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerTicketDao extends BaseDao {
    private Integer customerId;
    private String ticketType;
    private String ticketId;
    private String status;
    private String description;
    private String message;
    private String ticketDetails;
}


