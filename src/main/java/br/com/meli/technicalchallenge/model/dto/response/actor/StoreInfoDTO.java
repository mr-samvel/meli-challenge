package br.com.meli.technicalchallenge.model.dto.response.actor;

import br.com.meli.technicalchallenge.model.domain.actor.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Store information")
public class StoreInfoDTO {
    @Schema(description = "Store id", example = "0199bb55-661f-7f45-af8c-d1b013911a16")
    private String id;

    @Schema(description = "Store name", example = "TechStore Brasil")
    private String name;

    @Schema(description = "Store reputation (0-5)", example = "4.5")
    private Float reputation;

    @Schema(description = "Total sales count", example = "1523")
    private Integer salesCount;

    public static StoreInfoDTO from(Store store) {
        return StoreInfoDTO.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .reputation(store.getReputation())
                    .salesCount(store.getSalesCount())
                    .build();
    }
}