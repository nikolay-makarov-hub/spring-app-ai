package com.example.spring_bot_ai.controller;

import com.example.spring_bot_ai.model.DataCompanyTurnover;
import com.example.spring_bot_ai.model.FileDetail;
import com.example.spring_bot_ai.repository.FileDetailRepository;
import com.example.spring_bot_ai.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/datatables")
public class FileDetailController {

    private final FileDetailRepository fileDetailRepository;
    private final DataService dataService;

    @Autowired
    public FileDetailController(FileDetailRepository fileDetailRepository, DataService dataService) {
        this.fileDetailRepository = fileDetailRepository;
        this.dataService = dataService;
    }

    @GetMapping
    public List<FileDetail> getAllFileDetails() {
        return fileDetailRepository.findAll();
    }

    @GetMapping("/1")
    public List<DataCompanyTurnover> getAllData() {
        return dataService.getAllData();
    }

    @DeleteMapping("/1/{id}")
    public void deleteDataById(@PathVariable Long id) {
        dataService.deleteDataById(id);
    }

    @PostMapping("/1")
    public DataCompanyTurnover addData(@RequestBody DataCompanyTurnover dataCompanyTurnover) {
        return dataService.saveData(dataCompanyTurnover);
    }
}
