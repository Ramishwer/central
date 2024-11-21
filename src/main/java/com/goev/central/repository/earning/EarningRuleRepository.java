package com.goev.central.repository.earning;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dto.earning.EarningRuleDto;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Optional;


public interface EarningRuleRepository {
    List<EarningRuleDao> findAllBySatusAndDateRange(String status, DateTime from, DateTime to);

    EarningRuleDao save(EarningRuleDao earningRuleDao);

    EarningRuleDao findByUUID(String earningRuleUUID);

    EarningRuleDao update(EarningRuleDao earningRuleDao);

    Optional<EarningRuleDao> findLastRuleIdInEarningRule ();

    void delete(Integer id);

    EarningRuleDao findAllByClientName(String clientName);
}
