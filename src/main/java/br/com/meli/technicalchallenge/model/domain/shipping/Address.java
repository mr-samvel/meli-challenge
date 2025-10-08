package br.com.meli.technicalchallenge.model.domain.shipping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Address {
    private String id;
    private String street;
    private String number;
    private String complement;
    private String zipCode;
    private boolean isDefault;
    private String label;
    private String entityId;
    private AddressEntityType entityType;
}