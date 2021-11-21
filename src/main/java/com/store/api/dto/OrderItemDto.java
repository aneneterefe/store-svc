package com.store.api.dto;

import com.store.api.entities.Item;
import com.store.api.entities.Offer;
import com.store.api.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private long id;
    private int quantity;
    private double price;
    private Order order;
    private Item item;
    private Offer offer;
}
