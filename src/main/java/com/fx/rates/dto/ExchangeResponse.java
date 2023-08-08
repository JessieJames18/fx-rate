package com.fx.rates.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ExchangeResponse {
    private boolean success;
    private long timeStamp;
    private String base;
    private Date date;
    private Map<String, Long> rates;
}