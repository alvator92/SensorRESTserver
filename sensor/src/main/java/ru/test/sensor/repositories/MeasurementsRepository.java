package ru.test.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.test.sensor.model.Measurement;
import ru.test.sensor.model.Sensor;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    List<Measurement> findByOwner(Sensor owner);

    long countByRaining(boolean value);

    @Query(nativeQuery = true,
            value = "SELECT * FROM measurements s ORDER BY s.id ASC LIMIT 10")
    List<Measurement> getLimitMeasurements();

    @Query(nativeQuery = true,
            value = "SELECT * FROM measurements s ORDER BY s.id ASC LIMIT ?1 ")
    Optional<List<Measurement>> findMeasurementsWithLimit(int limit);

    @Query(nativeQuery = true,
            value = "SELECT m.id , m.sensor_id , m.value , m.raining , m.created_at FROM measurements m \n" +
                    "join sensor s on m.sensor_id = s.id \n" +
                    "where m.sensor_id = ?1 \n" +
                    "ORDER BY m.id ASC LIMIT ?2")
    Optional<List<Measurement>> findMeasurementsByOwnerWithLimit(int sensorId, int limit);


//    @Query("select s from measurements s ORDER BY s.id ASC LIMIT = :#{#limit}")
//    List<Measurement> getMeasurement(@Param("limit") int limit);
}
