package com.goev.central.service.partner.duty.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.duty.PartnerShiftMappingDao;
import com.goev.central.dao.payout.PayoutModelDao;
import com.goev.central.dao.shift.ShiftConfigurationDao;
import com.goev.central.dao.shift.ShiftDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.dto.partner.duty.PartnerShiftMappingDto;
import com.goev.central.dto.payout.PayoutModelDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.dto.shift.ShiftDto;
import com.goev.central.enums.partner.PartnerDutyStatus;
import com.goev.central.enums.partner.PartnerShiftStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftMappingRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.payout.PayoutModelRepository;
import com.goev.central.repository.shift.ShiftConfigurationRepository;
import com.goev.central.repository.shift.ShiftRepository;
import com.goev.central.service.partner.duty.PartnerShiftService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerShiftServiceImpl implements PartnerShiftService {

    private final PartnerShiftRepository partnerShiftRepository;
    private final PartnerShiftMappingRepository partnerShiftMappingRepository;
    private final PartnerRepository partnerRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftConfigurationRepository shiftConfigurationRepository;
    private final PayoutModelRepository payoutModelRepository;

    @Override
    public PaginatedResponseDto<PartnerShiftDto> getShifts(String status, PageDto page, FilterDto filter) {

        List<PartnerShiftDao> activeShifts ;

        if(PartnerShiftStatus.IN_PROGRESS.name().equals(status) || PartnerShiftStatus.PENDING.name().equals(status)  )
          activeShifts = partnerShiftRepository.findAllByStatus(status, page);
        else
            activeShifts = partnerShiftRepository.findAllByStatus(status, page,filter);

        if (CollectionUtils.isEmpty(activeShifts))
            return PaginatedResponseDto.<PartnerShiftDto>builder().elements(new ArrayList<>()).build();

        List<PartnerDao> partners = partnerRepository.findAllByIds(activeShifts.stream().map(PartnerShiftDao::getPartnerId).toList());
        Map<Integer, PartnerDao> partnerDaoMap = partners.stream().collect(Collectors.toMap(PartnerDao::getId, Function.identity()));

        List<PartnerShiftDto> shiftList = new ArrayList<>();
        activeShifts.forEach(x -> {
            PartnerViewDto partnerViewDto = PartnerViewDto.fromDao(partnerDaoMap.get(x.getPartnerId()));
            shiftList.add(PartnerShiftDto.fromDao(x, partnerViewDto));
        });
        return PaginatedResponseDto.<PartnerShiftDto>builder().elements(shiftList).build();

    }

    @Override
    public PartnerShiftMappingDto createShiftMapping(String partnerUUID, PartnerShiftMappingDto partnerShiftMappingDto) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        if (partnerShiftMappingDto.getShift() == null)
            throw new ResponseException("No shift details present.");

        ShiftDao shiftDao = shiftRepository.findByUUID(partnerShiftMappingDto.getShift().getUuid());

        if (shiftDao == null)
            throw new ResponseException("No shift found for Id :" + partnerShiftMappingDto.getShift().getUuid());


        List<ShiftConfigurationDao> shiftConfigurationDaoList = shiftConfigurationRepository.findByShiftId(shiftDao.getId());

        PartnerShiftMappingDao partnerShiftMappingDao = new PartnerShiftMappingDao();

        partnerShiftMappingDao.setPartnerId(partner.getId());
        partnerShiftMappingDao.setShiftId(shiftDao.getId());
        partnerShiftMappingDao.setLocationConfig(ApplicationConstants.GSON.toJson(partnerShiftMappingDto.getLocationConfig()));
        partnerShiftMappingDao.setDutyConfig(partnerShiftMappingDto.getDutyConfig());

        if (!CollectionUtils.isEmpty(shiftConfigurationDaoList)) {
            List<ShiftConfigurationDto> shiftConfigurationDtoList = shiftConfigurationDaoList.stream().map(x -> {
                PayoutModelDao payoutModelDao = payoutModelRepository.findById(x.getPayoutModelId());
                return ShiftConfigurationDto.fromDao(x, ShiftDto.fromDao(shiftDao), PayoutModelDto.fromDao(payoutModelDao));
            }).toList();

            partnerShiftMappingDao.setShiftConfig(ApplicationConstants.GSON.toJson(shiftConfigurationDtoList.stream().collect(Collectors.toMap(ShiftConfigurationDto::getDay,Function.identity()))));
        }

        partnerShiftMappingDao = partnerShiftMappingRepository.save(partnerShiftMappingDao);
        if (partnerShiftMappingDao == null)
            throw new ResponseException("Error in saving partner shift mapping");

        return PartnerShiftMappingDto.fromDao(partnerShiftMappingDao, PartnerViewDto.fromDao(partner), ShiftDto.fromDao(shiftDao));
    }

    @Override
    public Boolean deleteShiftMapping(String partnerUUID, String partnerShiftMappingUUID) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftMappingDao partnerShiftMappingDao = partnerShiftMappingRepository.findByUUID(partnerShiftMappingUUID);
        if (partnerShiftMappingDao == null)
            throw new ResponseException("No shift mapping found for Id :" + partnerShiftMappingUUID);

        partnerShiftMappingRepository.delete(partnerShiftMappingDao.getId());


        List<PartnerShiftDao> allPendingShifts = partnerShiftRepository.findAllByPartnerIdAndShiftIdAndStatus(partner.getId(),partnerShiftMappingDao.getShiftId(), PartnerShiftStatus.PENDING.name());
        if(!CollectionUtils.isEmpty(allPendingShifts)){
            for(PartnerShiftDao partnerShiftDao : allPendingShifts){
                partnerShiftRepository.delete(partnerShiftDao.getId());
            }
        }
        return true;
    }

    @Override
    public PartnerShiftDto updateShift(String partnerUUID, String shiftUUID, PartnerShiftDto partnerShiftDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByUUID(shiftUUID);
        if (partnerShiftDao == null)
            throw new ResponseException("No partner shift found for Id :" + shiftUUID);

        PartnerShiftDao newPartnerShiftDao = new PartnerShiftDao();
        newPartnerShiftDao.setPartnerId(partner.getId());

        newPartnerShiftDao.setId(partnerShiftDao.getId());
        newPartnerShiftDao.setUuid(partnerShiftDao.getUuid());
        partnerShiftDao = partnerShiftRepository.update(newPartnerShiftDao);
        if (partnerShiftDao == null)
            throw new ResponseException("Error in updating details partner shift");


        return PartnerShiftDto.fromDao(partnerShiftDao, PartnerViewDto.fromDao(partner));
    }

    @Override
    public PartnerShiftDto getShiftDetails(String partnerUUID, String shiftUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByUUID(shiftUUID);
        if (partnerShiftDao == null)
            throw new ResponseException("No partner shift found for Id :" + shiftUUID);


        return PartnerShiftDto.fromDao(partnerShiftDao, PartnerViewDto.fromDao(partner));
    }

    @Override
    public Boolean deleteShift(String partnerUUID, String shiftUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByUUID(shiftUUID);
        if (partnerShiftDao == null)
            throw new ResponseException("No partner shift found for Id :" + shiftUUID);
        partnerShiftRepository.delete(partnerShiftDao.getId());
        return true;
    }

    @Override
    public List<PartnerShiftMappingDto> getShiftMappings(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerShiftMappingDao> partnerShiftMappingDaoList = partnerShiftMappingRepository.findAllByPartnerId(partner.getId());

        List<PartnerShiftMappingDto> result = new ArrayList<>();

        for (PartnerShiftMappingDao partnerShiftMappingDao : partnerShiftMappingDaoList) {
            ShiftDao shiftDao = shiftRepository.findById(partnerShiftMappingDao.getShiftId());
            if (shiftDao == null)
                continue;
            result.add(PartnerShiftMappingDto.fromDao(partnerShiftMappingDao, PartnerViewDto.fromDao(partner), ShiftDto.fromDao(shiftDao)));
        }
        return result;

    }
}
