package br.com.meli.technicalchallenge.model.domain.actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private String id;
    private String name;
    private Float reputation;
    private Integer salesCount;
    private LocalDateTime registeredDate;

    public boolean isReliable() {
        return reputation >= 4.0f;
    }

    public boolean isTrustedSeller() {
        return isReliable() && salesCount >= 100;
    }
}