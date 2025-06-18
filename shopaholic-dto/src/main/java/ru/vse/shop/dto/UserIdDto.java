package ru.vse.shop.dto;

import jakarta.validation.constraints.NotEmpty;

public class UserIdDto {
    @NotEmpty
    private String value;

    public String getValue() {
        return value;
    }

    public UserIdDto setValue(String value) {
        this.value = value;
        return this;
    }
}
