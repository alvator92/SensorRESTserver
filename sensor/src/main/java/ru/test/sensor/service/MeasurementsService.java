package ru.test.sensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.sensor.model.Measurement;
import ru.test.sensor.model.Sensor;
import ru.test.sensor.repositories.MeasurementsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public Measurement findById(int id) {
        Optional<Measurement> optional = measurementsRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Measurement> findByOwner(Sensor owner) {
        Optional<List<Measurement>> optional = Optional.ofNullable((measurementsRepository.findByOwner(owner)));
        return optional.orElse(null);
    }

    @Transactional
    public void save(Measurement measurement) {
        measurementsRepository.save(measurement);
    }

}
