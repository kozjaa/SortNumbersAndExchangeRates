package com.maciej.app.service;

import com.maciej.app.model.ExchangeRatesTable;
import com.maciej.app.model.Rates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatesService {
    @Autowired
    private RestTemplate restTemplate;
    private String url = "http://api.nbp.pl/api/exchangerates/tables/a?format=json";

    public List<ExchangeRatesTable> getExchangeRatesTables(){
        List<ExchangeRatesTable> exchangeRatesTables = Arrays.asList(restTemplate.getForEntity
                (url, ExchangeRatesTable[].class).getBody());
        return exchangeRatesTables;
    }

    public List<Rates> getRatesList(){
        List<List<Rates>> listOfRatesList = getExchangeRatesTables().stream()
                .map(exchangeRatesTable -> exchangeRatesTable.getRates()).collect(Collectors.toList());
        List<Rates> ratesList = listOfRatesList.stream().flatMap(List::stream).collect(Collectors.toList());
        return ratesList;
    }

    public Double getMid(String code){
        Double mid = null;
        for(Rates o : getRatesList()) {
            if(o.getCode().equals(code)) {
                mid = o.getMid(); } }
       return mid;
    }

    public List<String> getAllCodes(){
        List<String> strings = getRatesList().stream().map(rates -> rates.getCode()).collect(Collectors.toList());
        return strings;
    }
}
