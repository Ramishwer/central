package com.goev.central.service.vehicle.impl;


import com.goev.central.dao.vehicle.detail.*;
import com.goev.central.dao.vehicle.document.VehicleDocumentDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.*;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;
import com.goev.central.enums.DocumentStatus;
import com.goev.central.repository.vehicle.detail.*;
import com.goev.central.repository.vehicle.document.VehicleDocumentRepository;
import com.goev.central.repository.vehicle.document.VehicleDocumentTypeRepository;
import com.goev.central.service.vehicle.VehicleService;
import com.goev.lib.enums.RecordState;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {


    private final VehicleRepository vehicleRepository;
    private final VehicleDetailRepository vehicleDetailRepository;
    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final VehicleLeasingAgencyRepository vehicleLeasingAgencyRepository;
    private final VehicleFinancierRepository vehicleFinancierRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleManufacturerRepository vehicleManufacturerRepository;
    private final VehicleDocumentTypeRepository vehicleDocumentTypeRepository;
    private final VehicleDocumentRepository vehicleDocumentRepository;


    @Override
    public VehicleDetailsDto createVehicle(VehicleDetailsDto vehicleDto) {

        VehicleDao existingVehicle = vehicleRepository.findByPlateNumber(vehicleDto.getDetails().getPlateNumber());

        if (existingVehicle != null) {
            throw new ResponseException("Error in saving vehicle: Vehicle with Plate Number :" + vehicleDto.getDetails().getPlateNumber() + " already exist");
        }

        VehicleDao vehicle = new VehicleDao();

        vehicle.setPlateNumber(vehicleDto.getDetails().getPlateNumber());
        vehicle = vehicleRepository.save(vehicle);
        if (vehicle == null)
            throw new ResponseException("Error in saving vehicle");

        VehicleDetailDao vehicleDetails = getVehicleDetailDao(vehicleDto);
        vehicleDetails.setVehicleId(vehicle.getId());
        vehicleDetails = vehicleDetailRepository.save(vehicleDetails);
        if (vehicleDetails == null)
            throw new ResponseException("Error in saving vehicle details");

        vehicle.setVehicleDetailsId(vehicleDetails.getId());
        vehicleRepository.update(vehicle);

        saveVehicleDocuments(vehicleDto.getDocuments(), vehicle.getId());

        VehicleDetailsDto result = VehicleDetailsDto.builder().build();
        setVehicleDetails(result, vehicle, vehicleDetails);
        setVehicleCategory(result, vehicleDetails.getVehicleCategoryId());
        setVehicleLeasingAgency(result, vehicleDetails.getVehicleLeasingAgencyId());
        setVehicleFinancier(result, vehicleDetails.getVehicleFinancierId());
        setVehicleModel(result, vehicleDetails.getVehicleModelId());
        setVehicleDocuments(result, vehicle.getId());
        return result;
    }

    @Override
    public VehicleDetailsDto updateVehicle(String vehicleUUID, VehicleDetailsDto vehicleDto) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        VehicleDetailDao oldVehicleDetails = vehicleDetailRepository.findById(vehicle.getVehicleDetailsId());
        if (oldVehicleDetails == null)
            throw new ResponseException("No vehicle details found for Id :" + vehicleUUID);


        VehicleDetailDao newVehicleDetails = getVehicleDetailDao(vehicleDto);
        newVehicleDetails.setVehicleId(oldVehicleDetails.getVehicleId());
        newVehicleDetails = vehicleDetailRepository.save(newVehicleDetails);

        vehicleDetailRepository.delete(oldVehicleDetails.getId());

        vehicle.setVehicleDetailsId(newVehicleDetails.getId());
        vehicle = vehicleRepository.update(vehicle);
        if (vehicle == null)
            throw new ResponseException("Error in saving details");

        saveVehicleDocuments(vehicleDto.getDocuments(), vehicle.getId());
        VehicleDetailsDto result = VehicleDetailsDto.builder().build();
        setVehicleDetails(result, vehicle, newVehicleDetails);
        setVehicleCategory(result, newVehicleDetails.getVehicleCategoryId());
        setVehicleLeasingAgency(result, newVehicleDetails.getVehicleLeasingAgencyId());
        setVehicleFinancier(result, newVehicleDetails.getVehicleFinancierId());
        setVehicleModel(result, newVehicleDetails.getVehicleModelId());
        setVehicleDocuments(result, vehicle.getId());
        return result;

    }

    @Override
    public VehicleDetailsDto getVehicleDetails(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        VehicleDetailDao vehicleDetailDao = vehicleDetailRepository.findById(vehicle.getVehicleDetailsId());

        if (vehicleDetailDao == null)
            throw new ResponseException("No vehicle details found for Id :" + vehicleUUID);

        VehicleDetailsDto result = VehicleDetailsDto.builder().build();
        setVehicleDetails(result, vehicle, vehicleDetailDao);
        setVehicleCategory(result, vehicleDetailDao.getVehicleCategoryId());
        setVehicleLeasingAgency(result, vehicleDetailDao.getVehicleLeasingAgencyId());
        setVehicleFinancier(result, vehicleDetailDao.getVehicleFinancierId());
        setVehicleModel(result, vehicleDetailDao.getVehicleModelId());
        setVehicleDocuments(result, vehicle.getId());

        return result;
    }

    @Override
    public Boolean deleteVehicle(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        vehicleRepository.delete(vehicle.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<VehicleViewDto> getVehicles() {
        PaginatedResponseDto<VehicleViewDto> result = PaginatedResponseDto.<VehicleViewDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<VehicleDao> vehicles = vehicleRepository.findAll();
        if (CollectionUtils.isEmpty(vehicles))
            return result;

        List<VehicleDetailDao> vehicleDetailsForVehicles = vehicleDetailRepository.findAllByIds(vehicles.stream().map(VehicleDao::getVehicleDetailsId).toList());
        Map<Integer, VehicleDetailDao> vehicleDetailMap = vehicleDetailsForVehicles.stream().collect(Collectors.toMap(VehicleDetailDao::getId, Function.identity()));
        for (VehicleDao vehicle : vehicles) {
            VehicleDetailDao vehicleDetails = vehicleDetailMap.get(vehicle.getVehicleDetailsId());
            result.getElements().add(VehicleViewDto.builder()
                    .plateNumber(vehicle.getPlateNumber())
                    .motorNumber(vehicleDetails.getMotorNumber())
                    .registrationDate(vehicleDetails.getRegistrationDate())
                    .vinNumber(vehicleDetails.getVinNumber())
                    .uuid(vehicle.getUuid())
                    .state(vehicle.getState())
                    .build());
        }
        return result;
    }

    private void setVehicleDocuments(VehicleDetailsDto result, Integer vehicleId) {
        List<VehicleDocumentTypeDao> activeDocumentTypes = vehicleDocumentTypeRepository.findAll();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return;
        Map<Integer, VehicleDocumentTypeDao> documentTypeIdToDocumentTypeMap = activeDocumentTypes.stream()
                .collect(Collectors.toMap(VehicleDocumentTypeDao::getId, Function.identity()));
        List<Integer> activeDocumentTypeIds = activeDocumentTypes.stream().map(VehicleDocumentTypeDao::getId).toList();


        Map<Integer, VehicleDocumentDao> existingDocumentMap = vehicleDocumentRepository.getExistingDocumentMap(vehicleId, activeDocumentTypeIds);
        List<VehicleDocumentDto> documentList = VehicleDetailsDto.getVehicleDocumentDtoList(documentTypeIdToDocumentTypeMap, existingDocumentMap);
        result.setDocuments(documentList);
    }


    private void setVehicleModel(VehicleDetailsDto result, Integer vehicleModelId) {
        VehicleModelDao vehicleModelDao = vehicleModelRepository.findById(vehicleModelId);
        if (vehicleModelDao == null)
            return;
        result.setVehicleModel(VehicleModelDto.builder()
                .model(vehicleModelDao.getModel())
                .variant(vehicleModelDao.getVariant())
                .month(vehicleModelDao.getMonth())
                .year(vehicleModelDao.getYear())
                .batteryCapacity(vehicleModelDao.getBatteryCapacity())
                .kmRange(vehicleModelDao.getKmRange())
                .uuid(vehicleModelDao.getUuid())
                .build());
        VehicleManufacturerDao vehicleManufacturerDao = vehicleManufacturerRepository.findById(vehicleModelDao.getManufacturerId());
        if (vehicleManufacturerDao == null)
            return;
        result.getVehicleModel().setManufacturer(VehicleManufacturerDto.builder()
                .uuid(vehicleManufacturerDao.getUuid())
                .name(vehicleManufacturerDao.getName())
                .build());

    }

    private void setVehicleFinancier(VehicleDetailsDto result, Integer vehicleFinancierId) {
        VehicleFinancierDao vehicleFinancierDao = vehicleFinancierRepository.findById(vehicleFinancierId);
        if (vehicleFinancierDao == null)
            return;
        result.setVehicleFinancier(VehicleFinancierDto.builder()
                .uuid(vehicleFinancierDao.getUuid())
                .name(vehicleFinancierDao.getName())
                .build());
    }

    private void setVehicleLeasingAgency(VehicleDetailsDto result, Integer vehicleLeasingAgencyId) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findById(vehicleLeasingAgencyId);
        if (vehicleLeasingAgencyDao == null)
            return;
        result.setVehicleFinancier(VehicleFinancierDto.builder()
                .uuid(vehicleLeasingAgencyDao.getUuid())
                .name(vehicleLeasingAgencyDao.getName())
                .build());

    }

    private void setVehicleCategory(VehicleDetailsDto result, Integer vehicleCategoryId) {
        VehicleCategoryDao vehicleCategoryDao = vehicleCategoryRepository.findById(vehicleCategoryId);
        if (vehicleCategoryDao == null)
            return;
        result.setVehicleFinancier(VehicleFinancierDto.builder()
                .uuid(vehicleCategoryDao.getUuid())
                .name(vehicleCategoryDao.getName())
                .build());
    }

    private void setVehicleDetails(VehicleDetailsDto result, VehicleDao vehicle, VehicleDetailDao vehicleDetailDao) {

        VehicleDto vehicleDto = VehicleDto.builder().build();
        vehicleDto.setPlateNumber(vehicle.getPlateNumber());
        vehicleDto.setState(vehicle.getState());
        vehicleDto.setUuid(vehicle.getUuid());


        result.setDetails(vehicleDto);
        result.setUuid(vehicle.getUuid());
        if (vehicleDetailDao == null)
            return;
        result.getDetails().setVinNumber(vehicleDetailDao.getVinNumber());
        result.getDetails().setMotorNumber(vehicleDetailDao.getMotorNumber());
        result.getDetails().setRegistrationDate(vehicleDetailDao.getRegistrationDate());

        result.setOnboardingDate(vehicleDetailDao.getOnboardingDate());
        result.setDeboardingDate(vehicleDetailDao.getDeboardingDate());
    }

    private VehicleDetailDao getVehicleDetailDao(VehicleDetailsDto vehicleDto) {
        VehicleDetailDao newVehicleDetails = new VehicleDetailDao();

        if (vehicleDto.getDetails() != null) {
            newVehicleDetails.setVinNumber(vehicleDto.getDetails().getVinNumber());
            newVehicleDetails.setMotorNumber(vehicleDto.getDetails().getMotorNumber());
            newVehicleDetails.setRegistrationDate(vehicleDto.getDetails().getRegistrationDate());
        }

        if (vehicleDto.getVehicleModel() != null) {

            VehicleModelDao vehicleModelDao = vehicleModelRepository.findByUUID(vehicleDto.getVehicleModel().getUuid());
            if (vehicleModelDao == null)
                throw new ResponseException("No vehicle model found for Id :" + vehicleDto.getVehicleModel().getUuid());
            newVehicleDetails.setVehicleModelId(vehicleModelDao.getId());
        }

        if (vehicleDto.getVehicleCategory() != null) {

            VehicleCategoryDao vehicleCategoryDao = vehicleCategoryRepository.findByUUID(vehicleDto.getVehicleCategory().getUuid());
            if (vehicleCategoryDao == null)
                throw new ResponseException("No vehicle category found for Id :" + vehicleDto.getVehicleCategory().getUuid());
            newVehicleDetails.setVehicleCategoryId(vehicleCategoryDao.getId());
        }

        if (vehicleDto.getVehicleFinancier() != null) {

            VehicleFinancierDao vehicleFinancierDao = vehicleFinancierRepository.findByUUID(vehicleDto.getVehicleFinancier().getUuid());
            if (vehicleFinancierDao == null)
                throw new ResponseException("No vehicle financier found for Id :" + vehicleDto.getVehicleFinancier().getUuid());
            newVehicleDetails.setVehicleFinancierId(vehicleFinancierDao.getId());
        }

        if (vehicleDto.getVehicleLeasingAgency() != null) {

            VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findByUUID(vehicleDto.getVehicleLeasingAgency().getUuid());
            if (vehicleLeasingAgencyDao == null)
                throw new ResponseException("No vehicle leasing agency found for Id :" + vehicleDto.getVehicleLeasingAgency().getUuid());
            newVehicleDetails.setVehicleLeasingAgencyId(vehicleLeasingAgencyDao.getId());
        }


        newVehicleDetails.setOnboardingDate(vehicleDto.getOnboardingDate());
        newVehicleDetails.setDeboardingDate(vehicleDto.getDeboardingDate());
        return newVehicleDetails;
    }

    private void saveVehicleDocuments(List<VehicleDocumentDto> documents, Integer vehicleId) {
        List<VehicleDocumentTypeDao> activeDocumentTypes = vehicleDocumentTypeRepository.findAll();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return;
        Map<String, VehicleDocumentTypeDao> uuidToVehicleDocumentTypeMap = activeDocumentTypes.stream().collect(Collectors.toMap(VehicleDocumentTypeDao::getUuid, Function.identity()));
        Map<Integer, VehicleDocumentTypeDao> idToVehicleDocumentTypeMap = activeDocumentTypes.stream().collect(Collectors.toMap(VehicleDocumentTypeDao::getId, Function.identity()));
        Map<Integer, VehicleDocumentDao> existingDocumentMap = vehicleDocumentRepository.getExistingDocumentMap(vehicleId, new ArrayList<>(idToVehicleDocumentTypeMap.keySet()));
        Map<Integer, VehicleDocumentDao> newDocumentMap = getDocumentMap(documents, vehicleId, uuidToVehicleDocumentTypeMap);
        List<VehicleDocumentDao> documentToDelete = new ArrayList<>();

        for (Map.Entry<Integer, VehicleDocumentTypeDao> entry : idToVehicleDocumentTypeMap.entrySet()) {
            Integer typeId = entry.getKey();
            VehicleDocumentDao existingDoc = existingDocumentMap.getOrDefault(typeId, null);
            VehicleDocumentDao newDoc = newDocumentMap.getOrDefault(typeId, null);

            if (existingDoc == null && newDoc == null)
                continue;

            if (newDoc != null) {
                if (existingDoc != null) {
                    if(existingDoc.getUuid().equals(newDoc.getUuid()))
                        continue;
                    documentToDelete.add(existingDoc);
                }
                vehicleDocumentRepository.save(newDoc);
            }
            if(existingDoc!=null && newDoc ==null){
                documentToDelete.add(existingDoc);
            }
        }
        if (!CollectionUtils.isEmpty(documentToDelete)) {
            for (VehicleDocumentDao document : documentToDelete) {
                vehicleDocumentRepository.delete(document.getId());
            }
        }
    }

    private Map<Integer, VehicleDocumentDao> getDocumentMap(List<VehicleDocumentDto> documents, Integer vehicleId, Map<String, VehicleDocumentTypeDao> uuidToVehicleDocumentTypeMap) {
        Map<Integer, VehicleDocumentDao> result = new HashMap<>();
        for (VehicleDocumentDto documentDto : documents) {
            if (documentDto.getType() != null && documentDto.getType().getUuid() != null) {
                VehicleDocumentTypeDao type = uuidToVehicleDocumentTypeMap.get(documentDto.getType().getUuid());
                if (type != null) {
                    VehicleDocumentDao vehicleDocumentDao = getVehicleDocumentDao(vehicleId, documentDto,type );
                    result.put(type.getId(), vehicleDocumentDao);
                }
            }
        }
        return result;
    }


    private VehicleDocumentDao getVehicleDocumentDao(Integer id, VehicleDocumentDto document, VehicleDocumentTypeDao vehicleDocumentTypeDao) {
        VehicleDocumentDao vehicleDocumentDao = new VehicleDocumentDao();
        vehicleDocumentDao.setVehicleId(id);
        vehicleDocumentDao.setStatus(DocumentStatus.UPLOADED.name());
        vehicleDocumentDao.setFileName(document.getFileName());
        vehicleDocumentDao.setVehicleDocumentTypeId(vehicleDocumentTypeDao.getId());
//            vehicleDocumentDao.setExtension(document.get);
        vehicleDocumentDao.setUrl(document.getUrl());
        vehicleDocumentDao.setDescription(document.getDescription());
        return vehicleDocumentDao;
    }
}
