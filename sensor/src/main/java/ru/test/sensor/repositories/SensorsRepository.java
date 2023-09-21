package ru.test.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.sensor.model.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Sensor findByName(String name);

}
