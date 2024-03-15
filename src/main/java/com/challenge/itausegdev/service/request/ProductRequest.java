package com.challenge.itausegdev.service.request;

import com.challenge.itausegdev.model.CategoryTypes;
import com.challenge.itausegdev.model.Product;
import com.challenge.itausegdev.validator.CategoryConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public final class ProductRequest
{

    @NonNull
    @NotBlank
    private String nome;

    @NonNull
    @CategoryConstraint
    private String categoria;

    @NonNull
    @NotNull
    private BigDecimal precoBase;

    public static Product toEntity(ProductRequest request, BigDecimal precoTarifado) {
        return new Product(request.nome, CategoryTypes.valueOf(request.categoria), request.precoBase, precoTarifado);
    }

}

