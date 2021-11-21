package com.store.api.service;

import com.store.api.controller.model.OrderRequest;
import com.store.api.dto.OrderDto;
import com.store.api.entities.Offer;
import com.store.api.entities.Order;
import com.store.api.entities.OrderItem;
import com.store.api.exceptions.GeneralException;
import com.store.api.mappers.OrderDtoMapper;
import com.store.api.repository.OrderRepository;
import com.store.api.util.PriceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;
    private static final String ERROR_MESSAGE = "Exception has occurred while accessing db";

    @Override
    public OrderDto createOrder(List<OrderRequest> orderRequests) {
        try {
            List<OrderItem> orderItems = createOrderItems(orderRequests);
            Order order = Order.builder()
                    .orderDate(LocalDate.now())
                    .orderItems(orderItems)
                    .totalOrderPrice(PriceCalculator.calculateTotalOrderPrice(orderItems))
                    .totalOfferPrice(PriceCalculator.calculateTotalOfferPrice(orderItems))
                    .build();
            return orderDtoMapper.map(orderRepository.save(order), OrderDto.class);
        } catch (Exception exception){
            throw new GeneralException(exception, ERROR_MESSAGE);
        }
    }

    private List<OrderItem> createOrderItems(List<OrderRequest> orderRequests) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderRequest orderRequest : orderRequests) {
            orderItems.add(getOrderItem(orderRequest));
        }
        return orderItems;
    }

    private OrderItem getOrderItem(OrderRequest orderRequest) {
        final Offer offerPrice = PriceCalculator.getOfferPrice(orderRequest.getItem(), orderRequest.getQuantity());
        return OrderItem.builder()
                .item(orderRequest.getItem())
                .price(PriceCalculator.calculateOrderItemPrice(orderRequest.getItem().getPrice(), orderRequest.getQuantity()))
                .quantity(orderRequest.getQuantity())
                .offer(Objects.nonNull(offerPrice) ?
                        Offer.builder()
                                .price(offerPrice.getPrice())
                                .quantity(offerPrice.getQuantity())
                                .build(): null
                        )
                .build();
    }
}
