package com.store.api.service;

import com.store.api.controller.model.OrderRequest;
import com.store.api.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
   OrderDto createOrder(List<OrderRequest> orderRequest);
}
