package ru.test.sensor.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.sensor.dto.MeasurementDTO;
import ru.test.sensor.dto.SensorDTO;
import ru.test.sensor.model.Measurement;
import ru.test.sensor.model.Sensor;
import ru.test.sensor.service.MeasurementsService;
import ru.test.sensor.service.SensorsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final ModelMapper modelMapper;
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementController(ModelMapper modelMapper, MeasurementsService measurementsService, SensorsService sensorsService) {
        this.modelMapper = modelMapper;
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
    }

    @GetMapping("/test")
    private String test() {
        for (Measurement measurement : measurementsService.findAll())
            System.out.println(measurement);
        return "measurementsService.findAll()";
    }

    @GetMapping("/get/by/{owner}")
    private List<MeasurementDTO> findAll(@PathVariable("owner") String name) {
        Sensor sensor = sensorsService.findByOwnerName(name);
        return sensor.getMeasurements().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public MeasurementDTO getById(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementsService.findById(id));
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
