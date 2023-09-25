package ru.test.rain.service;

import org.springframework.stereotype.Component;
import ru.test.rain.dto.MeasurementDTO;
import ru.test.rain.dto.SensorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MeasurementService {

    public List<MeasurementDTO> createMeasurementList(String owner) {
        List<MeasurementDTO> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(new MeasurementDTO(
                    getRndNumber(),
                    new Random().nextBoolean(),
                    new SensorDTO(owner)
            ));
        }
        return list;
    }

    private int getRndNumber() {
        Random random = new Random();
        int low = -25;
        int high = 42;

        return random.nextInt(high-low) + low;
    }
}
