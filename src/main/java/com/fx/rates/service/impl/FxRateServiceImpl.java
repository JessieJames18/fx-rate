package com.fx.rates.service.impl;

import com.fx.rates.dto.CalculateRequest;
import com.fx.rates.dto.ExchangeResponse;
import com.fx.rates.service.FxRateService;
import com.fx.rates.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FxRateServiceImpl implements FxRateService {

    public static final Logger LOGGER = LoggerFactory.getLogger(FxRateServiceImpl.class);

    @Value(value = "${exchange.url}")
    private String url;

    RestTemplate restTemplate;

    public FxRateServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public void validateRequest(CalculateRequest request) throws Exception {
        if(StringUtils.isNullOrEmpty(request.getFromCurrency()) ||
                StringUtils.isNullOrEmpty(request.getToCurrency())){
            throw new Exception("Invalid request.");
        }
    }

    @Override
    public ResponseEntity<ExchangeResponse> getFromExchange(CalculateRequest request) {
        return restTemplate.getForEntity(url + "&symbols=" + request.getFromCurrency() + ","
                + request.getToCurrency(), ExchangeResponse.class);
    }

    @Override
    public Long conversion(CalculateRequest request, ExchangeResponse exchangeResponse) {

        return (request.getAmount() * exchangeResponse.getRates().get(request.getToCurrency()))
                / exchangeResponse.getRates().get(request.getFromCurrency());
    }


}
