package com.espresso.productservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.espresso.productservice.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

  @Container static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  void shouldCreateProduct() throws Exception {
    ProductRequest productRequest = this.getProductRequest();
    String productRequestString = this.objectMapper.writeValueAsString(productRequest);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString))
        .andExpect(status().isCreated());
  }

  private ProductRequest getProductRequest() {
    return ProductRequest.builder()
        .name("Iphone 13")
        .description("iphone 13")
        .price(BigDecimal.valueOf(1200))
        .build();
  }
}
