package com.fx.rates.controller;


import com.fx.rates.dto.CalculateRequest;
import com.fx.rates.dto.CalculateResponse;
import com.fx.rates.dto.ExchangeResponse;
import com.fx.rates.service.FxRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/fx")
public class FxRateCalcController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FxRateCalcController.class);
    private final FxRateService fxRateService;

    public FxRateCalcController(FxRateService fxRateService) {
        this.fxRateService = fxRateService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalculateResponse> calculate(@RequestBody @Valid CalculateRequest request) {
        LOGGER.info("Request: {}", request);
        try {
            fxRateService.validateRequest(request);
            ResponseEntity<ExchangeResponse> response = fxRateService.getFromExchange(request);

            Long amountTo = fxRateService.conversion(request, response.getBody());

            CalculateResponse response1 = new CalculateResponse();
            response1.setFromCurrency(request.getFromCurrency());
            response1.setAmount(request.getAmount());
            response1.setToCurrency(request.getToCurrency());
            response1.setToAmount(amountTo);
            return ResponseEntity.ok(response1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }


}
