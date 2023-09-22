package ru.test.sensor.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.test.sensor.dto.SensorDTO;
import ru.test.sensor.model.Sensor;
import ru.test.sensor.service.SensorsService;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        Optional<Sensor> optionalSensor = Optional.ofNullable(sensorsService.findByName(sensorDTO.getName()));
        if (optionalSensor.isPresent()) {
            errors.rejectValue("name", "", "Такое имя уже есть в БД");
        }
    }
}
