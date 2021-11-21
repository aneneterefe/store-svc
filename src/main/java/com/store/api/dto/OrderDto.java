package com.store.api.dto;

import com.store.api.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private long id;
    private LocalDate orderDate;
    private double totalOrderPrice;
    private List<OrderItem> orderItems;
    private Double totalOfferPrice;
}
