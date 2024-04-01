


package com.goev.central.dao.customer.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerDetailDao extends BaseDao {
    private String phoneNumber;
    private String profileUrl;
    private String firstName;
    private String lastName;
    private String email;
    private String preferredLanguage;
}


