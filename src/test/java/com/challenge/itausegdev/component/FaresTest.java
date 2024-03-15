package com.challenge.itausegdev.component;

import com.challenge.itausegdev.model.CategoryTypes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("tests")
public class FaresTest {

    @Autowired
    private Fares fares;

    @Test
    public void shouldCalculateFareForTypeVida(){
        BigDecimal precoBase = new BigDecimal("100.00");
        BigDecimal expPrecoBase = new BigDecimal("103.20");
        BigDecimal result = fares.calculateFare(precoBase, CategoryTypes.VIDA);

        Assert.assertEquals(expPrecoBase, result);
    }

    @Test
    public void shouldCalculateFareForTypeAuto(){
        BigDecimal precoBase = new BigDecimal("50.00");
        BigDecimal expPrecoBase = new BigDecimal("55.25");
        BigDecimal result = fares.calculateFare(precoBase, CategoryTypes.AUTO);

        Assert.assertEquals(expPrecoBase, result);
    }

    @Test
    public void shouldCalculateFareForTypeViagem(){
        BigDecimal precoBase = new BigDecimal("500.00");
        BigDecimal expPrecoBase = new BigDecimal("535.00");
        BigDecimal result = fares.calculateFare(precoBase, CategoryTypes.VIAGEM);

        Assert.assertEquals(expPrecoBase, result);
    }

    @Test
    public void shouldCalculateFareForTypeResidencial(){
        BigDecimal precoBase = new BigDecimal("190.00");
        BigDecimal expPrecoBase = new BigDecimal("203.30");
        BigDecimal result = fares.calculateFare(precoBase, CategoryTypes.RESIDENCIAL);

        Assert.assertEquals(expPrecoBase, result);
    }

    @Test
    public void shouldCalculateFareForTypePatrimonial(){
        BigDecimal precoBase = new BigDecimal("1000.00");
        BigDecimal expPrecoBase = new BigDecimal("1080.00");
        BigDecimal result = fares.calculateFare(precoBase, CategoryTypes.PATRIMONIAL);

        Assert.assertEquals(expPrecoBase, result);
    }

}
