package com.fx.rates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateRequest {
    private String fromCurrency;
    private long amount;
    private String toCurrency;
}
