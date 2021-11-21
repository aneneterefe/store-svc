package com.store.api.util;

import com.store.api.entities.Offer;

public interface OfferCalculatorStrategy {
    Offer calculateOffer(int itemQuantity, double itemPrice);
}
