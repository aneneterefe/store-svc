package com.store.api.util;

import com.store.api.constants.OfferType;
import com.store.api.entities.Item;
import com.store.api.entities.Offer;
import com.store.api.entities.OrderItem;

import java.util.List;
import java.util.Objects;

public class PriceCalculator {

    private PriceCalculator(){}

    public static double calculateOrderItemPrice(Double itemPrice, Integer quantity){
        return itemPrice * quantity;
    }
    public static double calculateTotalOrderPrice(List<OrderItem> orderItems){
        return orderItems.stream().
                mapToDouble(OrderItem::getPrice).sum();
    }
    public static double calculateTotalOfferPrice(List<OrderItem> orderItems){
        return orderItems.stream().
                mapToDouble(orderItem ->
                        Objects.nonNull(orderItem.getOffer()) ?
                                orderItem.getOffer().getPrice():
                                orderItem.getPrice())
                .sum();
    }
    public static Offer getOfferPrice(Item item, Integer quantity){
        OfferCalculatorContext offerCalculatorContext;
        if(item.getOfferType() == OfferType.BUY_ONE_GET_ONE){
            offerCalculatorContext = new OfferCalculatorContext(new BuyOneGetOne());
            return offerCalculatorContext.calculateOfferPrice(quantity,item.getPrice());
        } else if(item.getOfferType() == OfferType.THREE_FOR_PRICE_OF_TWO){
            offerCalculatorContext = new OfferCalculatorContext(new ThreeForPriceOfTwo());
            return offerCalculatorContext.calculateOfferPrice(quantity,item.getPrice());
        }
        return null;
    }
}
