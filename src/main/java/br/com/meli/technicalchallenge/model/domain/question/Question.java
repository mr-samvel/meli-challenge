package br.com.meli.technicalchallenge.model.domain.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private String productId;
    private String userId;
    private String text;
    private LocalDateTime createdAt;
}