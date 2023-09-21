package ru.test.sensor.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SensorDTO {

    @NotEmpty(message = "Name should not be Empty")
    private String name;

    private List<MeasurementDTO> measurements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
