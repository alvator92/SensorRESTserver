package ru.test.sensor.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.sensor.dto.SensorDTO;
import ru.test.sensor.model.Sensor;
import ru.test.sensor.service.SensorsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorsService sensorsService, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String index() {
        return "Hello World, sensor is coming!";
    }

    @GetMapping("/test")
    private String test() {
        for (Sensor sensor : sensorsService.findAll())
            System.out.println(sensor);
        return "sensorsService.findAll()";
    }

    @GetMapping("/get")
    private List<SensorDTO> getAll() {
        for (Sensor sensor : sensorsService.findAll())
            System.out.println(sensor);
        return sensorsService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
