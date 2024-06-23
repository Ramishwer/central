package com.goev.central.service.challan.impl;

import com.goev.central.dao.challan.ChallanDao;
import com.goev.central.dto.challan.ChallanDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.challan.ChallanRepository;
import com.goev.central.service.challan.ChallanService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ChallanServiceImpl implements ChallanService {

    private final ChallanRepository challanRepository;

    @Override
    public PaginatedResponseDto<ChallanDto> getChallans() {
        PaginatedResponseDto<ChallanDto> result = PaginatedResponseDto.<ChallanDto>builder().elements(new ArrayList<>()).build();
        List<ChallanDao> challanDaos = challanRepository.findAllActive();
        if (CollectionUtils.isEmpty(challanDaos))
            return result;

        for (ChallanDao challanDao : challanDaos) {
            result.getElements().add(ChallanDto.builder()
                    .uuid(challanDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public ChallanDto createChallan(ChallanDto challanDto) {

        ChallanDao challanDao = new ChallanDao();
        challanDao = challanRepository.save(challanDao);
        if (challanDao == null)
            throw new ResponseException("Error in saving challan challan");
        return ChallanDto.builder()
                .uuid(challanDao.getUuid()).build();
    }

    @Override
    public ChallanDto updateChallan(String challanUUID, ChallanDto challanDto) {
        ChallanDao challanDao = challanRepository.findByUUID(challanUUID);
        if (challanDao == null)
            throw new ResponseException("No challan  found for Id :" + challanUUID);
        ChallanDao newChallanDao = new ChallanDao();


        newChallanDao.setId(challanDao.getId());
        newChallanDao.setUuid(challanDao.getUuid());
        challanDao = challanRepository.update(newChallanDao);
        if (challanDao == null)
            throw new ResponseException("Error in updating details challan ");
        return ChallanDto.builder()
                .uuid(challanDao.getUuid()).build();
    }

    @Override
    public ChallanDto getChallanDetails(String challanUUID) {
        ChallanDao challanDao = challanRepository.findByUUID(challanUUID);
        if (challanDao == null)
            throw new ResponseException("No challan  found for Id :" + challanUUID);
        return ChallanDto.builder()
                .uuid(challanDao.getUuid()).build();
    }

    @Override
    public Boolean deleteChallan(String challanUUID) {
        ChallanDao challanDao = challanRepository.findByUUID(challanUUID);
        if (challanDao == null)
            throw new ResponseException("No challan  found for Id :" + challanUUID);
        challanRepository.delete(challanDao.getId());
        return true;
    }
}
