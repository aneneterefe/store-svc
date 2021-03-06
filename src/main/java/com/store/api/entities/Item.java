package com.store.api.entities;

import com.store.api.constants.OfferType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private @NotNull String name;

    private @NotNull  double price;

    private String description;

    private @NotNull OfferType offerType;
}
