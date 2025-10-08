package br.com.meli.technicalchallenge.model.domain.actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime registeredDate;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}