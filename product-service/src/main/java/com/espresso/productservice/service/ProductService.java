package com.espresso.productservice.service;

import com.espresso.productservice.dto.ProductRequest;
import com.espresso.productservice.dto.ProductResponse;
import com.espresso.productservice.model.Product;
import com.espresso.productservice.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;

  public void createProduct(ProductRequest productRequest) {
    Product product =
        Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();
    this.productRepository.save(product);
    log.info("Product {} is saved", product.getId());
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = this.productRepository.findAll();

    return products.stream()
        .map(
            product ->
                ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build())
        .collect(Collectors.toList());
  }
}
