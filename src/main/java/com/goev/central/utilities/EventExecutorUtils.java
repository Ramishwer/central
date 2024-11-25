package com.goev.central.utilities;

import com.goev.central.config.SpringContext;
import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dao.customer.detail.CustomerDetailDao;
import com.goev.central.dao.customer.payment.CustomerPaymentDao;
import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.earning.PartnerFixedEarningSaveEventDataDao;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.dao.partner.document.PartnerDocumentDao;
import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.vehicle.asset.VehicleAssetMappingDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleDetailDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.dao.vehicle.transfer.VehicleAssetTransferDetailDao;
import com.goev.central.dao.vehicle.transfer.VehicleTransferDetailDao;
import com.goev.central.event.events.asset.save.AssetTypeSaveEvent;
import com.goev.central.event.events.asset.update.AssetTypeUpdateEvent;
import com.goev.central.event.events.booking.BookingSaveEvent;
import com.goev.central.event.events.booking.BookingUpdateEvent;
import com.goev.central.event.events.customer.save.CustomerDetailSaveEvent;
import com.goev.central.event.events.customer.save.CustomerPaymentSaveEvent;
import com.goev.central.event.events.customer.save.CustomerSaveEvent;
import com.goev.central.event.events.customer.update.CustomerDetailUpdateEvent;
import com.goev.central.event.events.customer.update.CustomerPaymentUpdateEvent;
import com.goev.central.event.events.customer.update.CustomerUpdateEvent;
import com.goev.central.event.events.earning.save.EarningRuleSaveEvent;
import com.goev.central.event.events.earning.save.PartnerFixedEarningTransactionSaveEvent;
import com.goev.central.event.events.earning.save.PartnerTotalEarningSaveEvent;
import com.goev.central.event.events.earning.update.EarningRuleUpdateEvent;
import com.goev.central.event.events.location.save.LocationSaveEvent;
import com.goev.central.event.events.location.update.LocationUpdateEvent;
import com.goev.central.event.events.partner.PartnerOnboardingStatusCheckEvent;
import com.goev.central.event.events.partner.save.*;
import com.goev.central.event.events.partner.update.*;
import com.goev.central.event.events.vehicle.save.*;
import com.goev.central.event.events.vehicle.update.*;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.service.EventProcessor;
import com.goev.lib.utilities.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Slf4j
@Service
@AllArgsConstructor
public class EventExecutorUtils {
    private final ExecutorService executorService;

    public boolean fireEvent(String event, Object data) {
        return fireEvent(event, data, DateTime.now());
    }

    private <T> boolean fireEvent(Event<T> eventObj, T data, DateTime executionTime) {
        eventObj.setData(data);
        eventObj.setExecutionTime(executionTime.getMillis());
        eventObj.setActionByUUID(ApplicationContext.getAuthUUID());
        eventObj.setActionByUUID(ApplicationContext.getAuthUUID());
        if (executionTime.isBefore(DateTime.now().plus(1000L))) {
            executorService.submit(() -> SpringContext.getBean(EventProcessor.class).sendEvent(eventObj));
        } else {
            executorService.submit(() -> SpringContext.getBean(EventProcessor.class).scheduleEvent(eventObj));
        }
        return true;
    }

    public boolean fireEvent(String event, Object data, DateTime executionTime) {

        switch (event) {
            case "PartnerSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerSaveEvent.class), (PartnerDao) data, executionTime);
            }
            case "PartnerUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerUpdateEvent.class), (PartnerDao) data, executionTime);
            }
            case "PartnerOnboardingStatusCheckEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerOnboardingStatusCheckEvent.class), (String) data, executionTime);
            }
            case "VehicleSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleSaveEvent.class), (VehicleDao) data, executionTime);
            }
            case "VehicleUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleUpdateEvent.class), (VehicleDao) data, executionTime);
            }
            case "BookingSaveEvent" -> {
                return fireEvent(SpringContext.getBean(BookingSaveEvent.class), (BookingDao) data, executionTime);
            }
            case "BookingUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(BookingUpdateEvent.class), (BookingDao) data, executionTime);
            }
            case "PartnerDutySaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDutySaveEvent.class), (PartnerDutyDao) data, executionTime);
            }
            case "PartnerDutyUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDutyUpdateEvent.class), (PartnerDutyDao) data, executionTime);
            }
            case "PartnerShiftSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerShiftSaveEvent.class), (PartnerShiftDao) data, executionTime);
            }
            case "PartnerShiftUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerShiftUpdateEvent.class), (PartnerShiftDao) data, executionTime);
            }
            case "PartnerDocumentTypeSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentTypeSaveEvent.class), (PartnerDocumentTypeDao) data, executionTime);
            }
            case "PartnerDocumentTypeUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentTypeUpdateEvent.class), (PartnerDocumentTypeDao) data, executionTime);
            }
            case "VehicleDocumentTypeSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentTypeSaveEvent.class), (VehicleDocumentTypeDao) data, executionTime);
            }
            case "VehicleDocumentTypeUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentTypeUpdateEvent.class), (VehicleDocumentTypeDao) data, executionTime);
            }
            case "VehicleDocumentSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentSaveEvent.class), (VehicleDocumentDao) data, executionTime);
            }
            case "VehicleDocumentUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentUpdateEvent.class), (VehicleDocumentDao) data, executionTime);
            }
            case "VehicleTransferDetailSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleTransferDetailSaveEvent.class), (VehicleTransferDetailDao) data, executionTime);
            }
            case "VehicleTransferDetailUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleTransferDetailUpdateEvent.class), (VehicleTransferDetailDao) data, executionTime);
            }
            case "VehicleAssetTransferDetailSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleAssetTransferDetailSaveEvent.class), (VehicleAssetTransferDetailDao) data, executionTime);
            }
            case "VehicleAssetTransferDetailUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleAssetTransferDetailUpdateEvent.class), (VehicleAssetTransferDetailDao) data, executionTime);
            }
            case "PartnerDocumentSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentSaveEvent.class), (PartnerDocumentDao) data, executionTime);
            }
            case "PartnerDocumentUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentUpdateEvent.class), (PartnerDocumentDao) data, executionTime);
            }
            case "AssetTypeSaveEvent" -> {
                return fireEvent(SpringContext.getBean(AssetTypeSaveEvent.class), (AssetTypeDao) data, executionTime);
            }
            case "AssetTypeUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(AssetTypeUpdateEvent.class), (AssetTypeDao) data, executionTime);
            }
            case "VehicleAssetMappingSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleAssetMappingSaveEvent.class), (VehicleAssetMappingDao) data, executionTime);
            }
            case "VehicleAssetMappingUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleAssetMappingUpdateEvent.class), (VehicleAssetMappingDao) data, executionTime);
            }
            case "CustomerSaveEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerSaveEvent.class), (CustomerDao) data, executionTime);
            }
            case "CustomerUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerUpdateEvent.class), (CustomerDao) data, executionTime);
            }
            case "CustomerDetailSaveEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerDetailSaveEvent.class), (CustomerDetailDao) data, executionTime);
            }
            case "CustomerDetailUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerDetailUpdateEvent.class), (CustomerDetailDao) data, executionTime);
            }
            case "CustomerPaymentSaveEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerPaymentSaveEvent.class), (CustomerPaymentDao) data, executionTime);
            }
            case "CustomerPaymentUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerPaymentUpdateEvent.class), (CustomerPaymentDao) data, executionTime);
            }
            case "LocationSaveEvent" -> {
                return fireEvent(SpringContext.getBean(LocationSaveEvent.class), (LocationDao) data, executionTime);
            }
            case "LocationUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(LocationUpdateEvent.class), (LocationDao) data, executionTime);
            }
            case "PartnerDetailSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDetailSaveEvent.class), (PartnerDetailDao) data, executionTime);
            }
            case "PartnerDetailUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDetailUpdateEvent.class), (PartnerDetailDao) data, executionTime);
            }
            case "VehicleDetailSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDetailSaveEvent.class), (VehicleDetailDao) data, executionTime);
            }
            case "VehicleDetailUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDetailUpdateEvent.class), (VehicleDetailDao) data, executionTime);
            }
            case "EarningRuleSaveEvent" -> {
                return fireEvent(SpringContext.getBean(EarningRuleSaveEvent.class), (EarningRuleDao) data, executionTime);
            }
            case "EarningRuleUpdateEvent" ->{
                return fireEvent(SpringContext.getBean(EarningRuleUpdateEvent.class), (EarningRuleDao) data, executionTime);
            }
            case "PartnerFixedEarningTransactionSaveEvent" ->{
                return fireEvent(SpringContext.getBean(PartnerFixedEarningTransactionSaveEvent.class), (PartnerFixedEarningSaveEventDataDao) data, executionTime);
            }
            case "PartnerTotalEarningSaveEvent" ->{
                return fireEvent(SpringContext.getBean(PartnerTotalEarningSaveEvent.class), (PartnerEarningDao) data, executionTime);
            }
            default -> {
                return false;
            }
        }

    }
}
