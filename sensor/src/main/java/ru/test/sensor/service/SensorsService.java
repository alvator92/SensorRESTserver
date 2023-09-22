package ru.test.sensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.sensor.model.Sensor;
import ru.test.sensor.repositories.SensorsRepository;
import ru.test.sensor.utils.SensorNotCreatedException;
import ru.test.sensor.utils.SensorNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Sensor findByName(String name) {
        Optional<Sensor> optionalSensor = sensorsRepository.findByName(name);
        return optionalSensor.orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void create(Sensor sensor) {
        enrichSensor(sensor);
        sensorsRepository.save(sensor);
    }

    private void enrichSensor(Sensor sensor) {
        sensor.setCreatedAt(new Date());
    }
}
