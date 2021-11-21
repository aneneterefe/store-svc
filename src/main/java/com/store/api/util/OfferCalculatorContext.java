package com.store.api.util;

import com.store.api.entities.Offer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OfferCalculatorContext {

    private OfferCalculatorStrategy offerCalculatorStrategy;

    public Offer calculateOfferPrice(int quantity, double price){
        return offerCalculatorStrategy.calculateOffer(quantity,price);
    }
}
