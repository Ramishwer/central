package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerDao;

import java.util.List;

public interface PartnerRepository {
    PartnerDao save(PartnerDao partner);

    PartnerDao update(PartnerDao partner);

    void delete(Integer id);

    PartnerDao findByUUID(String uuid);

    PartnerDao findById(Integer id);

    List<PartnerDao> findAllByIds(List<Integer> ids);

    List<PartnerDao> findAllActive();

    PartnerDao findByPhoneNumber(String phoneNumber);

    List<PartnerDao> findAllByOnboardingStatus(String onboardingStatus);

    List<PartnerDao> findAllByStatus(List<String> status);

    List<PartnerDao> findAllByStatusAndShiftIdNotNull(List<String> status);

    PartnerDao findByVehicleId(Integer vehicleId);

    List<PartnerDao> findAllUnAssignedPartners();
    List<PartnerDao> findAllEligiblePartnersForBusinessSegment(Integer businessSegmentId);

    void updateBookingDetails(Integer partnerId, String bookingDetails);
    void updateStats(Integer partnerId, String stats);
    void updateVehicleDetails(Integer partnerId, String vehicleDetails);
}