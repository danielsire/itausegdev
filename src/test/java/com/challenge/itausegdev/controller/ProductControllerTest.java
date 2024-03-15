package com.challenge.itausegdev.controller;

import com.challenge.itausegdev.exceptions.ProductNotFoundException;
import com.challenge.itausegdev.model.CategoryTypes;
import com.challenge.itausegdev.model.Product;
import com.challenge.itausegdev.service.ProductService;
import com.challenge.itausegdev.service.request.ProductRequest;
import com.challenge.itausegdev.service.response.ProductResponse;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @InjectMocks
    private ProductController controller;

    @MockBean
    private ProductService service;

    private final String resource = "/api/insurance";

    @Test
    public void shouldListAllProductsAndReturnOk() throws Exception {
        List<ProductResponse> expected = List.of(
                getProductResponse("test 1", new BigDecimal("100"), new BigDecimal("105")),
                getProductResponse("test 2", new BigDecimal("100"), new BigDecimal("105"))
                );
        Mockito.when(service.findAll()).thenReturn(expected);

        mvc.perform(get(resource))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void shouldGetOneProductsReturnOk() throws Exception {
        ProductResponse expected = getProductResponse("test 1", new BigDecimal("100"), new BigDecimal("105"));
        Optional<ProductResponse> optExpected = Optional.of(expected);
        Mockito.when(service.findById(expected.getId())).thenReturn(optExpected);

        mvc.perform(get(resource + "/" + expected.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(expected.getNome())))
                .andExpect(jsonPath("$.categoria", is(expected.getCategoria())));
    }

    @Test
    public void shouldAddNewAndReturnOk() throws Exception {
        String nome = "test 1";
        BigDecimal precoBase = new BigDecimal("100");
        ProductResponse expected = getProductResponse(nome, precoBase, new BigDecimal("105"));
        ProductRequest request = getProductRequest(nome, precoBase);
        Mockito.when(service.insert(request)).thenReturn(expected);

        mvc.perform(post(resource)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(new GsonBuilder().create().toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(expected.getNome())))
                .andExpect(jsonPath("$.categoria", is(expected.getCategoria())));
    }

    @Test
    public void shouldUpdateAndReturnOk() throws Exception {
        String nome = "test 1";
        BigDecimal precoBase = new BigDecimal("100");
        ProductResponse expected = getProductResponse(nome, precoBase, new BigDecimal("105"));
        ProductRequest request = getProductRequest(nome, precoBase);
        Mockito.when(service.update(request, expected.getId())).thenReturn(expected);

        mvc.perform(put(resource+ "/" + expected.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(new GsonBuilder().create().toJson(expected)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(expected.getNome())))
                .andExpect(jsonPath("$.categoria", is(expected.getCategoria())));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateProductNotExist() throws Exception {
        String nome = "test 1";
        BigDecimal precoBase = new BigDecimal("100");
        ProductResponse expected = getProductResponse(nome, precoBase, new BigDecimal("105"));
        ProductRequest request = getProductRequest(nome, precoBase);
        Mockito.when(service.update(request, expected.getId())).thenThrow(ProductNotFoundException.class);

        mvc.perform(put(resource+ "/" + expected.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(new GsonBuilder().create().toJson(expected)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAndReturnOk() throws Exception {
        UUID expected = UUID.randomUUID();
        Mockito.doNothing().when(service).deleteById(expected);

        mvc.perform(delete(resource + "/" + expected))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowExceptionWhenDeleteProductIsNotPresentReturnOk() throws Exception {
        UUID expected = UUID.randomUUID();
        Mockito.doThrow(ProductNotFoundException.class).when(service).deleteById(expected);

        mvc.perform(delete(resource + "/" + expected))
                .andExpect(status().isNotFound());
    }

    private ProductResponse getProductResponse(String nome, BigDecimal precoBase, BigDecimal precoTarifado) {
        return new ProductResponse(UUID.randomUUID(), nome, CategoryTypes.AUTO.name(), precoBase, precoTarifado);
    }

    private ProductRequest getProductRequest(String nome, BigDecimal precoBase) {
        return new ProductRequest(nome, CategoryTypes.AUTO.name(), precoBase);
    }


}
