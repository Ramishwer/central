


package com.goev.central.dao.customer.app;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerAppSupportedLanguageDao extends BaseDao {
    private String languageCode;
    private String name;
    private String s3Key;
}


