package com.goev.central.service.partner.payout.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dao.partner.payout.PartnerPayoutMappingDao;
import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.dao.payout.PayoutCategoryDao;
import com.goev.central.dao.payout.PayoutElementDao;
import com.goev.central.dao.payout.PayoutModelConfigurationDao;
import com.goev.central.dao.payout.PayoutModelDao;
import com.goev.central.dto.VariableDto;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutMappingDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.partner.payout.PartnerPayoutTransactionDto;
import com.goev.central.dto.payout.PayoutCategoryDto;
import com.goev.central.dto.payout.PayoutConfigDto;
import com.goev.central.dto.payout.PayoutElementDto;
import com.goev.central.dto.payout.PayoutModelDto;
import com.goev.central.enums.partner.PartnerShiftStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutMappingRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutTransactionRepository;
import com.goev.central.repository.payout.PayoutCategoryRepository;
import com.goev.central.repository.payout.PayoutElementRepository;
import com.goev.central.repository.payout.PayoutModelConfigurationRepository;
import com.goev.central.repository.payout.PayoutModelRepository;
import com.goev.central.service.partner.payout.PartnerPayoutService;
import com.goev.lib.exceptions.ResponseException;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class PartnerPayoutServiceImpl implements PartnerPayoutService {
    private final PartnerPayoutRepository partnerPayoutRepository;
    private final PartnerPayoutTransactionRepository partnerPayoutTransactionRepository;
    private final PartnerPayoutMappingRepository partnerPayoutMappingRepository;
    private final PayoutModelRepository payoutModelRepository;
    private final PayoutModelConfigurationRepository payoutModelConfigurationRepository;
    private final PayoutElementRepository payoutElementRepository;
    private final PayoutCategoryRepository payoutCategoryRepository;

    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerPayoutDto> getPayoutsForPartner(String partnerUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PaginatedResponseDto<PartnerPayoutDto> result = PaginatedResponseDto.<PartnerPayoutDto>builder().elements(new ArrayList<>()).build();
        List<PartnerPayoutDao> partnerPayoutDaos = partnerPayoutRepository.findAllByPartnerId(partnerDao.getId());
        if (CollectionUtils.isEmpty(partnerPayoutDaos))
            return result;

        for (PartnerPayoutDao partnerPayoutDao : partnerPayoutDaos) {
            result.getElements().add(PartnerPayoutDto.builder()
                    .uuid(partnerPayoutDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerPayoutSummaryDto getPayoutSummaryForPayout(String partnerUUID, String partnerPayoutUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PartnerPayoutDao partnerPayoutDao = partnerPayoutRepository.findByUUID(partnerPayoutUUID);
        if (partnerPayoutDao == null)
            throw new ResponseException("No partner payout  found for Id :" + partnerPayoutUUID);

        return ApplicationConstants.GSON.fromJson(partnerPayoutDao.getPayoutSummary(), PartnerPayoutSummaryDto.class);
    }

    @Override
    public PaginatedResponseDto<PartnerPayoutTransactionDto> getAllPartnerTransactionsForPayout(String partnerUUID, String partnerPayoutUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner  found for Id :" + partnerUUID);

        PartnerPayoutDao partnerPayoutDao = partnerPayoutRepository.findByUUID(partnerPayoutUUID);
        if (partnerPayoutDao == null)
            throw new ResponseException("No partner payout  found for Id :" + partnerPayoutUUID);


        PaginatedResponseDto<PartnerPayoutTransactionDto> result = PaginatedResponseDto.<PartnerPayoutTransactionDto>builder().elements(new ArrayList<>()).build();
        List<PartnerPayoutTransactionDao> partnerPayoutTransactionDaos = partnerPayoutTransactionRepository.findAllByPartnerPayoutId(partnerPayoutDao.getId());
        if (CollectionUtils.isEmpty(partnerPayoutTransactionDaos))
            return result;

        for (PartnerPayoutTransactionDao partnerPayoutTransactionDao : partnerPayoutTransactionDaos) {
            result.getElements().add(PartnerPayoutTransactionDto
                    .fromDao(partnerPayoutTransactionDao,
                            PartnerPayoutDto.fromDao(partnerPayoutDao,
                                    PartnerViewDto.fromDao(partnerDao)),
                            PartnerViewDto.fromDao(partnerDao)));
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<PartnerPayoutDto> getPayouts(String status, PageDto page, FilterDto filter) {

        List<PartnerPayoutDao> activePayouts;
        if (PartnerShiftStatus.IN_PROGRESS.name().equals(status) || PartnerShiftStatus.PENDING.name().equals(status))
            activePayouts = partnerPayoutRepository.findAllByStatus(status, page);
        else
            activePayouts = partnerPayoutRepository.findAllByStatus(status, page, filter);

        if (CollectionUtils.isEmpty(activePayouts))
            return PaginatedResponseDto.<PartnerPayoutDto>builder().elements(new ArrayList<>()).build();


        List<PartnerDao> partners = partnerRepository.findAllByIds(activePayouts.stream().map(PartnerPayoutDao::getPartnerId).toList());
        Map<Integer, PartnerDao> partnerDaoMap = partners.stream().collect(Collectors.toMap(PartnerDao::getId, Function.identity()));

        List<PartnerPayoutDto> payoutList = new ArrayList<>();
        activePayouts.forEach(x -> {
            PartnerViewDto partnerViewDto = PartnerViewDto.fromDao(partnerDaoMap.get(x.getPartnerId()));
            payoutList.add(PartnerPayoutDto.fromDao(x, partnerViewDto));
        });
        return PaginatedResponseDto.<PartnerPayoutDto>builder().elements(payoutList).build();

    }

    @Override
    public List<PartnerPayoutMappingDto> getPayoutModelMappings(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerPayoutMappingDao> partnerPayoutMappingDaoList = partnerPayoutMappingRepository.findAllByPartnerId(partner.getId());

        List<PartnerPayoutMappingDto> result = new ArrayList<>();

        for (PartnerPayoutMappingDao partnerPayoutMappingDao : partnerPayoutMappingDaoList) {
            result.add(PartnerPayoutMappingDto.fromDao(partnerPayoutMappingDao, PartnerViewDto.fromDao(partner)));
        }

        return result;
    }

    @Override
    public PartnerPayoutMappingDto createPayoutModelMapping(String partnerUUID, PartnerPayoutMappingDto partnerPayoutMappingDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        if (partnerPayoutMappingDto.getConfig() == null || partnerPayoutMappingDto.getConfig().isEmpty())
            throw new ResponseException("No payout model details present.");

        PartnerPayoutMappingDao partnerPayoutMappingDao = new PartnerPayoutMappingDao();
        partnerPayoutMappingDao.setTriggerType(partnerPayoutMappingDao.getTriggerType());
        partnerPayoutMappingDao.setPartnerId(partner.getId());
        partnerPayoutMappingDao.setConfig(ApplicationConstants.GSON.toJson(partnerPayoutMappingDto.getConfig()));
        partnerPayoutMappingDao.setPayoutConfig(ApplicationConstants.GSON.toJson(getPayoutConfig(partnerPayoutMappingDto.getConfig())));
        partnerPayoutMappingDao = partnerPayoutMappingRepository.save(partnerPayoutMappingDao);
        if (partnerPayoutMappingDao == null)
            throw new ResponseException("Error in saving partner payout mapping");

        return PartnerPayoutMappingDto.fromDao(partnerPayoutMappingDao, PartnerViewDto.fromDao(partner));
    }

    private Map<String, PayoutConfigDto> getPayoutConfig(Map<String, PayoutModelDto> config) {

        Type t = new TypeToken<List<VariableDto>>() {
        }.getType();

        Map<String, PayoutConfigDto> result = new HashMap<>();

        for (Map.Entry<String, PayoutModelDto> entry : config.entrySet()) {
            String day = entry.getKey();
            PayoutModelDto modelDto = entry.getValue();
            PayoutConfigDto payoutConfigDto = PayoutConfigDto.builder().payoutModelConfig(modelDto).build();

            PayoutModelDao payoutModelDao = payoutModelRepository.findByUUID(modelDto.getUuid());

            List<PayoutModelConfigurationDao> payoutModelConfigurationDaoList = payoutModelConfigurationRepository.findByPayoutModelIdAndDay(payoutModelDao.getId(), day);

            List<PayoutElementDao> payoutElementDtoList = payoutElementRepository.findAllByIds(payoutModelConfigurationDaoList.stream().map(PayoutModelConfigurationDao::getPayoutElementId).toList());
            if (!CollectionUtils.isEmpty(payoutElementDtoList)) {
                Map<Integer, List<VariableDto>> elementWiseVariables = payoutModelConfigurationDaoList.stream().collect(Collectors.toMap(PayoutModelConfigurationDao::getPayoutElementId, y -> {
                            if (y.getVariableValues() == null)
                                return new ArrayList<>();
                            return ApplicationConstants.GSON.fromJson(y.getVariableValues(), t);
                        }
                ));
                List<PayoutElementDto> elementList = new ArrayList<>();
                for (PayoutElementDao elementDao : payoutElementDtoList) {
                    PayoutCategoryDao category = payoutCategoryRepository.findById(elementDao.getPayoutCategoryId());
                    PayoutElementDto resultDto = PayoutElementDto.fromDao(elementDao, PayoutCategoryDto.fromDao(category));
                    resultDto.setVariables(elementWiseVariables.get(elementDao.getId()));
                    elementList.add(resultDto);

                }
                payoutConfigDto.setPayoutElementsConfig(elementList);
            }
            result.put(day, payoutConfigDto);

        }
        return result;
    }

    @Override
    public Boolean deletePayoutModelMapping(String partnerUUID, String partnerPayoutModelMappingUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerPayoutMappingDao partnerPayoutMappingDao = partnerPayoutMappingRepository.findByUUID(partnerPayoutModelMappingUUID);
        if (partnerPayoutMappingDao == null)
            throw new ResponseException("No payout mapping found for Id :" + partnerPayoutModelMappingUUID);

        partnerPayoutMappingRepository.delete(partnerPayoutMappingDao.getId());
        return true;
    }


}
