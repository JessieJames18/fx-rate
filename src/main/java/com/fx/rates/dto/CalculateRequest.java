package com.fx.rates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateRequest {
    private String fromCurrency;
    @NotNull(message = "Amount cannot be null")
    private long amount;
    private String toCurrency;
}
