package com.goev.central.repository.earning;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import org.joda.time.DateTime;


public interface PartnerFixedEarningRepository {

    void saveEarningTransaction(PartnerDao partnerDao, EarningRuleDao earningRuleDao, Float fixedEarning, DateTime start);

    Float getTotalFixedEarning(PartnerDao partnerDao, DateTime startMonthDate, DateTime endMonthDate);

}
