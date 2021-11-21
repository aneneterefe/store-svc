package com.store.api.dto;

import com.store.api.constants.OfferType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private long id;
    private String name;
    private double price;
    private String description;
    private OfferType offerType;
}
