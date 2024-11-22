package com.goev.central.repository.earning;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import org.joda.time.DateTime;


public interface PartnerTotalEarningRepository {

    PartnerEarningDao getPartnerEarningDetails(PartnerDao partner , DateTime monthStartDate , DateTime monthEndDate);

    void savePartnerTotalEarning(PartnerDao partner , EarningRuleDao earningRuleDao, DateTime monthStartDate , DateTime monthEndDate ,Float totalFixedEaring , Integer noOfPresentdays , Integer noOfAbsentdays);

    void updatePartnerTotalEarning (PartnerEarningDao partnerEarningDao);
}
