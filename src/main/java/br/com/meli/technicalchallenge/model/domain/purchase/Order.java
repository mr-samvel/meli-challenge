package br.com.meli.technicalchallenge.model.domain.purchase;

import br.com.meli.technicalchallenge.model.domain.shipping.Shipping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String userId;
    private String productId;
    private Integer quantity;
    private Float totalPrice;
    private LocalDateTime purchaseDate;
    private OrderStatus status;
    private Shipping shipping;

    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

    // FIXME move logic from here. a product can be reviewed only 1 time per user
    public boolean canBeReviewed() {
        return isDelivered(); 
    }
}