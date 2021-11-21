package com.store.api.controller.model;

import com.store.api.constants.OfferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    private String name;
    private double price;
    private String description;
    private OfferType offerType;
}
