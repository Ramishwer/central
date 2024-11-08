package com.goev.central.repository.earning;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dto.earning.EarningRuleDto;

import java.util.List;
import java.util.Optional;


public interface EarningRuleRepository {
    List<EarningRuleDao> findAll();

    EarningRuleDao save(EarningRuleDao earningRuleDao);

    EarningRuleDao findByUUID(String earningRuleUUID);

    EarningRuleDao update(EarningRuleDao earningRuleDao);

    Optional<EarningRuleDao> findLastRuleIdInEarningRule ();
}
