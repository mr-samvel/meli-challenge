package br.com.meli.technicalchallenge.model.domain.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Review {
    private String id;
    private String productId;
    private String userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public Review(String id, String productId, String userId, Integer rating, String comment, LocalDateTime createdAt) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public boolean isPositive() {
        return rating >= 4;
    }

    public boolean isNegative() {
        return rating <= 2;
    }
}