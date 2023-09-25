package ru.test.sensor.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.test.sensor.dto.MeasurementDTO;
import ru.test.sensor.dto.SensorDTO;
import ru.test.sensor.model.Measurement;
import ru.test.sensor.model.Sensor;
import ru.test.sensor.service.MeasurementsService;
import ru.test.sensor.service.SensorsService;
import ru.test.sensor.utils.SensorErrorResponse;
import ru.test.sensor.utils.SensorNotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @GetMapping("/")
    private List<MeasurementDTO> findAll() {
        return measurementsService.getLimitMeasurements().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/limit/{ownerId}/{limit}")
    public List<MeasurementDTO> findMeasurementsByOwnerWithLimit(@PathVariable("ownerId") int ownerId,
                                                                 @PathVariable("limit") int limit) {

        List<Measurement> responseList = measurementsService.findMeasurementsByOwnerWithLimit(ownerId, limit);
        List<MeasurementDTO> dtos = new ArrayList<>();
        responseList.forEach(measurement -> {
            dtos.add(convertToMeasurementDTO(measurement));
        });

        return dtos;
    }

    @GetMapping("/get/by/{owner}")
    private List<MeasurementDTO> findAllByOwner(@PathVariable("owner") String name) {
        Sensor sensor = sensorsService.findByName(name);
        return sensor.getMeasurements().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public MeasurementDTO getById(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementsService.findById(id));
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount(@RequestParam boolean rain) {
        return measurementsService.countRainingDays(rain);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody List<@Valid MeasurementDTO> measurementDTOs,
                                                     BindingResult bindingResult) {

        System.out.println("measurementDTO :" + measurementDTOs);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }

        List<Measurement> measurements = new ArrayList<>();

        measurementDTOs.stream().forEach(measurementDTO -> {
            Measurement measurement = convertDTOtoMeasurement(measurementDTO);
            measurement.setOwner(sensorsService.findByName(measurement.getOwner().getName()));
            measurements.add(measurement);
        });

        measurementsService.saveAll(measurements);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertDTOtoMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor Not found Exception!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
