


package com.goev.central.dao.customer.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerDeviceDao extends BaseDao {
    private String fcmToken;
    private String imei1;
    private String imei2;
    private String screenHeight;
    private String screenWidth;
    private String screenDpi;
    private String appVersion;
    private String platform;
    private String brand;
    private String model;
    private String carrier;
    private String osVersion;
    private String gms;
    private String manufacturer;
    private String city;
    private String distinctId;
    private Integer customerSessionId;
    private Integer customerId;
}


