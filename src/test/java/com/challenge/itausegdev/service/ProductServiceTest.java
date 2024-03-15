package com.challenge.itausegdev.service;

import com.challenge.itausegdev.exceptions.ProductNotFoundException;
import com.challenge.itausegdev.model.CategoryTypes;
import com.challenge.itausegdev.model.Product;
import com.challenge.itausegdev.repository.ProductRepository;
import com.challenge.itausegdev.service.request.ProductRequest;
import com.challenge.itausegdev.service.response.ProductResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("tests")
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService service;


    @Before
    public void init() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldReturnProductWhenSaved() {
        BigDecimal precoBase = new BigDecimal("100.00");
        BigDecimal expectedPrecoTarifado = new BigDecimal("110.50");
        CategoryTypes categoria = CategoryTypes.AUTO;
        ProductRequest request = getProductRequest("test save", precoBase);
        Product product = getProduct( "test save", precoBase, expectedPrecoTarifado);

        ProductResponse saved = service.insert(request);

        Assert.assertNotNull(saved);
        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getNome(), product.getNome());
        Assert.assertEquals(saved.getCategoria(), product.getCategoria().name());
        Assert.assertEquals(saved.getPrecoBase(), product.getPrecoBase());
        Assert.assertEquals(saved.getPrecoTarifado(), product.getPrecoTarifado());
    }

    @Test
    public void shouldReturnProductWhenUpdated() {
        BigDecimal precoBase = new BigDecimal("100.00");
        BigDecimal precoBaseUpdated = new BigDecimal("105.00");
        BigDecimal expectedPrecoTarifado = new BigDecimal("116.02");
        ProductRequest request = getProductRequest("test update", precoBaseUpdated);
        Product product = getProduct( "test save", precoBase, expectedPrecoTarifado);

        Product saved = productRepository.save(product);

        ProductResponse updated = service.update(request, saved.getId());

        Assert.assertNotNull(updated);
        Assert.assertNotNull(updated.getId());
        Assert.assertEquals(updated.getNome(), request.getNome());
        Assert.assertEquals(updated.getCategoria(), request.getCategoria());
        Assert.assertEquals(updated.getPrecoBase(), request.getPrecoBase());
        Assert.assertEquals(updated.getPrecoTarifado(), expectedPrecoTarifado);
    }

    @Test
    public void shouldThrowExceptionWhenProductIsNotPresentForUpdate() {
        BigDecimal precoBase = new BigDecimal("100.00");
        ProductRequest request = getProductRequest("test update", precoBase);

        Assert.assertThrows(ProductNotFoundException.class, () -> {
            service.update(request, UUID.randomUUID());
        });

    }

    @Test
    public void shouldReturnProduct() {
        BigDecimal precoBase = new BigDecimal("105.00");
        BigDecimal expectedPrecoTarifado = new BigDecimal("116.02");
        ProductRequest request = getProductRequest("test get", precoBase);
        Product product = getProduct( "test get", precoBase, expectedPrecoTarifado);

        Product saved = productRepository.save(product);

        ProductResponse response = service.findById(saved.getId()).get();

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getId());
        Assert.assertEquals(response.getNome(), request.getNome());
        Assert.assertEquals(response.getCategoria(), request.getCategoria());
        Assert.assertEquals(response.getPrecoBase(), request.getPrecoBase());
        Assert.assertEquals(response.getPrecoTarifado(), expectedPrecoTarifado);
    }

    @Test
    public void shouldReturnProductList() {
        BigDecimal precoBase = new BigDecimal("105.00");
        BigDecimal expectedPrecoTarifado = new BigDecimal("116.02");
        ProductRequest request = getProductRequest("test get", precoBase);
        Product product = getProduct( "test get", precoBase, expectedPrecoTarifado);

        productRepository.save(product);

        List<ProductResponse> response = service.findAll();

        Assert.assertFalse(response.isEmpty());
        ProductResponse first = response.getFirst();
        Assert.assertNotNull(first.getId());
        Assert.assertEquals(first.getNome(), request.getNome());
        Assert.assertEquals(first.getCategoria(), request.getCategoria());
        Assert.assertEquals(first.getPrecoBase(), request.getPrecoBase());
        Assert.assertEquals(first.getPrecoTarifado(), expectedPrecoTarifado);
    }

    @Test
    public void shouldDeleteProduct() {
        BigDecimal precoBase = new BigDecimal("105.00");
        BigDecimal expectedPrecoTarifado = new BigDecimal("116.02");
        ProductRequest request = getProductRequest("test get", precoBase);
        Product product = getProduct( "test get", precoBase, expectedPrecoTarifado);

        Product saved = productRepository.save(product);

        Assert.assertNotNull(saved.getId());

        service.deleteById(saved.getId());

        Optional<ProductResponse> response = service.findById(saved.getId());

        Assert.assertFalse(response.isPresent());
    }

    @Test
    public void shouldThrowExceptionWhenProductIsNotPresentForDelete() {
        Assert.assertThrows(ProductNotFoundException.class, () -> {
            service.deleteById(UUID.randomUUID());
        });

    }

    private Product getProduct(String nome, BigDecimal precoBase, BigDecimal precoTarifado) {
        Product product = new Product(nome, CategoryTypes.AUTO, precoBase, precoTarifado);
        product.setId(UUID.randomUUID());

        return product;
    }

    private ProductRequest getProductRequest(String nome, BigDecimal precoBase) {
        return new ProductRequest(nome, CategoryTypes.AUTO.name(), precoBase);
    }

}
