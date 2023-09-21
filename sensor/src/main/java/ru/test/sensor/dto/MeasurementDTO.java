package ru.test.sensor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;

public class MeasurementDTO {

    private int value;
    @NotEmpty
    private boolean raining;
    @JsonIgnore
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
}
