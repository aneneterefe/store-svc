package com.store.api.controller.model;

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
}
