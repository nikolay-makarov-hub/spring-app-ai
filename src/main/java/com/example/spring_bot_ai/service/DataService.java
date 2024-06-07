package com.example.spring_bot_ai.service;

import com.example.spring_bot_ai.model.DataCompanyTurnover;
import com.example.spring_bot_ai.repository.DataCompanyTurnoverRepository;
import com.example.spring_bot_ai.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DataService {

    @Autowired
    private DataCompanyTurnoverRepository dataCompanyTurnoverRepository;

    @Autowired
    private CSVHelper csvHelper;

    public void save(MultipartFile file) {
        try {
            List<DataCompanyTurnover> dataCompanyTurnovers = csvHelper.csvToDataCompanyTurnovers(file.getInputStream(), file.getOriginalFilename());

            dataCompanyTurnoverRepository.saveAll(dataCompanyTurnovers);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store csv data: " + e.getMessage());
        }
    }

    public List<DataCompanyTurnover> getAllData() {
        return dataCompanyTurnoverRepository.findAll();
    }

    public void deleteDataById(Long id) {
        dataCompanyTurnoverRepository.deleteById(id);
    }

    public DataCompanyTurnover saveData(DataCompanyTurnover dataCompanyTurnover) {
        return dataCompanyTurnoverRepository.save(dataCompanyTurnover);
    }
}
