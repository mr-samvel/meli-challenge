package br.com.meli.technicalchallenge.model.dto.response.shipping;

import br.com.meli.technicalchallenge.model.domain.shipping.Shipping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Shipping information")
public class ShippingInfoDTO {
    @Schema(description = "Whether shipping is free", example = "true")
    private Boolean isFree;

    @Schema(description = "Original shipping cost", example = "15.90")
    private Float cost;

    @Schema(description = "Estimated delivery time in days", example = "3")
    private Integer estimatedDeliveryDays;

//    @Schema(description = "Origin address (store)")
//    private AddressDTO from;

    @Schema(description = "Destination address (user)")
    private AddressDTO to;

    public static ShippingInfoDTO from(Shipping shipping) {
        return ShippingInfoDTO.builder()
                    .isFree(shipping.isFree())
                    .cost(shipping.getCost())
                    .estimatedDeliveryDays(shipping.getEstimatedDeliveryDays())
                    .to(AddressDTO.from(shipping.getTo()))
                    .build();
    }
}