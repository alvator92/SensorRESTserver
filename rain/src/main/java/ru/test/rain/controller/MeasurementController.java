package ru.test.rain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.test.rain.dto.MeasurementDTO;
import ru.test.rain.service.MeasurementService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/send/{owner}")
    public ResponseEntity<HttpStatus> sendMeasurements(@PathVariable("owner") String owner) {
        measurementService.createMeasurementList(owner);

        String url = "http://localhost:8080/measurements/add";

        List<MeasurementDTO> list = measurementService.createMeasurementList(owner);

        post(url, list);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private String post(String url, List<MeasurementDTO> measurementDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<List<MeasurementDTO>> request = new HttpEntity<>(measurementDTO);
        return restTemplate.postForObject(url, request, String.class);
    }

}
