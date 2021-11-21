package com.store.api.util;

import com.store.api.entities.OrderItem;

import java.util.List;

public class PriceCalculator {

    private PriceCalculator(){}

    public static double calculateOrderItemPrice(Double itemPrice, Integer quantity){
        return itemPrice * quantity;
    }
    public static double calculateTotalOrderPrice(List<OrderItem> orderItems){
        return orderItems.stream().
                mapToDouble(OrderItem::getPrice).sum();
    }
}
