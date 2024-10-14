package com.goev.central.service.vehicle.detail.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.vehicle.detail.*;
import com.goev.central.dto.common.QrValueDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.vehicle.VehicleStatsDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.*;
import com.goev.central.enums.vehicle.VehicleOnboardingStatus;
import com.goev.central.enums.vehicle.VehicleStatus;
import com.goev.central.enums.vehicle.VehicleSubStatus;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.repository.vehicle.detail.*;
import com.goev.central.service.vehicle.detail.VehicleDetailService;
import com.goev.central.utilities.S3Utils;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleDetailServiceImpl implements VehicleDetailService {

    private final VehicleRepository vehicleRepository;
    private final VehicleDetailRepository vehicleDetailRepository;
    private final LocationRepository locationRepository;
    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final VehicleLeasingAgencyRepository vehicleLeasingAgencyRepository;
    private final VehicleFinancerRepository vehicleFinancerRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleManufacturerRepository vehicleManufacturerRepository;
    private final BusinessSegmentRepository businessSegmentRepository;
    private final BusinessClientRepository businessClientRepository;
    private final S3Utils s3;

    @Override
    public VehicleDetailDto createVehicle(VehicleDetailDto vehicleDto) {
        VehicleDao existingVehicle = vehicleRepository.findByPlateNumber(vehicleDto.getVehicleDetails().getPlateNumber());

        if (existingVehicle != null) {
            throw new ResponseException("Error in saving vehicle: Vehicle with Vehicle Number :" + vehicleDto.getVehicleDetails().getPlateNumber() + " already exist");
        }
        VehicleDao vehicleDao = getVehicleDao(vehicleDto);
        VehicleDao vehicle = vehicleRepository.save(vehicleDao);

        if (vehicle == null)
            throw new ResponseException("Error in saving details");

        VehicleDetailDao vehicleDetails = getVehicleDetailDao(vehicleDto, vehicleDao);
        vehicleDetails.setVehicleId(vehicle.getId());
        vehicleDetails = vehicleDetailRepository.save(vehicleDetails);
        if (vehicleDetails == null)
            throw new ResponseException("Error in saving vehicle details");

        vehicle.setImageUrl(vehicleDetails.getImageUrl());
        vehicle.setVehicleDetailsId(vehicleDetails.getId());
        vehicle.setViewInfo(ApplicationConstants.GSON.toJson(getVehicleViewDto(vehicleDetails, vehicle)));
        vehicleRepository.update(vehicle);

        return getVehicleDetailDto(vehicleDao, vehicleDetails);
    }

    private VehicleDao getVehicleDao(VehicleDetailDto vehicleDto) {
        VehicleDao vehicleDao = new VehicleDao();

        vehicleDao.setPlateNumber(vehicleDto.getVehicleDetails().getPlateNumber());
        vehicleDao.setOnboardingStatus(VehicleOnboardingStatus.ONBOARDED.name());
        vehicleDao.setStatus(VehicleStatus.AVAILABLE.name());
        vehicleDao.setSubStatus(VehicleSubStatus.NOT_ASSIGNED.name());
        return vehicleDao;
    }

    private VehicleViewDto getVehicleViewDto(VehicleDetailDao vehicleDetails, VehicleDao vehicleDao) {
        VehicleViewDto result = VehicleViewDto.builder()
                .plateNumber(vehicleDao.getPlateNumber())
                .vinNumber(vehicleDetails.getVinNumber())
                .motorNumber(vehicleDetails.getMotorNumber())
                .imageUrl(vehicleDao.getImageUrl())
                .onboardingDate(vehicleDetails.getOnboardingDate())
                .deboardingDate(vehicleDetails.getDeboardingDate())
                .uuid(vehicleDao.getUuid())
                .build();


        if (vehicleDetails.getHomeLocationId() != null) {
            LocationDao locationDao = locationRepository.findById(vehicleDetails.getHomeLocationId());

            if (locationDao != null)
                result.setHomeLocation(LocationDto.builder().uuid(locationDao.getUuid()).name(locationDao.getName()).build());
        }

        if (vehicleDetails.getVehicleModelId() != null) {
            VehicleModelDao vehicleModel = vehicleModelRepository.findById(vehicleDetails.getVehicleModelId());
            if (vehicleModel != null && vehicleModel.getManufacturerId() != null) {
                VehicleManufacturerDao vehicleManufacturer = vehicleManufacturerRepository.findById(vehicleModel.getManufacturerId());

                if (vehicleManufacturer != null) {
                    result.setVehicleModel(VehicleModelDto.builder()
                            .manufacturer(VehicleManufacturerDto.builder()
                                    .name(vehicleManufacturer.getName()).build())
                            .year(vehicleModel.getYear())
                            .month(vehicleModel.getMonth())
                            .variant(vehicleModel.getVariant())
                            .kmRange(vehicleModel.getKmRange())
                            .name(vehicleModel.getName())
                            .build());
                }
            }
        }
        return result;

    }


    @Override
    public VehicleDetailDto getVehicleDetails(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        VehicleDetailDao vehicleDetailDao = vehicleDetailRepository.findById(vehicle.getVehicleDetailsId());

        if (vehicleDetailDao == null)
            throw new ResponseException("No vehicle details found for Id :" + vehicleUUID);

        return getVehicleDetailDto(vehicle, vehicleDetailDao);
    }

    @Override
    public VehicleDetailDto updateVehicle(String vehicleUUID, VehicleDetailDto vehicleDetailDto) {

        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);
        VehicleDetailDao vehicleDetailDao = vehicleDetailRepository.findById(vehicle.getVehicleDetailsId());

        if (vehicleDetailDao == null)
            throw new ResponseException("No vehicle details found for Id :" + vehicleUUID);

        VehicleDetailDao vehicleDetails = getVehicleDetailDao(vehicleDetailDto, vehicle);
        vehicleDetails.setVehicleId(vehicle.getId());
        vehicleDetails = vehicleDetailRepository.save(vehicleDetails);
        if (vehicleDetails == null)
            throw new ResponseException("Error in saving vehicle details");

        vehicleDetailRepository.delete(vehicleDetailDao.getId());
        vehicle.setImageUrl(vehicleDetails.getImageUrl());
        vehicle.setVehicleDetailsId(vehicleDetails.getId());
        vehicle.setViewInfo(ApplicationConstants.GSON.toJson(getVehicleViewDto(vehicleDetails, vehicle)));
        vehicle = vehicleRepository.update(vehicle);
        return getVehicleDetailDto(vehicle, vehicleDetails);
    }

    @Override
    public String getVehicleQr(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);
        return ApplicationConstants.GSON.toJson(QrValueDto.builder()
                .name(vehicle.getPlateNumber())
                .entity("VEHICLE")
                .uuid(vehicleUUID)
                .timestamp(DateTime.now())
                .build());
    }

    private VehicleDetailDto getVehicleDetailDto(VehicleDao vehicle, VehicleDetailDao vehicleDetailDao) {
        VehicleDetailDto result = VehicleDetailDto.builder().build();
        setVehicleDetails(result, vehicle, vehicleDetailDao);
        setVehicleCategory(result, vehicleDetailDao.getVehicleCategoryId());
        setVehicleHomeLocation(result, vehicleDetailDao.getHomeLocationId());
        setVehicleLeasingAgency(result, vehicleDetailDao.getVehicleLeasingAgencyId());
        setVehicleFinancer(result, vehicleDetailDao.getVehicleFinancerId());
        setVehicleModel(result, vehicleDetailDao.getVehicleModelId());
        return result;
    }


    private VehicleDetailDao getVehicleDetailDao(VehicleDetailDto vehicleDto, VehicleDao vehicleDao) {
        VehicleDetailDao newVehicleDetails = new VehicleDetailDao();


        newVehicleDetails.setVinNumber(vehicleDto.getVinNumber());
        newVehicleDetails.setMotorNumber(vehicleDto.getMotorNumber());
        newVehicleDetails.setRegistrationDate(vehicleDto.getRegistrationDate());
        newVehicleDetails.setBatteryNumber(vehicleDto.getBatteryNumber());
        newVehicleDetails.setHasDuplicateKeys(vehicleDto.getHasDuplicateKeys());
        if (vehicleDto.getVehicleDetails() != null)
            newVehicleDetails.setPlateNumber(vehicleDto.getVehicleDetails().getPlateNumber());

        if (vehicleDto.getVehicleModel() != null) {

            VehicleModelDao vehicleModelDao = vehicleModelRepository.findByUUID(vehicleDto.getVehicleModel().getUuid());
            if (vehicleModelDao == null)
                throw new ResponseException("No vehicle model found for Id :" + vehicleDto.getVehicleModel().getUuid());
            newVehicleDetails.setVehicleModelId(vehicleModelDao.getId());

            if(vehicleModelDao.getKmRange()!=null) {
                VehicleStatsDto statsDto = VehicleStatsDto.builder().build();
                if(vehicleDao.getStats()!=null){
                    statsDto = ApplicationConstants.GSON.fromJson(vehicleDao.getStats(), VehicleStatsDto.class);
                }
                statsDto.setKmRange(vehicleModelDao.getKmRange());
                vehicleDao.setStats(ApplicationConstants.GSON.toJson(statsDto));
            }
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
            vehicleDao.setHomeLocationId(locationDao.getId());
            vehicleDao.setHomeLocationDetails(ApplicationConstants.GSON.toJson(LocationDto.fromDao(locationDao)));
        }else{
            vehicleDao.setHomeLocationId(null);
            vehicleDao.setHomeLocationDetails(null);
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

        newVehicleDetails.setImageUrl(vehicleDto.getImageUrl());
        if (vehicleDto.getImage() != null) {
            newVehicleDetails.setImageUrl(s3.getUrlForPath(vehicleDto.getImage().getPath(), "image/vehicle/" + vehicleDao.getPlateNumber()));
        }
        newVehicleDetails.setOnboardingDate(vehicleDto.getOnboardingDate());
        newVehicleDetails.setDeboardingDate(vehicleDto.getDeboardingDate());
        newVehicleDetails.setInsuranceExpiryDate(vehicleDto.getInsuranceExpiryDate());
        newVehicleDetails.setInsurancePolicyNumber(vehicleDto.getInsurancePolicyNumber());
        return newVehicleDetails;
    }

    private void setVehicleDetails(VehicleDetailDto result, VehicleDao vehicleDao, VehicleDetailDao vehicleDetailDao) {
        VehicleViewDto vehicleDto = VehicleViewDto.builder().build();
        vehicleDto.setPlateNumber(vehicleDao.getPlateNumber());
        vehicleDto.setUuid(vehicleDao.getUuid());
        vehicleDto.setImageUrl(vehicleDao.getImageUrl());
        result.setImageUrl(vehicleDao.getImageUrl());
        result.setVehicleDetails(vehicleDto);
        result.setUuid(vehicleDao.getUuid());
        if (vehicleDetailDao == null)
            return;
        result.setState(vehicleDetailDao.getState());
        result.setVinNumber(vehicleDetailDao.getVinNumber());
        result.setMotorNumber(vehicleDetailDao.getMotorNumber());
        result.setBatteryNumber(vehicleDetailDao.getBatteryNumber());
        result.setHasDuplicateKeys(vehicleDetailDao.getHasDuplicateKeys());
        result.setRegistrationDate(vehicleDetailDao.getRegistrationDate());
        result.setInsuranceExpiryDate(vehicleDetailDao.getInsuranceExpiryDate());
        result.setInsurancePolicyNumber(vehicleDetailDao.getInsurancePolicyNumber());
        result.setOnboardingDate(vehicleDetailDao.getOnboardingDate());
        result.setDeboardingDate(vehicleDetailDao.getDeboardingDate());
        result.setPlateNumber(vehicleDetailDao.getPlateNumber());
    }

    private void setVehicleHomeLocation(VehicleDetailDto result, Integer homeLocationId) {
        LocationDao locationDao = locationRepository.findById(homeLocationId);
        if (locationDao == null)
            return;
        result.setHomeLocation(LocationDto.builder()
                .coordinates(LatLongDto.builder()
                        .latitude(locationDao.getLatitude().doubleValue())
                        .longitude(locationDao.getLongitude().doubleValue())
                        .build())
                .name(locationDao.getName())
                .type(locationDao.getType())
                .uuid(locationDao.getUuid())
                .build());
    }

    private void setVehicleModel(VehicleDetailDto result, Integer vehicleModelId) {
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

    private void setVehicleFinancer(VehicleDetailDto result, Integer vehicleFinancerId) {
        VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findById(vehicleFinancerId);
        if (vehicleFinancerDao == null)
            return;
        result.setVehicleFinancer(VehicleFinancerDto.builder()
                .uuid(vehicleFinancerDao.getUuid())
                .name(vehicleFinancerDao.getName())
                .build());
    }

    private void setVehicleLeasingAgency(VehicleDetailDto result, Integer vehicleLeasingAgencyId) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findById(vehicleLeasingAgencyId);
        if (vehicleLeasingAgencyDao == null)
            return;
        result.setVehicleLeasingAgency(VehicleLeasingAgencyDto.builder()
                .uuid(vehicleLeasingAgencyDao.getUuid())
                .name(vehicleLeasingAgencyDao.getName())
                .build());

    }

    private void setVehicleCategory(VehicleDetailDto result, Integer vehicleCategoryId) {
        VehicleCategoryDao vehicleCategoryDao = vehicleCategoryRepository.findById(vehicleCategoryId);
        if (vehicleCategoryDao == null)
            return;
        result.setVehicleCategory(VehicleCategoryDto.builder()
                .uuid(vehicleCategoryDao.getUuid())
                .name(vehicleCategoryDao.getName())
                .build());
    }

}
