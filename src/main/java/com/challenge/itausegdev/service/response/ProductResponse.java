package com.challenge.itausegdev.service.response;

import com.challenge.itausegdev.model.CategoryTypes;
import com.challenge.itausegdev.model.Product;
import com.challenge.itausegdev.service.request.ProductRequest;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductResponse
{

    @NonNull
    private UUID id;

    @NonNull
    private String nome;

    @NonNull
    private String categoria;

    @NonNull
    private BigDecimal precoBase;

    @NonNull
    private BigDecimal precoTarifado;

    public static ProductResponse toResponse(Product response) {
        return new ProductResponse(response.getId(), response.getNome(), response.getCategoria().name(), response.getPrecoBase(), response.getPrecoTarifado());
    }
}
