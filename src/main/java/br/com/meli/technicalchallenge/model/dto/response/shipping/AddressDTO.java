package br.com.meli.technicalchallenge.model.dto.response.shipping;

import br.com.meli.technicalchallenge.model.domain.shipping.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Address information")
public class AddressDTO {
    @Schema(description = "Street name", example = "Rua Gilson da Costa Xavier")
    private String street;

    @Schema(description = "Street number", example = "1578")
    private String number;

    @Schema(description = "Address complement", example = "Lote 2")
    private String complement;

    @Schema(description = "Zip code", example = "88050-500")
    private String zipCode;

    @Schema(description = "Address label", example = "Casa")
    private String label;

    public static AddressDTO from(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .zipCode(address.getZipCode())
                .label(address.getLabel())
                .build();
    }
}