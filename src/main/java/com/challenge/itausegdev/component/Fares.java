package com.challenge.itausegdev.component;

import com.challenge.itausegdev.model.CategoryTypes;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;

import static com.challenge.itausegdev.model.CategoryTypes.VIDA;
import static com.challenge.itausegdev.model.CategoryTypes.AUTO;
import static com.challenge.itausegdev.model.CategoryTypes.VIAGEM;
import static com.challenge.itausegdev.model.CategoryTypes.RESIDENCIAL;
import static com.challenge.itausegdev.model.CategoryTypes.PATRIMONIAL;

import static com.challenge.itausegdev.component.TaxTypes.IOF;
import static com.challenge.itausegdev.component.TaxTypes.PIS;
import static com.challenge.itausegdev.component.TaxTypes.COFINS;

import static java.util.Objects.requireNonNull;

@Component
public class Fares {

    private ImmutableMap<String, ImmutableMap<String, BigDecimal>> taxes = new ImmutableMap.Builder<String, ImmutableMap<String,BigDecimal>>()
            .put(
                    VIDA.name(),
                    new ImmutableMap.Builder<String, BigDecimal>()
                            .put(IOF.name(), new BigDecimal("0.01"))
                            .put(PIS.name(), new BigDecimal("0.022"))
                            .put(COFINS.name(), new BigDecimal("0.00"))
                            .build()
            )
            .put(
                    AUTO.name(),
                    new ImmutableMap.Builder<String, BigDecimal>()
                            .put(IOF.name(), new BigDecimal("0.055"))
                            .put(PIS.name(), new BigDecimal("0.04"))
                            .put(COFINS.name(), new BigDecimal("0.01"))
                            .build()
            )
            .put(
                    VIAGEM.name(),
                    new ImmutableMap.Builder<String, BigDecimal>()
                            .put(IOF.name(), new BigDecimal("0.02"))
                            .put(PIS.name(), new BigDecimal("0.04"))
                            .put(COFINS.name(), new BigDecimal("0.01"))
                            .build()
            )
            .put(
                    RESIDENCIAL.name(),
                    new ImmutableMap.Builder<String, BigDecimal>()
                            .put(IOF.name(), new BigDecimal("0.04"))
                            .put(PIS.name(), new BigDecimal("0.00"))
                            .put(COFINS.name(), new BigDecimal("0.03"))
                            .build()
            )
            .put(
                    PATRIMONIAL.name(),
                    new ImmutableMap.Builder<String, BigDecimal>()
                            .put(IOF.name(), new BigDecimal("0.05"))
                            .put(PIS.name(), new BigDecimal("0.03"))
                            .put(COFINS.name(), new BigDecimal("0.00"))
                            .build()
            )
            .build();

    /**
     *  apply -> f(x) = price + (price x IOF) + (price x PIS) + (price x COFINS)
     * @param price
     * @param category
     * @return taxed price based on the category with the formula
     */
    public BigDecimal calculateFare(BigDecimal price, CategoryTypes category) {

        BigDecimal iofTaxed = price.multiply(requireNonNull(taxes.get(category.name())).get(IOF.name()));

        BigDecimal pisTaxed = price.multiply(requireNonNull(taxes.get(category.name())).get(PIS.name()));

        BigDecimal cofinsTaxed = price.multiply(requireNonNull(taxes.get(category.name())).get(COFINS.name()));

        return price.add(iofTaxed).add(pisTaxed).add(cofinsTaxed).setScale(2, RoundingMode.HALF_EVEN);
    }

}
