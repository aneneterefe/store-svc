package com.store.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.store.api.controller.model.OrderRequest;
import com.store.api.dto.OrderDto;
import com.store.api.entities.Order;
import com.store.api.exceptions.GeneralException;
import com.store.api.mappers.OrderDtoMapper;
import com.store.api.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Spy
    private OrderDtoMapper orderDtoMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Create Order: Success")
    void create_order_success() throws IOException {
        List<OrderRequest> orderRequests = getOrderRequest();
        when(orderRepository.save(Mockito.any())).thenReturn(getOrder());
        OrderDto order = orderService.createOrder(orderRequests);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(orderRequests.size(), order.getOrderItems().size());
        Assertions.assertEquals(
                orderRequests.get(0).getQuantity() * orderRequests.get(0).getItem().getPrice(),
                order.getTotalOrderPrice());
    }

    @Test
    @DisplayName("Create Order: Null Validation")
    void create_order_Exception(){
        final GeneralException generalException = assertThrows(GeneralException.class, () -> orderService.createOrder(null));
        assertEquals("Exception has occurred while accessing db",generalException.getErrorMessage());
    }

    private Order getOrder() throws IOException {
        return objectMapper.readValue(
                new File("src/test/resources/order_response.json"),
                Order.class);
    }

    private List<OrderRequest> getOrderRequest() throws IOException {
        return Arrays.asList(
                objectMapper.readValue(
                        new File("src/test/resources/order_request.json"),
                        OrderRequest[].class));

    }
}