package com.goev.central.service.partner.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.FirebaseLocationDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerActionDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.partner.detail.PartnerTrackingDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.booking.BookingSubStatus;
import com.goev.central.enums.partner.PartnerDutyStatus;
import com.goev.central.enums.partner.PartnerOnboardingStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.repository.FirebaseRepository;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.service.partner.detail.PartnerService;
import com.goev.lib.exceptions.ResponseException;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerServiceImpl implements PartnerService {


    private final PartnerRepository partnerRepository;
    private final PartnerDutyRepository partnerDutyRepository;
    private final BookingRepository bookingRepository;
    private final PartnerShiftRepository partnerShiftRepository;
    private final VehicleRepository vehicleRepository;
    private final FirebaseRepository firebaseRepository;

    @Override
    public Boolean deletePartner(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);
        partnerRepository.delete(partner.getId());
        return true;
    }


    public PartnerDao updatePartnerOnboardingStatus(PartnerDao partner, PartnerOnboardingStatus status) {
        partner.setOnboardingStatus(status.name());
        partner = partnerRepository.update(partner);
        return partner;
    }

    @Override
    public PaginatedResponseDto<PartnerDto> getPartnerStatuses(String status) {
        PaginatedResponseDto<PartnerDto> result = PaginatedResponseDto.<PartnerDto>builder().elements(new ArrayList<>()).build();
        List<PartnerDao> partners = null;
        if (PartnerStatus.OFF_DUTY.name().equals(status))
            partners = partnerRepository.findAllByStatusAndShiftIdNotNull(Collections.singletonList(status));
        else if (PartnerStatus.ON_DUTY.name().equals(status))
            partners = partnerRepository.findAllByStatus(Arrays.asList(PartnerStatus.ON_DUTY.name(), PartnerStatus.VEHICLE_ASSIGNED.name(),
                    PartnerStatus.CHECKLIST.name(), PartnerStatus.RETURN_CHECKLIST.name()));
        else if (PartnerStatus.ONLINE.name().equals(status))
            partners = partnerRepository.findAllByStatus(Arrays.asList(PartnerStatus.ONLINE.name(), PartnerStatus.ON_BOOKING.name()));
        return getPartnerDtoPaginatedResponseDto(partners, result);
    }

    @Override
    public PartnerDto getPartnerStatus(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setUuid(partner.getUuid());
        partnerDto.setPunchId(partner.getPunchId());
        partnerDto.setStatus(partner.getStatus());
        partnerDto.setSubStatus(partner.getSubStatus());
        partnerDto.setLocationStatus(partner.getLocationStatus());

        partnerDto.setVehicleDetails(ApplicationConstants.GSON.fromJson(partner.getVehicleDetails(), VehicleViewDto.class));
        partnerDto.setDutyDetails(ApplicationConstants.GSON.fromJson(partner.getDutyDetails(), PartnerDutyDto.class));
        partnerDto.setBookingDetails(ApplicationConstants.GSON.fromJson(partner.getBookingDetails(), BookingViewDto.class));
        partnerDto.setLocationDetails(ApplicationConstants.GSON.fromJson(partner.getLocationDetails(), LocationDto.class));
        partnerDto.setPartnerDetails(PartnerViewDto.fromDao(partner));
        return partnerDto;
    }

    @Override
    public PaginatedResponseDto<PartnerTrackingDto> getPartnerTrackings() {
        PaginatedResponseDto<PartnerTrackingDto> result = PaginatedResponseDto.<PartnerTrackingDto>builder().elements(new ArrayList<>()).build();
        List<PartnerDao> partners = partnerRepository.findAllByStatus(Arrays.stream(PartnerStatus.values())
                .filter(x -> (!x.equals(PartnerStatus.OFF_DUTY) && !x.equals(PartnerStatus.ON_DUTY)))
                .map(PartnerStatus::name)
                .toList());

        try {
            Map<String, Object> locationData = firebaseRepository.getFromFirebase("/partner");

            for (PartnerDao partnerDao : partners) {
                PartnerTrackingDto partnerTrackingDto = PartnerTrackingDto.builder()
                        .partnerDetails(PartnerDto.fromDao(partnerDao))
                        .build();
                if (locationData.containsKey(partnerDao.getUuid())) {
                    Type t = new TypeToken<Map<String, Object>>() {
                    }.getType();
                    Map<String, Object> partnerData = ApplicationConstants.GSON.fromJson(ApplicationConstants.GSON.toJson(locationData.get(partnerDao.getUuid())), t);
                    if (partnerData.containsKey("location")) {

                        FirebaseLocationDto firebaseLocationDto = ApplicationConstants.GSON.fromJson(ApplicationConstants.GSON.toJson(partnerData.get("location")), FirebaseLocationDto.class);
                        if (firebaseLocationDto.getCoords() != null)
                            partnerTrackingDto.setLocation(firebaseLocationDto.getCoords());
                    }
                }
                result.getElements().add(partnerTrackingDto);
            }
        } catch (Exception e) {
            log.error("Error in fetching location data", e);
        }

        return result;

    }


    private PaginatedResponseDto<PartnerDto> getPartnerDtoPaginatedResponseDto(List<PartnerDao> partners, PaginatedResponseDto<PartnerDto> result) {
        if (CollectionUtils.isEmpty(partners))
            return result;
        for (PartnerDao partner : partners) {
            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setUuid(partner.getUuid());
            partnerDto.setPunchId(partner.getPunchId());
            partnerDto.setStatus(partner.getStatus());
            partnerDto.setSubStatus(partner.getSubStatus());
            partnerDto.setLocationStatus(partner.getLocationStatus());

            partnerDto.setVehicleDetails(ApplicationConstants.GSON.fromJson(partner.getVehicleDetails(), VehicleViewDto.class));
            partnerDto.setDutyDetails(ApplicationConstants.GSON.fromJson(partner.getDutyDetails(), PartnerDutyDto.class));
            partnerDto.setBookingDetails(ApplicationConstants.GSON.fromJson(partner.getBookingDetails(), BookingViewDto.class));
            partnerDto.setLocationDetails(ApplicationConstants.GSON.fromJson(partner.getLocationDetails(), LocationDto.class));
            partnerDto.setPartnerDetails(PartnerViewDto.fromDao(partner));
            result.getElements().add(partnerDto);
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<PartnerViewDto> getPartners() {

        PaginatedResponseDto<PartnerViewDto> result = PaginatedResponseDto.<PartnerViewDto>builder().elements(new ArrayList<>()).build();
        List<PartnerDao> partners = partnerRepository.findAllActive();
        return getPartnerViewDtoPaginatedResponseDto(partners, result);
    }

    private PaginatedResponseDto<PartnerViewDto> getPartnerViewDtoPaginatedResponseDto(List<PartnerDao> partners, PaginatedResponseDto<PartnerViewDto> result) {
        if (CollectionUtils.isEmpty(partners))
            return result;
        for (PartnerDao partner : partners) {
            PartnerViewDto partnerViewDto = ApplicationConstants.GSON.fromJson(partner.getViewInfo(), PartnerViewDto.class);
            if (partnerViewDto == null)
                continue;
            partnerViewDto.setUuid(partner.getUuid());
            result.getElements().add(partnerViewDto);
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<PartnerViewDto> getPartners(String onboardingStatus) {
        PaginatedResponseDto<PartnerViewDto> result = PaginatedResponseDto.<PartnerViewDto>builder().elements(new ArrayList<>()).build();
        List<PartnerDao> partners = partnerRepository.findAllByOnboardingStatus(onboardingStatus);
        return getPartnerViewDtoPaginatedResponseDto(partners, result);
    }


    @Override
    public PartnerDto updatePartner(String partnerUUID, PartnerActionDto actionDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        switch (actionDto.getAction()) {
            case DEBOARD -> {
                partner = updatePartnerOnboardingStatus(partner, PartnerOnboardingStatus.DEBOARDED);
            }
            case SUSPEND -> {
                partner = updatePartnerOnboardingStatus(partner, PartnerOnboardingStatus.SUSPENDED);
            }

            case CHECK_IN -> {
                partner = checkin(partner, actionDto);
            }
            case UNASSIGN -> {
                partner = unassign(partner, actionDto);
            }
            case CHANGE_VEHICLE -> {
                partner = changeVehicle(partner, actionDto);
            }
            case ASSIGN_VEHICLE -> {
                partner = assignVehicle(partner, actionDto);
            }
            case SELECT_VEHICLE -> {
                partner = selectVehicle(partner, actionDto);
            }
            case RETURN_VEHICLE -> {
                partner = returnVehicle(partner, actionDto);
            }
            case SUBMIT_CHECKLIST -> {
                partner = submitChecklist(partner, actionDto);
            }
            case GO_ONLINE -> {
                partner = goOnline(partner, actionDto);
            }
            case PAUSE -> {
                partner = pause(partner, actionDto);
            }
            case UNPAUSE -> {
                partner = unPause(partner, actionDto);
            }
            case ENROUTE -> {
                partner = enroute(partner, actionDto);
            }
            case ARRIVE -> {
                partner = arrive(partner, actionDto);
            }
            case START -> {
                partner = start(partner, actionDto);
            }
            case END -> {
                partner = end(partner, actionDto);
            }
            case COMPLETE -> {
                partner = complete(partner, actionDto);
            }
            case GO_OFFLINE -> {
                partner = goOffline(partner, actionDto);
            }
            case CHECK_OUT -> {
                partner = checkOut(partner, actionDto);
            }
        }

        return PartnerDto.fromDao(partner);
    }

    private PartnerDao changeVehicle(PartnerDao partner, PartnerActionDto actionDto) {
        VehicleDao vehicle = vehicleRepository.findByUUID(actionDto.getVehicleUUID());
        if (vehicle == null)
            throw new ResponseException("Vehicle no present for id : " + actionDto.getVehicleUUID());

        PartnerDao existingPartner = partnerRepository.findByVehicleId(vehicle.getId());

        if (existingPartner != null && !existingPartner.getId().equals(partner.getId()))
            throw new ResponseException("Vehicle is already assigned to other partner.");

        partner = unassign(partner, actionDto);
        return assignVehicle(partner, actionDto);
    }

    private PartnerDao assignVehicle(PartnerDao partnerDao, PartnerActionDto actionDto) {


        if (!(PartnerStatus.ON_DUTY.name().equals(partnerDao.getStatus()) && PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name().equals(partnerDao.getSubStatus()))) {
            throw new ResponseException("Vehicle can only be assigned in on duty state");
        }
        VehicleDao vehicle = vehicleRepository.findByUUID(actionDto.getVehicleUUID());
        if (vehicle == null)
            throw new ResponseException("Vehicle no present for id : " + actionDto.getVehicleUUID());

        PartnerDao existingPartner = partnerRepository.findByVehicleId(vehicle.getId());

        if (existingPartner != null)
            throw new ResponseException("Vehicle is already assigned to other partner.");

        partnerDao.setStatus(PartnerStatus.ON_DUTY.name());
        partnerDao.setSubStatus(PartnerSubStatus.VEHICLE_ALLOTTED.name());
        partnerDao.setVehicleId(vehicle.getId());
        partnerDao.setVehicleDetails(ApplicationConstants.GSON.toJson(VehicleViewDto.fromDao(vehicle)));
        return partnerRepository.update(partnerDao);

    }

    private PartnerDao unassign(PartnerDao partnerDao, PartnerActionDto actionDto) {

        if (partnerDao.getVehicleId() == null)
            throw new ResponseException("No vehicle assigned");

        partnerDao.setStatus(PartnerStatus.ON_DUTY.name());
        partnerDao.setSubStatus(PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name());
        partnerDao.setVehicleId(null);
        partnerDao.setVehicleDetails(null);
        return partnerRepository.update(partnerDao);
    }


    private PartnerDao checkOut(PartnerDao partner, PartnerActionDto actionDto) {
        PartnerDutyDao currentDuty = partnerDutyRepository.findById(partner.getPartnerDutyId());
        if (currentDuty != null) {
            currentDuty.setStatus(PartnerDutyStatus.COMPLETED.name());
            currentDuty.setActualDutyEndTime(DateTime.now());
            partnerDutyRepository.update(currentDuty);
        }

        partner.setStatus(PartnerStatus.OFF_DUTY.name());
        partner.setSubStatus(PartnerSubStatus.NO_DUTY.name());
        partner.setPartnerDutyId(null);
        partner.setVehicleId(null);
        partner.setVehicleDetails(null);
        if (partner.getPartnerShiftId() == null)
            partner.setDutyDetails(null);

        partner = partnerRepository.update(partner);

        return partner;
    }

    private PartnerDao goOffline(PartnerDao partner, PartnerActionDto actionDto) {

        partner.setStatus(PartnerStatus.VEHICLE_ASSIGNED.name());
        partner.setSubStatus(PartnerSubStatus.WAITING_FOR_ONLINE.name());
        partner = partnerRepository.update(partner);
        return partner;
    }

    private PartnerDao complete(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ONLINE.name());
        partner.setSubStatus(PartnerSubStatus.NO_BOOKING.name());
        partner = partnerRepository.update(partner);
        if (partner.getBookingId() != null) {
            BookingDao bookingDao = bookingRepository.findById(partner.getBookingId());
            bookingDao.setStatus(BookingStatus.COMPLETED.name());
            bookingDao.setSubStatus(BookingSubStatus.COMPLETED.name());
            bookingRepository.update(bookingDao);
        }
        return partner;
    }

    private PartnerDao end(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ONLINE.name());
        partner.setSubStatus(PartnerSubStatus.NO_BOOKING.name());
        partner = partnerRepository.update(partner);
        if (partner.getBookingId() != null) {
            BookingDao bookingDao = bookingRepository.findById(partner.getBookingId());
            bookingDao.setStatus(BookingStatus.COMPLETED.name());
            bookingDao.setSubStatus(BookingSubStatus.ENDED.name());
            bookingRepository.update(bookingDao);
        }
        return partner;
    }

    private PartnerDao start(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ON_BOOKING.name());
        partner.setSubStatus(PartnerSubStatus.STARTED.name());
        partner = partnerRepository.update(partner);
        if (partner.getBookingId() != null) {
            BookingDao bookingDao = bookingRepository.findById(partner.getBookingId());
            bookingDao.setStatus(BookingStatus.IN_PROGRESS.name());
            bookingDao.setSubStatus(BookingSubStatus.STARTED.name());
            bookingRepository.update(bookingDao);
        }
        return partner;
    }

    private PartnerDao arrive(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ON_BOOKING.name());
        partner.setSubStatus(PartnerSubStatus.ARRIVED.name());
        partner = partnerRepository.update(partner);
        if (partner.getBookingId() != null) {
            BookingDao bookingDao = bookingRepository.findById(partner.getBookingId());
            bookingDao.setStatus(BookingStatus.IN_PROGRESS.name());
            bookingDao.setSubStatus(BookingSubStatus.ARRIVED.name());
            bookingRepository.update(bookingDao);
        }
        return partner;
    }

    private PartnerDao enroute(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ON_BOOKING.name());
        partner.setSubStatus(PartnerSubStatus.ENROUTE.name());
        partner = partnerRepository.update(partner);
        if (partner.getBookingId() != null) {
            BookingDao bookingDao = bookingRepository.findById(partner.getBookingId());
            bookingDao.setStatus(BookingStatus.IN_PROGRESS.name());
            bookingDao.setSubStatus(BookingSubStatus.ENROUTE.name());
            bookingRepository.update(bookingDao);
        }
        return partner;
    }

    private PartnerDao unPause(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ONLINE.name());
        partner.setSubStatus(PartnerSubStatus.NO_BOOKING.name());
        partner = partnerRepository.update(partner);
        return partner;
    }

    private PartnerDao pause(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ONLINE.name());
        partner.setSubStatus(PartnerSubStatus.PAUSE.name());
        partner = partnerRepository.update(partner);
        return partner;
    }

    private PartnerDao goOnline(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.ONLINE.name());
        partner.setSubStatus(PartnerSubStatus.NO_BOOKING.name());
        partner = partnerRepository.update(partner);
        return partner;
    }

    private PartnerDao submitChecklist(PartnerDao partner, PartnerActionDto actionDto) {
        if (PartnerStatus.CHECKLIST.name().equals(partner.getStatus())) {
            partner.setStatus(PartnerStatus.VEHICLE_ASSIGNED.name());
            partner.setSubStatus(PartnerSubStatus.WAITING_FOR_ONLINE.name());
            partner = partnerRepository.update(partner);
        } else if (PartnerStatus.RETURN_CHECKLIST.name().equals(partner.getStatus())) {
            partner.setStatus(PartnerStatus.ON_DUTY.name());
            partner.setSubStatus(PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name());
            partner.setVehicleDetails(null);
            partner.setVehicleId(null);
            partner = partnerRepository.update(partner);
        }
        return partner;
    }

    private PartnerDao selectVehicle(PartnerDao partner, PartnerActionDto actionDto) {
        partner.setStatus(PartnerStatus.VEHICLE_ASSIGNED.name());
        partner.setSubStatus(PartnerSubStatus.WAITING_FOR_ONLINE.name());
        partner = partnerRepository.update(partner);


//        partner.setStatus(PartnerStatus.CHECKLIST.name());
//        partner.setSubStatus(PartnerSubStatus.CHECKLIST_PENDING.name());
//        partner = partnerRepository.update(partner);
        return partner;
    }

    private PartnerDao returnVehicle(PartnerDao partner, PartnerActionDto actionDto) {


        partner.setStatus(PartnerStatus.ON_DUTY.name());
        partner.setSubStatus(PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name());
        partner.setVehicleDetails(null);
        partner.setVehicleId(null);
        partner = partnerRepository.update(partner);

//        partner.setStatus(PartnerStatus.RETURN_CHECKLIST.name());
//        partner.setSubStatus(PartnerSubStatus.CHECKLIST_PENDING.name());
//        partner = partnerRepository.update(partner);
        return partner;
    }

    private PartnerDao checkin(PartnerDao partner, PartnerActionDto actionDto) {

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findById(partner.getPartnerShiftId());
        if (partnerShiftDao == null)
            throw new ResponseException("Invalid action: Shift Details Incorrect");

//        LocationDao expectedInLocation = locationRepository.findById(partnerShiftDao.getInLocationId());
//        if (expectedInLocation == null)
//            throw new ResponseException("Invalid action: Shift Details Incorrect No Location present");

//        validateLocationQr(actionDto.getQrString(),expectedInLocation);
//        validateLocationGps(actionDto.getLocation(),expectedInLocation);

        PartnerDutyDao newDuty = new PartnerDutyDao();

        newDuty.setPartnerId(partner.getId());
        newDuty.setStatus(PartnerDutyStatus.IN_PROGRESS.name());
        newDuty.setPartnerShiftId(partnerShiftDao.getId());

        newDuty.setActualDutyStartTime(DateTime.now());
//        newDuty.setActualDutyStartLocationDetails(ApplicationConstants.GSON.toJson(expectedInLocation));

        newDuty.setPlannedDutyStartTime(partnerShiftDao.getEstimatedStartTime());
        newDuty.setPlannedOnlineTime(partnerShiftDao.getEstimatedOnlineTime());
        newDuty.setPlannedDutyEndTime(partnerShiftDao.getEstimatedEndTime());

        newDuty.setPlannedDutyStartLocationDetails(partnerShiftDao.getInLocationDetails());
        newDuty.setPlannedOnlineLocationDetails(partnerShiftDao.getOnlineLocationDetails());
        newDuty.setPlannedDutyEndLocationDetails(partnerShiftDao.getOutLocationDetails());

        newDuty.setPlannedDutyStartLocationId(partnerShiftDao.getInLocationId());
        newDuty.setPlannedOnlineLocationId(partnerShiftDao.getOnlineLocationId());
        newDuty.setPlannedDutyEndLocationId(partnerShiftDao.getOutLocationId());

        newDuty = partnerDutyRepository.save(newDuty);

        partner.setDutyDetails(ApplicationConstants.GSON.toJson(PartnerDutyDto.fromDao(newDuty, PartnerViewDto.fromDao(partner), partnerShiftDao)));
        partner.setPartnerDutyId(newDuty.getId());
        partner.setStatus(PartnerStatus.ON_DUTY.name());
        partner.setSubStatus(PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name());
        partner = partnerRepository.update(partner);

        return partner;
    }

}
