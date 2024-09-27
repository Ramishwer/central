package com.goev.central.config;


import com.goev.central.command.targets.BookingTarget;
import com.goev.central.event.ExtendedEventProcessor;
import com.goev.central.event.events.asset.save.AssetSaveEvent;
import com.goev.central.event.events.asset.save.AssetTypeSaveEvent;
import com.goev.central.event.events.asset.update.AssetTypeUpdateEvent;
import com.goev.central.event.events.asset.update.AssetUpdateEvent;
import com.goev.central.event.events.booking.BookingSaveEvent;
import com.goev.central.event.events.booking.BookingUpdateEvent;
import com.goev.central.event.events.customer.save.CustomerDetailSaveEvent;
import com.goev.central.event.events.customer.save.CustomerSaveEvent;
import com.goev.central.event.events.customer.update.CustomerDetailUpdateEvent;
import com.goev.central.event.events.customer.update.CustomerUpdateEvent;
import com.goev.central.event.events.location.save.LocationSaveEvent;
import com.goev.central.event.events.location.update.LocationUpdateEvent;
import com.goev.central.event.events.partner.PartnerOnboardingStatusCheckEvent;
import com.goev.central.event.events.partner.save.*;
import com.goev.central.event.events.partner.update.*;
import com.goev.central.event.events.vehicle.save.*;
import com.goev.central.event.events.vehicle.update.*;
import com.goev.central.event.handlers.asset.save.AssetSaveEventHandler;
import com.goev.central.event.handlers.asset.save.AssetTypeSaveEventHandler;
import com.goev.central.event.handlers.asset.update.AssetTypeUpdateEventHandler;
import com.goev.central.event.handlers.asset.update.AssetUpdateEventHandler;
import com.goev.central.event.handlers.booking.save.BookingSaveEventHandler;
import com.goev.central.event.handlers.booking.update.BookingUpdateEventHandler;
import com.goev.central.event.handlers.customer.save.CustomerDetailSaveEventHandler;
import com.goev.central.event.handlers.customer.save.CustomerSaveEventHandler;
import com.goev.central.event.handlers.customer.update.CustomerDetailUpdateEventHandler;
import com.goev.central.event.handlers.customer.update.CustomerUpdateEventHandler;
import com.goev.central.event.handlers.location.save.LocationSaveEventHandler;
import com.goev.central.event.handlers.location.update.LocationUpdateEventHandler;
import com.goev.central.event.handlers.partner.PartnerOnboardingStatusCheckEventHandler;
import com.goev.central.event.handlers.partner.save.*;
import com.goev.central.event.handlers.partner.update.*;
import com.goev.central.event.handlers.vehicle.save.*;
import com.goev.central.event.handlers.vehicle.update.*;
import com.goev.central.event.targets.CentralTarget;
import com.goev.central.event.targets.CustomerTarget;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.service.EventProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Slf4j
public class EventConfig {

    @Bean
    public EventProcessor getEventProcessor(

            PartnerOnboardingStatusCheckEventHandler partnerOnboardingStatusCheckEventHandler,

            AssetUpdateEventHandler assetUpdateEventHandler,
            AssetTypeUpdateEventHandler assetTypeUpdateEventHandler,
            CustomerUpdateEventHandler customerUpdateEventHandler,
            CustomerDetailUpdateEventHandler customerDetailUpdateEventHandler,
            PartnerUpdateEventHandler partnerUpdateEventHandler,
            PartnerDetailUpdateEventHandler partnerDetailUpdateEventHandler,
            PartnerDutyUpdateEventHandler partnerDutyUpdateEventHandler,
            PartnerShiftUpdateEventHandler partnerShiftUpdateEventHandler,
            PartnerPayoutUpdateEventHandler partnerPayoutUpdateEventHandler,
            PartnerDocumentUpdateEventHandler partnerDocumentUpdateEventHandler,
            PartnerDocumentTypeUpdateEventHandler partnerDocumentTypeUpdateEventHandler,
            VehicleUpdateEventHandler vehicleUpdateEventHandler,
            VehicleDocumentUpdateEventHandler vehicleDocumentUpdateEventHandler,
            VehicleDocumentTypeUpdateEventHandler vehicleDocumentTypeUpdateEventHandler,
            VehicleDetailUpdateEventHandler vehicleDetailUpdateEventHandler,
            VehicleTransferDetailUpdateEventHandler vehicleTransferDetailUpdateEventHandler,
            VehicleAssetTransferDetailUpdateEventHandler vehicleAssetTransferDetailUpdateEventHandler,
            VehicleAssetMappingUpdateEventHandler vehicleAssetMappingUpdateEventHandler,
            BookingUpdateEventHandler bookingUpdateEventHandler,
            LocationUpdateEventHandler locationUpdateEventHandler,


            AssetSaveEventHandler assetSaveEventHandler,
            AssetTypeSaveEventHandler assetTypeSaveEventHandler,
            CustomerSaveEventHandler customerSaveEventHandler,
            CustomerDetailSaveEventHandler customerDetailSaveEventHandler,
            PartnerSaveEventHandler partnerSaveEventHandler,
            PartnerDetailSaveEventHandler partnerDetailSaveEventHandler,
            PartnerDutySaveEventHandler partnerDutySaveEventHandler,
            PartnerPayoutSaveEventHandler partnerPayoutSaveEventHandler,
            PartnerDocumentSaveEventHandler partnerDocumentSaveEventHandler,
            PartnerDocumentTypeSaveEventHandler partnerDocumentTypeSaveEventHandler,
            VehicleSaveEventHandler vehicleSaveEventHandler,
            VehicleDocumentSaveEventHandler vehicleDocumentSaveEventHandler,
            VehicleDocumentTypeSaveEventHandler vehicleDocumentTypeSaveEventHandler,
            VehicleDetailSaveEventHandler vehicleDetailSaveEventHandler,
            VehicleTransferDetailSaveEventHandler vehicleTransferDetailSaveEventHandler,
            VehicleAssetTransferDetailSaveEventHandler vehicleAssetTransferDetailSaveEventHandler,
            VehicleAssetMappingSaveEventHandler vehicleAssetMappingSaveEventHandler,
            BookingSaveEventHandler bookingSaveEventHandler,
            LocationSaveEventHandler locationSaveEventHandler


    ) {
        ExtendedEventProcessor eventProcessor = new ExtendedEventProcessor();

        eventProcessor.registerEvents(new PartnerOnboardingStatusCheckEvent());

        eventProcessor.registerEventHandlers(new PartnerOnboardingStatusCheckEvent(), partnerOnboardingStatusCheckEventHandler);

        eventProcessor.registerEvents(new AssetUpdateEvent());
        eventProcessor.registerEvents(new AssetTypeUpdateEvent());
        eventProcessor.registerEvents(new CustomerUpdateEvent());
        eventProcessor.registerEvents(new CustomerDetailUpdateEvent());
        eventProcessor.registerEvents(new PartnerUpdateEvent());
        eventProcessor.registerEvents(new PartnerDetailUpdateEvent());
        eventProcessor.registerEvents(new PartnerDutyUpdateEvent());
        eventProcessor.registerEvents(new PartnerShiftUpdateEvent());
        eventProcessor.registerEvents(new PartnerPayoutUpdateEvent());
        eventProcessor.registerEvents(new PartnerDocumentUpdateEvent());
        eventProcessor.registerEvents(new PartnerDocumentTypeUpdateEvent());
        eventProcessor.registerEvents(new VehicleUpdateEvent());
        eventProcessor.registerEvents(new VehicleDocumentUpdateEvent());
        eventProcessor.registerEvents(new VehicleDocumentTypeUpdateEvent());
        eventProcessor.registerEvents(new VehicleDetailUpdateEvent());
        eventProcessor.registerEvents(new VehicleTransferDetailUpdateEvent());
        eventProcessor.registerEvents(new VehicleAssetTransferDetailUpdateEvent());
        eventProcessor.registerEvents(new VehicleAssetMappingUpdateEvent());
        eventProcessor.registerEvents(new BookingUpdateEvent());
        eventProcessor.registerEvents(new LocationUpdateEvent());


        eventProcessor.registerEventHandlers(new AssetUpdateEvent(), assetUpdateEventHandler);
        eventProcessor.registerEventHandlers(new AssetTypeUpdateEvent(), assetTypeUpdateEventHandler);
        eventProcessor.registerEventHandlers(new CustomerUpdateEvent(), customerUpdateEventHandler);
        eventProcessor.registerEventHandlers(new CustomerDetailUpdateEvent(), customerDetailUpdateEventHandler);
        eventProcessor.registerEventHandlers(new PartnerUpdateEvent(), partnerUpdateEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDetailUpdateEvent(), partnerDetailUpdateEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDutyUpdateEvent(), partnerDutyUpdateEventHandler);
        eventProcessor.registerEventHandlers(new PartnerShiftUpdateEvent(), partnerShiftUpdateEventHandler);
        eventProcessor.registerEventHandlers(new PartnerPayoutUpdateEvent(), partnerPayoutUpdateEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDocumentUpdateEvent(), partnerDocumentUpdateEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDocumentTypeUpdateEvent(), partnerDocumentTypeUpdateEventHandler);
        eventProcessor.registerEventHandlers(new VehicleUpdateEvent(), vehicleUpdateEventHandler);
        eventProcessor.registerEventHandlers(new VehicleDocumentUpdateEvent(), vehicleDocumentUpdateEventHandler);
        eventProcessor.registerEventHandlers(new VehicleDocumentTypeUpdateEvent(), vehicleDocumentTypeUpdateEventHandler);
        eventProcessor.registerEventHandlers(new VehicleDetailUpdateEvent(), vehicleDetailUpdateEventHandler);
        eventProcessor.registerEventHandlers(new VehicleTransferDetailUpdateEvent(), vehicleTransferDetailUpdateEventHandler);
        eventProcessor.registerEventHandlers(new VehicleAssetTransferDetailUpdateEvent(), vehicleAssetTransferDetailUpdateEventHandler);
        eventProcessor.registerEventHandlers(new VehicleAssetMappingUpdateEvent(), vehicleAssetMappingUpdateEventHandler);
        eventProcessor.registerEventHandlers(new BookingUpdateEvent(), bookingUpdateEventHandler);
        eventProcessor.registerEventHandlers(new LocationUpdateEvent(), locationUpdateEventHandler);


        eventProcessor.registerEvents(new AssetSaveEvent());
        eventProcessor.registerEvents(new AssetTypeSaveEvent());
        eventProcessor.registerEvents(new CustomerSaveEvent());
        eventProcessor.registerEvents(new CustomerDetailSaveEvent());
        eventProcessor.registerEvents(new PartnerSaveEvent());
        eventProcessor.registerEvents(new PartnerDetailSaveEvent());
        eventProcessor.registerEvents(new PartnerDutySaveEvent());
        eventProcessor.registerEvents(new PartnerPayoutSaveEvent());
        eventProcessor.registerEvents(new PartnerDocumentSaveEvent());
        eventProcessor.registerEvents(new PartnerDocumentTypeSaveEvent());
        eventProcessor.registerEvents(new VehicleSaveEvent());
        eventProcessor.registerEvents(new VehicleDocumentSaveEvent());
        eventProcessor.registerEvents(new VehicleDocumentTypeSaveEvent());
        eventProcessor.registerEvents(new VehicleDetailSaveEvent());
        eventProcessor.registerEvents(new VehicleTransferDetailSaveEvent());
        eventProcessor.registerEvents(new VehicleAssetTransferDetailSaveEvent());
        eventProcessor.registerEvents(new VehicleAssetMappingSaveEvent());
        eventProcessor.registerEvents(new BookingSaveEvent());
        eventProcessor.registerEvents(new LocationSaveEvent());

        eventProcessor.registerEventHandlers(new AssetSaveEvent(), assetSaveEventHandler);
        eventProcessor.registerEventHandlers(new AssetTypeSaveEvent(), assetTypeSaveEventHandler);
        eventProcessor.registerEventHandlers(new CustomerSaveEvent(), customerSaveEventHandler);
        eventProcessor.registerEventHandlers(new CustomerDetailSaveEvent(), customerDetailSaveEventHandler);
        eventProcessor.registerEventHandlers(new PartnerSaveEvent(), partnerSaveEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDetailSaveEvent(), partnerDetailSaveEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDutySaveEvent(), partnerDutySaveEventHandler);
        eventProcessor.registerEventHandlers(new PartnerPayoutSaveEvent(), partnerPayoutSaveEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDocumentSaveEvent(), partnerDocumentSaveEventHandler);
        eventProcessor.registerEventHandlers(new PartnerDocumentTypeSaveEvent(), partnerDocumentTypeSaveEventHandler);
        eventProcessor.registerEventHandlers(new VehicleSaveEvent(), vehicleSaveEventHandler);
        eventProcessor.registerEventHandlers(new VehicleDocumentSaveEvent(), vehicleDocumentSaveEventHandler);
        eventProcessor.registerEventHandlers(new VehicleDocumentTypeSaveEvent(), vehicleDocumentTypeSaveEventHandler);
        eventProcessor.registerEventHandlers(new VehicleDetailSaveEvent(), vehicleDetailSaveEventHandler);
        eventProcessor.registerEventHandlers(new VehicleTransferDetailSaveEvent(), vehicleTransferDetailSaveEventHandler);
        eventProcessor.registerEventHandlers(new VehicleAssetTransferDetailSaveEvent(), vehicleAssetTransferDetailSaveEventHandler);
        eventProcessor.registerEventHandlers(new VehicleAssetMappingSaveEvent(), vehicleAssetMappingSaveEventHandler);
        eventProcessor.registerEventHandlers(new BookingSaveEvent(), bookingSaveEventHandler);
        eventProcessor.registerEventHandlers(new LocationSaveEvent(), locationSaveEventHandler);

        eventProcessor.registerTargets(PartnerTarget.getTarget(eventProcessor));
        eventProcessor.registerTargets(CentralTarget.getTarget(eventProcessor));
        eventProcessor.registerTargets(CustomerTarget.getTarget(eventProcessor));
        return eventProcessor;
    }

}
