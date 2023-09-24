package ru.test.rain.dto;

import javax.validation.constraints.NotEmpty;

public class SensorDTO {

    @NotEmpty(message = "Name should not be Empty")
    private String name;

    public SensorDTO(String name) {
        this.name = name;
    }

    public SensorDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
