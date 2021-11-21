package com.store.api.util;

import com.store.api.entities.Offer;

public class ThreeForPriceOfTwo implements OfferCalculatorStrategy{

    @Override
    public Offer calculateOffer(int itemQuantity, double itemPrice) {
        int tempQuantity = itemQuantity;
        int freeItems = 0;
        while(tempQuantity >= 3){
            freeItems ++;
            tempQuantity= tempQuantity - 3;
        }
        return Offer.builder()
                .price((itemQuantity - freeItems) * itemPrice)
                .quantity(itemQuantity)
                .build();
    }
}
