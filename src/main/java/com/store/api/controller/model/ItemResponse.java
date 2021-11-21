package com.store.api.controller.model;

import com.store.api.constants.OfferType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private long id;
    private String name;
    private double price;
    private String description;
    private OfferType offerType;
}
