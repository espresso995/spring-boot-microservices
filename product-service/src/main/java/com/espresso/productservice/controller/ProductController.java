package com.espresso.productservice.controller;

import com.espresso.productservice.dto.ProductRequest;
import com.espresso.productservice.dto.ProductResponse;
import com.espresso.productservice.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(@RequestBody ProductRequest productRequest) {
    this.productService.createProduct(productRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {
    return this.productService.getAllProducts();
  }
}
