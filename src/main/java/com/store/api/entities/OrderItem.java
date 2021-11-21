package com.store.api.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private @NotNull int quantity;

    private double price;

    @OneToOne
    @JoinColumn
    private @NotNull Item item;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Offer offer;
}
