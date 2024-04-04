package com.goev.central.service.vehicle.impl;


import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.vehicle.detail.*;
import com.goev.central.dao.vehicle.document.VehicleDocumentDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.*;
import com.goev.central.dto.vehicle.document.VehicleDocumentDto;
import com.goev.central.enums.DocumentStatus;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.repository.vehicle.detail.*;
import com.goev.central.repository.vehicle.document.VehicleDocumentRepository;
import com.goev.central.repository.vehicle.document.VehicleDocumentTypeRepository;
import com.goev.central.service.vehicle.VehicleService;
import com.goev.central.utilities.S3Utils;
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
    private final VehicleFinancerRepository vehicleFinancerRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleManufacturerRepository vehicleManufacturerRepository;
    private final VehicleDocumentTypeRepository vehicleDocumentTypeRepository;
    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final LocationRepository locationRepository;
    private final S3Utils s3;


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
        setVehicleHomeLocation(result,vehicleDetails.getHomeLocationId());
        setVehicleLeasingAgency(result, vehicleDetails.getVehicleLeasingAgencyId());
        setVehicleFinancer(result, vehicleDetails.getVehicleFinancerId());
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
        setVehicleHomeLocation(result,newVehicleDetails.getHomeLocationId());
        setVehicleLeasingAgency(result, newVehicleDetails.getVehicleLeasingAgencyId());
        setVehicleFinancer(result, newVehicleDetails.getVehicleFinancerId());
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
        setVehicleHomeLocation(result,vehicleDetailDao.getHomeLocationId());
        setVehicleLeasingAgency(result, vehicleDetailDao.getVehicleLeasingAgencyId());
        setVehicleFinancer(result, vehicleDetailDao.getVehicleFinancerId());
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

            VehicleModelDao model = vehicleModelRepository.findById(vehicleDetails.getVehicleModelId());
            VehicleManufacturerDao manufacturer = vehicleManufacturerRepository.findById(model.getManufacturerId());
            result.getElements().add(VehicleViewDto.builder()
                    .plateNumber(vehicle.getPlateNumber())
                    .motorNumber(vehicleDetails.getMotorNumber())
                    .registrationDate(vehicleDetails.getRegistrationDate())
                    .vinNumber(vehicleDetails.getVinNumber())
                    .uuid(vehicle.getUuid())
                    .state(vehicle.getState())
                    .manufacturerName(manufacturer.getName())
                    .year(model.getYear())
                    .modelName(model.getName())
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
                .name(vehicleModelDao.getName())
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

    private void setVehicleFinancer(VehicleDetailsDto result, Integer vehicleFinancerId) {
        VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findById(vehicleFinancerId);
        if (vehicleFinancerDao == null)
            return;
        result.setVehicleFinancer(VehicleFinancerDto.builder()
                .uuid(vehicleFinancerDao.getUuid())
                .name(vehicleFinancerDao.getName())
                .build());
    }

    private void setVehicleLeasingAgency(VehicleDetailsDto result, Integer vehicleLeasingAgencyId) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findById(vehicleLeasingAgencyId);
        if (vehicleLeasingAgencyDao == null)
            return;
        result.setVehicleLeasingAgency(VehicleLeasingAgencyDto.builder()
                .uuid(vehicleLeasingAgencyDao.getUuid())
                .name(vehicleLeasingAgencyDao.getName())
                .build());

    }

    private void setVehicleCategory(VehicleDetailsDto result, Integer vehicleCategoryId) {
        VehicleCategoryDao vehicleCategoryDao = vehicleCategoryRepository.findById(vehicleCategoryId);
        if (vehicleCategoryDao == null)
            return;
        result.setVehicleCategory(VehicleCategoryDto.builder()
                .uuid(vehicleCategoryDao.getUuid())
                .name(vehicleCategoryDao.getName())
                .build());
    }

    private void setVehicleHomeLocation(VehicleDetailsDto result, Integer homeLocationId) {
        LocationDao locationDao = locationRepository.findById(homeLocationId);
        if (locationDao == null)
            return;
        result.setHomeLocation(LocationDto.builder()
                .latitude(locationDao.getLatitude())
                .longitude(locationDao.getLongitude())
                .name(locationDao.getName())
                .type(locationDao.getType())
                .uuid(locationDao.getUuid())
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
        result.setInsuranceExpiry(vehicleDetailDao.getInsuranceExpiry());
        result.setInsurancePolicyNumber(vehicleDetailDao.getInsurancePolicyNumber());
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

        if (vehicleDto.getHomeLocation() != null) {

            LocationDao locationDao = locationRepository.findByUUID(vehicleDto.getHomeLocation().getUuid());
            if (locationDao == null)
                throw new ResponseException("No location found for Id :" + vehicleDto.getHomeLocation().getUuid());
            newVehicleDetails.setHomeLocationId(locationDao.getId());
        }

        if (vehicleDto.getVehicleFinancer() != null) {

            VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findByUUID(vehicleDto.getVehicleFinancer().getUuid());
            if (vehicleFinancerDao == null)
                throw new ResponseException("No vehicle financer found for Id :" + vehicleDto.getVehicleFinancer().getUuid());
            newVehicleDetails.setVehicleFinancerId(vehicleFinancerDao.getId());
        }

        if (vehicleDto.getVehicleLeasingAgency() != null) {

            VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findByUUID(vehicleDto.getVehicleLeasingAgency().getUuid());
            if (vehicleLeasingAgencyDao == null)
                throw new ResponseException("No vehicle leasing agency found for Id :" + vehicleDto.getVehicleLeasingAgency().getUuid());
            newVehicleDetails.setVehicleLeasingAgencyId(vehicleLeasingAgencyDao.getId());
        }


        newVehicleDetails.setOnboardingDate(vehicleDto.getOnboardingDate());
        newVehicleDetails.setDeboardingDate(vehicleDto.getDeboardingDate());
        newVehicleDetails.setInsuranceExpiry(vehicleDto.getInsuranceExpiry());
        newVehicleDetails.setInsurancePolicyNumber(vehicleDto.getInsurancePolicyNumber());
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
                newDoc.setUrl(s3.getUrlForPath(newDoc.getUrl(),idToVehicleDocumentTypeMap.get(typeId).getS3Key()));
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
