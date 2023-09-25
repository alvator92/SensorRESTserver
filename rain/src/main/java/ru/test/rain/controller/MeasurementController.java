package ru.test.rain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.test.rain.dto.MeasurementDTO;
import ru.test.rain.service.MeasurementService;

import java.util.ArrayList;
import java.util.HashMap;
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

    @GetMapping("/get/limit/{ownerId}/{limit}")
    public ResponseEntity<List<MeasurementDTO>> getLimitMeasurements(@PathVariable("ownerId") int ownerId,
                                                                     @PathVariable("limit") int limit) {

        String url = "http://localhost:8080/measurements/get/limit/{ownerId}/{limit}";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("ownerId", String.valueOf(ownerId));
        uriParam.put("limit", String.valueOf(limit));

        List<MeasurementDTO> response = get(url, uriParam);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{owner}")
    public ResponseEntity<HttpStatus> getMeasurements(@PathVariable("owner") String owner) {

        String url = "http://localhost:8080/measurements/get/by/{owner}";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("owner", owner);


        List<MeasurementDTO> list = get(url, uriParam);

        list.forEach(System.out::println);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private static List<MeasurementDTO> get(String url, Map<String, String> uriParam) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<MeasurementDTO>> rateResponse =
                restTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MeasurementDTO>>() {
                        },
                        uriParam);
        List<MeasurementDTO> list = rateResponse.getBody();

        return list;
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
