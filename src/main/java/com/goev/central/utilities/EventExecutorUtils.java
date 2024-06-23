package com.goev.central.utilities;

import com.goev.central.config.SpringContext;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dao.customer.payment.CustomerPaymentDao;
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
import com.goev.central.event.events.asset.save.AssetTypeSaveEvent;
import com.goev.central.event.events.asset.update.AssetTypeUpdateEvent;
import com.goev.central.event.events.booking.BookingSaveEvent;
import com.goev.central.event.events.booking.BookingUpdateEvent;
import com.goev.central.event.events.customer.save.CustomerPaymentSaveEvent;
import com.goev.central.event.events.customer.save.CustomerSaveEvent;
import com.goev.central.event.events.customer.update.CustomerPaymentUpdateEvent;
import com.goev.central.event.events.customer.update.CustomerUpdateEvent;
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

@Slf4j
@Service
@AllArgsConstructor
public class EventExecutorUtils {

    private static <T> boolean fireEvent(Event<T> eventObj, T data) {
        eventObj.setData(data);
        eventObj.setExecutionTime(DateTime.now().getMillis());
        eventObj.setActionByUUID(ApplicationContext.getAuthUUID());
        SpringContext.getBean(EventProcessor.class).sendEvent(eventObj);
        return true;
    }

    public boolean fireEvent(String event, Object data) {

        switch (event) {
            case "PartnerSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerSaveEvent.class), (PartnerDao) data);
            }
            case "PartnerUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerUpdateEvent.class), (PartnerDao) data);
            }
            case "PartnerOnboardingStatusCheckEvent"->{
                return fireEvent(SpringContext.getBean(PartnerOnboardingStatusCheckEvent.class), (String) data);
            }
            case "VehicleSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleSaveEvent.class), (VehicleDao) data);
            }
            case "VehicleUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleUpdateEvent.class), (VehicleDao) data);
            }
            case "BookingSaveEvent" -> {
                return fireEvent(SpringContext.getBean(BookingSaveEvent.class),(BookingDao) data);
            }
            case "BookingUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(BookingUpdateEvent.class), (BookingDao) data);
            }
            case "PartnerDutySaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDutySaveEvent.class),(PartnerDutyDao) data);
            }
            case "PartnerDutyUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDutyUpdateEvent.class),(PartnerDutyDao) data);
            }
            case "PartnerShiftSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerShiftSaveEvent.class),(PartnerShiftDao) data);
            }
            case "PartnerShiftUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerShiftUpdateEvent.class),(PartnerShiftDao) data);
            }
            case "PartnerDocumentTypeSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentTypeSaveEvent.class),(PartnerDocumentTypeDao) data);
            }
            case "PartnerDocumentTypeUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentTypeUpdateEvent.class),(PartnerDocumentTypeDao) data);
            }
            case "VehicleDocumentTypeSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentTypeSaveEvent.class),(VehicleDocumentTypeDao) data);
            }
            case "VehicleDocumentTypeUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentTypeUpdateEvent.class),(VehicleDocumentTypeDao) data);
            }
            case "VehicleDocumentSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentSaveEvent.class), (VehicleDocumentDao) data);
            }
            case "VehicleDocumentUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDocumentUpdateEvent.class),(VehicleDocumentDao) data);
            }
            case "PartnerDocumentSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentSaveEvent.class), (PartnerDocumentDao) data);
            }
            case "PartnerDocumentUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDocumentUpdateEvent.class), (PartnerDocumentDao) data);
            }
            case "AssetTypeSaveEvent" -> {
                return fireEvent(SpringContext.getBean(AssetTypeSaveEvent.class), (AssetTypeDao) data);
            }
            case "AssetTypeUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(AssetTypeUpdateEvent.class), (AssetTypeDao) data);
            }
            case "VehicleAssetMappingSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleAssetMappingSaveEvent.class), (VehicleAssetMappingDao) data);
            }
            case "VehicleAssetMappingUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleAssetMappingUpdateEvent.class), (VehicleAssetMappingDao) data);
            }
            case "CustomerSaveEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerSaveEvent.class), (CustomerDao) data);
            }
            case "CustomerUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerUpdateEvent.class), (CustomerDao) data);
            }
            case "CustomerPaymentSaveEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerPaymentSaveEvent.class), (CustomerPaymentDao) data);
            }
            case "CustomerPaymentUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(CustomerPaymentUpdateEvent.class), (CustomerPaymentDao) data);
            }
            case "LocationSaveEvent" -> {
                return fireEvent(SpringContext.getBean(LocationSaveEvent.class), (LocationDao) data);
            }
            case "LocationUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(LocationUpdateEvent.class), (LocationDao) data);
            }
            case "PartnerDetailSaveEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDetailSaveEvent.class), (PartnerDetailDao) data);
            }
            case "PartnerDetailUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(PartnerDetailUpdateEvent.class), (PartnerDetailDao) data);
            }
            case "VehicleDetailSaveEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDetailSaveEvent.class), (VehicleDetailDao) data);
            }
            case "VehicleDetailUpdateEvent" -> {
                return fireEvent(SpringContext.getBean(VehicleDetailUpdateEvent.class), (VehicleDetailDao) data);
            }


            default -> {
                return false;
            }
        }

    }
}
