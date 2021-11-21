package com.store.api.util;

import com.store.api.entities.Offer;

public class BuyOneGetOne implements OfferCalculatorStrategy {

    @Override
    public Offer calculateOffer(int itemQuantity, double itemPrice) {
         return Offer.builder()
                 .price(itemPrice*itemQuantity)
                 .quantity(itemQuantity*2)
                 .build();
    }
}
