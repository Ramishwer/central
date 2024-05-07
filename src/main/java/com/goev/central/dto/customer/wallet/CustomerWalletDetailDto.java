package com.goev.central.dto.customer.wallet;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.detail.CustomerDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerWalletDetailDto {

    private String uuid;
    private Integer walletBalance;

}
