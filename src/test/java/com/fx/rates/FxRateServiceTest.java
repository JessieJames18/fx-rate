package com.fx.rates;

import com.fx.rates.dto.CalculateRequest;
import com.fx.rates.dto.ExchangeResponse;
import com.fx.rates.service.impl.FxRateServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FxRateServiceTest {

    @InjectMocks
    private FxRateServiceImpl fxRateService;

    @Mock
    RestTemplate restTemplate;

    @Value(value = "${exchange.url}")
    private String url;

    @Test
    public void test_getFromExchange(){
        CalculateRequest success = new CalculateRequest();
        success.setAmount(100);
        success.setFromCurrency("PHP");
        success.setToCurrency("USD");

        Map<String, Long> rates = new HashMap<>();
        rates.put("PHP", 6L);
        rates.put("USD", 1L);

        ExchangeResponse response = new ExchangeResponse();
        response.setRates(rates);
        response.setSuccess(true);
        response.setDate(new Date());

        when(restTemplate.getForEntity(url +"&symbols=" + success.getFromCurrency() + ","
                + success.getToCurrency(), ExchangeResponse.class)).
                thenReturn(ResponseEntity.ok(response));

        ResponseEntity<?> response1 = fxRateService.getFromExchange(success);

        Assert.assertNotNull(response1);
        Assert.assertTrue(response1.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void test_calculate(){
        Map<String, Long> rates = new HashMap<>();
        rates.put("PHP", 60L);
        rates.put("USD", 1L);

        ExchangeResponse response = new ExchangeResponse();
        response.setRates(rates);
        response.setSuccess(true);
        response.setDate(new Date());

        CalculateRequest success = new CalculateRequest();
        success.setAmount(100);
        success.setFromCurrency("PHP");
        success.setToCurrency("USD");

        Long result = fxRateService.conversion(success, response);

        Assert.assertNotNull(result);
    }
}
