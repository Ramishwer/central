package com.goev.central.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingInvoicingDetailDto {
    private String uuid;
    private BookingViewDto booking;
    private String invoiceNumber;
    private String invoiceUrl;
    private String receiptUrl;
    private String receiptDetails;
}
