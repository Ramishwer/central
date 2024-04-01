package com.goev.central.dao.system.language;


import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SystemSupportedLanguageDao extends BaseDao {
    private String languageCode;
    private String name;
    private String s3Key;
}



