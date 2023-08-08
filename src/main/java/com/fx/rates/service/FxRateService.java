package com.fx.rates.service;

import com.fx.rates.dto.CalculateRequest;
import com.fx.rates.dto.ExchangeResponse;
import org.springframework.http.ResponseEntity;

public interface FxRateService {
    void validateRequest(CalculateRequest request) throws Exception;

    ResponseEntity<ExchangeResponse> getFromExchange(CalculateRequest request);

    Long conversion(CalculateRequest request , ExchangeResponse exchangeResponse);
}
