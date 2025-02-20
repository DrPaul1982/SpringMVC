package org.example.springmvc.controller;

import org.example.springmvc.model.Order;
import org.example.springmvc.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderRepository orderRepository;

    @Test
    void testGetOrderById() throws Exception {
        Order order = new Order(1L, LocalDate.now(), 500.0, Collections.emptyList());
        when(orderRepository.getOrderById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"totalCost\":500.0,\"products\":[]}"));
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(orderRepository.getAllOrders())
                .thenReturn(Collections.singletonList(new Order(2L, LocalDate.now(), 1500.0, Collections.emptyList())));


        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":2,\"totalCost\":1500.0,\"products\":[]}]"));
    }

    @Test
    void testAddOrder() throws Exception {
        String orderJson = "{\"totalCost\": 500.0, \"products\": []}";

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk());
    }
}