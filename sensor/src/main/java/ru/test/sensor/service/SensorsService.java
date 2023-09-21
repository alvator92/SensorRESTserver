package ru.test.sensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.sensor.model.Sensor;
import ru.test.sensor.repositories.SensorsRepository;

import java.util.List;

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

    public Sensor findByOwnerName(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void create(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

}
