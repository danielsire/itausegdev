package com.challenge.itausegdev.validator;

import com.challenge.itausegdev.model.CategoryTypes;
import com.challenge.itausegdev.service.request.ProductRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CategoryValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldNotReturnError() {
        ProductRequest request = new ProductRequest();
        request.setNome("Test");
        request.setPrecoBase(new BigDecimal("100.00"));
        request.setCategoria(CategoryTypes.AUTO.name());
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldReturnErrorForWrongCategory() {
        ProductRequest request = new ProductRequest();
        request.setNome("Test");
        request.setPrecoBase(new BigDecimal("100.00"));
        request.setCategoria("CARRO");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);
        Assert.assertEquals(violations.size(), 1);
    }
}
