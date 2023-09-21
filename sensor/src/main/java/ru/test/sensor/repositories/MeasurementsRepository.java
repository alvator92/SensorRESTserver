package ru.test.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.sensor.model.Measurement;
import ru.test.sensor.model.Sensor;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    List<Measurement> findByOwner(Sensor owner);

}
