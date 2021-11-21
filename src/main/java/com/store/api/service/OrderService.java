package com.store.api.service;

import com.store.api.controller.model.OrderRequest;
import com.store.api.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
   OrderDto createOrder(List<OrderRequest> orderRequest);
   List<OrderDto> getAllOrders();
   Optional<OrderDto> getOrder(Long id);
}
