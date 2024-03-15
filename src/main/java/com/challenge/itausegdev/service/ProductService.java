package com.challenge.itausegdev.service;

import com.challenge.itausegdev.component.Fares;
import com.challenge.itausegdev.exceptions.ProductNotFoundException;
import com.challenge.itausegdev.model.CategoryTypes;
import com.challenge.itausegdev.model.Product;
import com.challenge.itausegdev.repository.ProductRepository;
import com.challenge.itausegdev.service.request.ProductRequest;
import com.challenge.itausegdev.service.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final Fares fares;

    public ProductService(ProductRepository productRepository, Fares fares) {
        this.productRepository = productRepository;
        this.fares = fares;
    }

    public List<ProductResponse> findAll() {
        return Optional.of(productRepository.findAll())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(ProductResponse::toResponse).toList();
    }

    public Optional<ProductResponse> findById(UUID id) {
        return productRepository.findById(id).map(ProductResponse::toResponse);
    }

    public ProductResponse insert(ProductRequest request) {
        return ProductResponse.toResponse(
                productRepository.save(
                        ProductRequest.toEntity(
                                request,
                                fares.calculateFare(
                                        request.getPrecoBase(),
                                        CategoryTypes.valueOf(request.getCategoria())
                                )
                        )
                )
        );
    }

    public ProductResponse update(ProductRequest request, UUID id) {

        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) {
            throw new ProductNotFoundException();
        }

        requestToEntity(request, product);

        return ProductResponse.toResponse(
                productRepository.save(
                        product.get()
                )
        );
    }

    private void requestToEntity(ProductRequest request, Optional<Product> product) {
        if(product.isPresent()) {
            product.get().setNome(request.getNome());
            product.get().setCategoria(CategoryTypes.valueOf(request.getCategoria()));
            product.get().setPrecoBase(request.getPrecoBase());
            product.get().setPrecoTarifado(
                    fares.calculateFare(
                            request.getPrecoBase(),
                            CategoryTypes.valueOf(request.getCategoria())
                    )
            );
        }
    }

    public void deleteById(UUID id) {
        if(productRepository.findById(id).isEmpty()) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

}
