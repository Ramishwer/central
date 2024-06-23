package com.goev.central.service.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerCategoryDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerCategoryDto;
import com.goev.central.repository.partner.detail.PartnerCategoryRepository;
import com.goev.central.service.partner.detail.PartnerCategoryService;
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
public class PartnerCategoryServiceImpl implements PartnerCategoryService {

    private final PartnerCategoryRepository partnerCategoryRepository;

    @Override
    public PaginatedResponseDto<PartnerCategoryDto> getCategories() {
        PaginatedResponseDto<PartnerCategoryDto> result = PaginatedResponseDto.<PartnerCategoryDto>builder().elements(new ArrayList<>()).build();
        List<PartnerCategoryDao> partnerCategoryDaos = partnerCategoryRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerCategoryDaos))
            return result;

        for (PartnerCategoryDao partnerCategoryDao : partnerCategoryDaos) {
            result.getElements().add(getPartnerCategoryDto(partnerCategoryDao));
        }
        return result;
    }

    private PartnerCategoryDto getPartnerCategoryDto(PartnerCategoryDao partnerCategoryDao) {
        return PartnerCategoryDto.builder()
                .name(partnerCategoryDao.getName())
                .description(partnerCategoryDao.getDescription())
                .uuid(partnerCategoryDao.getUuid())
                .build();
    }

    @Override
    public PartnerCategoryDto createCategory(PartnerCategoryDto partnerCategoryDto) {

        PartnerCategoryDao partnerCategoryDao = getPartnerCategoryDao(partnerCategoryDto);
        partnerCategoryDao = partnerCategoryRepository.save(partnerCategoryDao);
        if (partnerCategoryDao == null)
            throw new ResponseException("Error in saving partner category");
        return getPartnerCategoryDto(partnerCategoryDao);
    }

    private PartnerCategoryDao getPartnerCategoryDao(PartnerCategoryDto partnerCategoryDto) {
        PartnerCategoryDao partnerCategoryDao = new PartnerCategoryDao();
        partnerCategoryDao.setName(partnerCategoryDto.getName());
        partnerCategoryDao.setDescription(partnerCategoryDto.getDescription());
        return partnerCategoryDao;
    }

    @Override
    public PartnerCategoryDto updateCategory(String categoryUUID, PartnerCategoryDto partnerCategoryDto) {
        PartnerCategoryDao partnerCategoryDao = partnerCategoryRepository.findByUUID(categoryUUID);
        if (partnerCategoryDao == null)
            throw new ResponseException("No partner category found for Id :" + categoryUUID);
        PartnerCategoryDao newPartnerCategoryDao = getPartnerCategoryDao(partnerCategoryDto);

        newPartnerCategoryDao.setId(partnerCategoryDao.getId());
        newPartnerCategoryDao.setUuid(partnerCategoryDao.getUuid());
        partnerCategoryDao = partnerCategoryRepository.update(newPartnerCategoryDao);
        if (partnerCategoryDao == null)
            throw new ResponseException("Error in updating details partner category");
        return getPartnerCategoryDto(partnerCategoryDao);
    }

    @Override
    public PartnerCategoryDto getCategoryDetails(String categoryUUID) {
        PartnerCategoryDao partnerCategoryDao = partnerCategoryRepository.findByUUID(categoryUUID);
        if (partnerCategoryDao == null)
            throw new ResponseException("No partner category found for Id :" + categoryUUID);
        return getPartnerCategoryDto(partnerCategoryDao);
    }

    @Override
    public Boolean deleteCategory(String categoryUUID) {
        PartnerCategoryDao partnerCategoryDao = partnerCategoryRepository.findByUUID(categoryUUID);
        if (partnerCategoryDao == null)
            throw new ResponseException("No partner category found for Id :" + categoryUUID);
        partnerCategoryRepository.delete(partnerCategoryDao.getId());
        return true;
    }
}
