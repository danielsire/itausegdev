package com.challenge.itausegdev.service.request;

import com.challenge.itausegdev.model.CategoryTypes;
import com.challenge.itausegdev.model.Product;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public final class ProductRequest
{

    @NonNull
    private String nome;

    @NonNull
    private String categoria;

    @NonNull
    private BigDecimal precoBase;

    public static Product toEntity(ProductRequest request, BigDecimal precoTarifado) {
        return new Product(request.nome, CategoryTypes.valueOf(request.categoria), request.precoBase, precoTarifado);
    }

}

