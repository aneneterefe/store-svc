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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        List<OrderRequest> orderRequests = getOrderRequest("src/test/resources/order_request.json");
        when(orderRepository.save(Mockito.any())).thenReturn(getOrder("src/test/resources/order_response.json"));
        OrderDto order = orderService.createOrder(orderRequests);
        assertNotNull(order);
        Assertions.assertEquals(orderRequests.size(), order.getOrderItems().size());
        Assertions.assertEquals(
                orderRequests.get(0).getQuantity() * orderRequests.get(0).getItem().getPrice(),
                order.getTotalOrderPrice());
    }

    @Test
    @DisplayName("Create Order: Buy one get one test")
    void create_order_buy_one_get_one() throws IOException {
        List<OrderRequest> orderRequests = getOrderRequest("src/test/resources/order_request_buy_one_get_one.json");
        final double expectedTotalPrice = orderRequests.get(0).getQuantity() * orderRequests.get(0).getItem().getPrice();
        when(orderRepository.save(Mockito.any())).thenReturn(getOrder("src/test/resources/order_response_buy_one_get_one.json"));
        OrderDto order = orderService.createOrder(orderRequests);
        assertNotNull(order);
        assertEquals(orderRequests.size(), order.getOrderItems().size());
        assertEquals(
                expectedTotalPrice,
                order.getTotalOrderPrice());
        assertEquals(
                orderRequests.get(0).getQuantity() * 2,
                order.getOrderItems().get(0).getOffer().getQuantity());
        assertEquals(
                expectedTotalPrice,
                order.getOrderItems().get(0).getOffer().getPrice());
    }

    @Test
    @DisplayName("Create Order: Three for price of two")
    void create__request_three_for_price_of_two() throws IOException {
        List<OrderRequest> orderRequests = getOrderRequest("src/test/resources/order_request_three_for_price_of_two.json");
        final double expectedTotalPrice = orderRequests.get(0).getQuantity() * orderRequests.get(0).getItem().getPrice();
        when(orderRepository.save(Mockito.any())).thenReturn(getOrder("src/test/resources/order_response_three_for_price_of_two.json"));
        OrderDto order = orderService.createOrder(orderRequests);
        assertNotNull(order);
        assertEquals(orderRequests.size(), order.getOrderItems().size());
        assertEquals(
                expectedTotalPrice,
                order.getTotalOrderPrice());
        assertEquals(
                orderRequests.get(0).getQuantity(),
                order.getOrderItems().get(0).getOffer().getQuantity());
        assertTrue(
                expectedTotalPrice >
                order.getOrderItems().get(0).getOffer().getPrice());
        assertEquals(
                4.2,
                order.getTotalOfferPrice());
    }

    @Test
    @DisplayName("Create Order: Null Validation")
    void create_order_Exception(){
        final GeneralException generalException = assertThrows(GeneralException.class, () -> orderService.createOrder(null));
        assertEquals("Exception has occurred while accessing db",generalException.getErrorMessage());
    }

    @Test
    @DisplayName("Get Order: findAll")
    void get_order_success() throws IOException {
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(getOrder("src/test/resources/order_response.json")));
        List<OrderDto> order = orderService.getAllOrders();
        assertNotNull(order);
        assertTrue(order.size()>0);
    }

    @Test
    @DisplayName("Get Order: findById")
    void get_order_by_id() throws IOException {
        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getOrder("src/test/resources/order_response.json")));
        Optional<OrderDto> order = orderService.getOrder(1L);
        assertTrue(order.isPresent());
        assertEquals(1L, order.get().getId());
    }

    @Test
    @DisplayName("Get Order By Id: Not Found")
    void get_order_by_id_not_found() {
        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Optional<OrderDto> order = orderService.getOrder(1L);
        assertFalse(order.isPresent());
    }

    private Order getOrder(String filePath) throws IOException {
        return objectMapper.readValue(
                new File(filePath),
                Order.class);
    }

    private List<OrderRequest> getOrderRequest(String filePath) throws IOException {
        return Arrays.asList(
                objectMapper.readValue(
                        new File(filePath),
                        OrderRequest[].class));

    }
}