package com.goev.central.utilities;

import com.goev.central.config.SpringContext;
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
import com.goev.central.event.events.partner.save.*;
import com.goev.central.event.events.partner.update.*;
import com.goev.central.event.events.vehicle.save.*;
import com.goev.central.event.events.vehicle.update.*;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.service.EventProcessor;
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
        SpringContext.getBean(EventProcessor.class).sendEvent(eventObj);
        return true;
    }

    public boolean fireEvent(String event, Object data) {

        switch (event) {
            case "PartnerSaveEvent" -> {
                return fireEvent(new PartnerSaveEvent(), (PartnerDao) data);
            }
            case "PartnerUpdateEvent" -> {
                return fireEvent(new PartnerUpdateEvent(), (PartnerDao) data);
            }
            case "VehicleSaveEvent" -> {
                return fireEvent(new VehicleSaveEvent(), (VehicleDao) data);
            }
            case "VehicleUpdateEvent" -> {
                return fireEvent(new VehicleUpdateEvent(), (VehicleDao) data);
            }
            case "BookingSaveEvent" -> {
                return fireEvent(new BookingSaveEvent(),(BookingDao) data);
            }
            case "BookingUpdateEvent" -> {
                return fireEvent(new BookingUpdateEvent(), (BookingDao) data);
            }
            case "PartnerDutySaveEvent" -> {
                return fireEvent(new PartnerDutySaveEvent(),(PartnerDutyDao) data);
            }
            case "PartnerDutyUpdateEvent" -> {
                return fireEvent(new PartnerDutyUpdateEvent(),(PartnerDutyDao) data);
            }
            case "PartnerShiftSaveEvent" -> {
                return fireEvent(new PartnerShiftSaveEvent(),(PartnerShiftDao) data);
            }
            case "PartnerShiftUpdateEvent" -> {
                return fireEvent(new PartnerShiftUpdateEvent(),(PartnerShiftDao) data);
            }
            case "PartnerDocumentTypeSaveEvent" -> {
                return fireEvent(new PartnerDocumentTypeSaveEvent(),(PartnerDocumentTypeDao) data);
            }
            case "PartnerDocumentTypeUpdateEvent" -> {
                return fireEvent(new PartnerDocumentTypeUpdateEvent(),(PartnerDocumentTypeDao) data);
            }
            case "VehicleDocumentTypeSaveEvent" -> {
                return fireEvent(new VehicleDocumentTypeSaveEvent(),(VehicleDocumentTypeDao) data);
            }
            case "VehicleDocumentTypeUpdateEvent" -> {
                return fireEvent(new VehicleDocumentTypeUpdateEvent(),(VehicleDocumentTypeDao) data);
            }
            case "VehicleDocumentSaveEvent" -> {
                return fireEvent(new VehicleDocumentSaveEvent(), (VehicleDocumentDao) data);
            }
            case "VehicleDocumentUpdateEvent" -> {
                return fireEvent(new VehicleDocumentUpdateEvent(),(VehicleDocumentDao) data);
            }
            case "PartnerDocumentSaveEvent" -> {
                return fireEvent(new PartnerDocumentSaveEvent(), (PartnerDocumentDao) data);
            }
            case "PartnerDocumentUpdateEvent" -> {
                return fireEvent(new PartnerDocumentUpdateEvent(), (PartnerDocumentDao) data);
            }
            case "AssetTypeSaveEvent" -> {
                return fireEvent(new AssetTypeSaveEvent(), (AssetTypeDao) data);
            }
            case "AssetTypeUpdateEvent" -> {
                return fireEvent(new AssetTypeUpdateEvent(), (AssetTypeDao) data);
            }
            case "VehicleAssetMappingSaveEvent" -> {
                return fireEvent(new VehicleAssetMappingSaveEvent(), (VehicleAssetMappingDao) data);
            }
            case "VehicleAssetMappingUpdateEvent" -> {
                return fireEvent(new VehicleAssetMappingUpdateEvent(), (VehicleAssetMappingDao) data);
            }
            case "CustomerSaveEvent" -> {
                return fireEvent(new CustomerSaveEvent(), (CustomerDao) data);
            }
            case "CustomerUpdateEvent" -> {
                return fireEvent(new CustomerUpdateEvent(), (CustomerDao) data);
            }
            case "CustomerPaymentSaveEvent" -> {
                return fireEvent(new CustomerPaymentSaveEvent(), (CustomerPaymentDao) data);
            }
            case "CustomerPaymentUpdateEvent" -> {
                return fireEvent(new CustomerPaymentUpdateEvent(), (CustomerPaymentDao) data);
            }
            case "LocationSaveEvent" -> {
                return fireEvent(new LocationSaveEvent(), (LocationDao) data);
            }
            case "LocationUpdateEvent" -> {
                return fireEvent(new LocationUpdateEvent(), (LocationDao) data);
            }
            case "PartnerDetailSaveEvent" -> {
                return fireEvent(new PartnerDetailSaveEvent(), (PartnerDetailDao) data);
            }
            case "PartnerDetailUpdateEvent" -> {
                return fireEvent(new PartnerDetailUpdateEvent(), (PartnerDetailDao) data);
            }
            case "VehicleDetailSaveEvent" -> {
                return fireEvent(new VehicleDetailSaveEvent(), (VehicleDetailDao) data);
            }
            case "VehicleDetailUpdateEvent" -> {
                return fireEvent(new VehicleDetailUpdateEvent(), (VehicleDetailDao) data);
            }


            default -> {
                return false;
            }
        }

    }
}
