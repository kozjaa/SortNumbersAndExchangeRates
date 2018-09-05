package com.maciej.app.controller;

import com.maciej.app.model.Code;
import com.maciej.app.model.Numbers;
import com.maciej.app.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private RatesService ratesService;

    @GetMapping(value = "/status/ping")
    @ResponseStatus(HttpStatus.OK)
    public String pong(){
        return "pong";
    }

    @PostMapping(value = "/numbers/sort-command")
    public ResponseEntity<List<Integer>> sortNumbers(@RequestBody Numbers numbers){
        List<Integer> listOfNumbers = numbers.getNumbers();
        if (listOfNumbers == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (numbers.getOrder().toUpperCase().equals("ASC")){
            Collections.sort(listOfNumbers);
            return new ResponseEntity<List<Integer>>(listOfNumbers, HttpStatus.OK);}
        else if (numbers.getOrder().toUpperCase().equals("DESC")){
            Collections.sort(listOfNumbers);
            Collections.reverse(listOfNumbers);
            return new ResponseEntity<List<Integer>>(listOfNumbers, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/currencies/get-current-currency-value-command")
    public ResponseEntity<Double> getMid(@RequestBody Code code){
        Double mid = ratesService.getMid(code.getCode().toUpperCase());
        List<String> allCodes = ratesService.getAllCodes();
        if (!allCodes.contains(code.getCode())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Double>(mid, HttpStatus.OK);
    }
}
