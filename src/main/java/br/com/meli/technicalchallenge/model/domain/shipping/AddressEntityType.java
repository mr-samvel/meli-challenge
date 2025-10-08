package br.com.meli.technicalchallenge.model.domain.shipping;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressEntityType {
    USER(1),
    STORE(2);

    @JsonValue
    private final int value;

    public static AddressEntityType fromValue(int value) {
        for (AddressEntityType type : AddressEntityType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid AddressEntityType value: " + value);
    }
}