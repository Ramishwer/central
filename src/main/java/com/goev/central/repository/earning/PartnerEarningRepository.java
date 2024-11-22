package com.goev.central.repository.earning;

import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import org.joda.time.DateTime;

public interface PartnerEarningRepository {

    PartnerEarningDao getPartnerEarningDetails(Integer partnerId , DateTime monthStartDate , DateTime monthEndDate);
}
