package com.challenge.itausegdev.controller;

import com.challenge.itausegdev.exceptions.ProductNotFoundException;
import com.challenge.itausegdev.service.ProductService;
import com.challenge.itausegdev.service.request.ProductRequest;
import com.challenge.itausegdev.service.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/insurance")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(value = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        Optional<ProductResponse> response = productService.findById(id);
        if (response.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return ResponseEntity.ok(response.get());
    }

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody final ProductRequest request) {
        return ResponseEntity.ok(productService.insert(request));
    }

    @PutMapping(value = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id, @Valid @RequestBody final ProductRequest request) {

        return ResponseEntity.ok(productService.update(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        productService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
