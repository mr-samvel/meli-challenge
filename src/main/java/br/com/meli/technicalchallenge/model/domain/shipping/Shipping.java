package br.com.meli.technicalchallenge.model.domain.shipping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Shipping {
    private boolean isFree;
    private Float cost;
    private Integer estimatedDeliveryDays;
    private StoreAddress from;
    private UserAddress to;

    public Shipping(StoreAddress from, UserAddress to) {
        this.from = from;
        this.to = to;
        // Mock logic
        this.isFree = true;
        this.cost = 15.90f;
        this.estimatedDeliveryDays = 3;
    }
}