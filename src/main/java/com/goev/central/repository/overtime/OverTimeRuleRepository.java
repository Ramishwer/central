package com.goev.central.repository.overtime;
import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.overtime.OverTimeRuleDao;

import java.util.Optional;


public interface OverTimeRuleRepository {

    OverTimeRuleDao save(OverTimeRuleDao overTimeRuleDao);

    Optional<OverTimeRuleDao> findLastRuleIdInOverTimeRule ();
}
