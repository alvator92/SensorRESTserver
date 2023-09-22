package ru.test.sensor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MeasurementDTO {

    @Min(value = -100, message = "Температура должна быть в этих пределах -100 : 100")
    @Max(value = 100, message = "Температура должна быть в этих пределах -100 : 100")
    private int value;
    private boolean raining;
//    @JsonIgnore
    private SensorDTO owner;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getOwner() {
        return owner;
    }

    public void setOwner(SensorDTO owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", owner=" + owner +
                '}';
    }
}
