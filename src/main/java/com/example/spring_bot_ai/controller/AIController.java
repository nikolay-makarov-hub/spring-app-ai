package com.example.spring_bot_ai.controller;

import com.example.spring_bot_ai.model.DataCompanyTurnover;
import com.example.spring_bot_ai.model.FilteredData;
import com.example.spring_bot_ai.repository.DataCompanyTurnoverRepository;
import com.example.spring_bot_ai.repository.FilteredDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AIController {

    @Autowired
    private DataCompanyTurnoverRepository dataCompanyTurnoverRepository;

    @Autowired
    private FilteredDataRepository filteredDataRepository;

    private static final String TARGET_URL = "http://80.209.240.170:5000/upload";

    @PostMapping("/predict-sales")
    @Transactional
    public ResponseEntity<List<FilteredData>> processData() {
        List<DataCompanyTurnover> data = dataCompanyTurnoverRepository.findAll();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<List<DataCompanyTurnover>> request = new HttpEntity<>(data, headers);
        ResponseEntity<FilteredData[]> response = restTemplate.postForEntity(TARGET_URL, request, FilteredData[].class);

        if (response.getBody() != null) {
            List<FilteredData> savedData = filteredDataRepository.saveAll(List.of(response.getBody()));
            return ResponseEntity.ok(savedData);  // Возвращаем сохраненные данные в формате JSON
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}