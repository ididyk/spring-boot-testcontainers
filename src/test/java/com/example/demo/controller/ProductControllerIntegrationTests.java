package com.example.demo.controller;

import com.example.demo.AbstractContainerBaseIntegrationTest;
import com.example.demo.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class ProductControllerIntegrationTests extends AbstractContainerBaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private List<Product> products;

    private String asJsonString(Object product) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(product);
    }

    @BeforeEach
    public void beforeAll() {
        this.products = List.of(
                new Product(1L, "test1", "sku1", 1.0),
                new Product(2L, "test2", "sku2", 2.0),
                new Product(3L, "test3", "sku3", 3.0)
        );
    }

    public void afterAll() {
    }

    @Test
    void addNewProductTest() throws Exception {
        //given - setup or precondition
        var mockProduct = products.get(0);

        // when - action
        var requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockProduct))
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

    }
//
//    @Test
//    void addBatchProductsTest() throws Exception {
//
//        // when - action
//        var requestBuilder = MockMvcRequestBuilders
//                .post("/products/batch")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(products))
//                .accept(MediaType.APPLICATION_JSON);
//        ResultActions response = mockMvc.perform(requestBuilder);
//
//        // then - verify the output
////        response.andDo(print());
//        response.andExpect(status().isCreated());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(products.size())));
//    }
//
//    @Test
//    void getAllProductsTest() throws Exception {
//
//        // when - action
//        var requestBuilder = MockMvcRequestBuilders
//                .get("/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON);
//        ResultActions response = mockMvc.perform(requestBuilder);
//
//        // then - verify the output
////        response.andDo(print());
//        response.andExpect(status().isOk());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
//    }

    @Test
    void fullFlowProductsTest() throws Exception {

        // when - action
        var requestBuilder = MockMvcRequestBuilders
                .post("/products/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(products))
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(products.size())));

        // when - action
        var requestBuilder2 = MockMvcRequestBuilders
                .get("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder2)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(products.size())));
    }

}
