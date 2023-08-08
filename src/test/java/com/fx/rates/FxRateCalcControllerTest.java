package com.fx.rates;

import com.fx.rates.dto.CalculateRequest;
import com.fx.rates.dto.CalculateResponse;
import com.fx.rates.dto.ExchangeResponse;
import com.fx.rates.service.impl.FxRateServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FxRateCalcControllerTest {

    @InjectMocks
    private com.fx.rates.controller.FxRateCalcController fxRateCalcController;

    @Mock
    private FxRateServiceImpl fxRateService;

    @Test
    public void testSuccess(){
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

        when(fxRateService.getFromExchange(success)).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<CalculateResponse> response1 = fxRateCalcController.calculate(success);

        Assert.assertTrue(response1.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void test_badRequest(){
        CalculateRequest badRequest = new CalculateRequest();
        badRequest.setAmount(100);
        badRequest.setFromCurrency("PHP");
        badRequest.setToCurrency("");

        when(fxRateService.getFromExchange(badRequest)).thenThrow(HttpClientErrorException.BadRequest.class);

        ResponseEntity<CalculateResponse> response = fxRateCalcController.calculate(badRequest);
    }
}
