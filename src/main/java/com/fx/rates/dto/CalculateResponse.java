package com.fx.rates.dto;

import lombok.Data;

@Data
public class CalculateResponse extends CalculateRequest{

    private long toAmount;
}
