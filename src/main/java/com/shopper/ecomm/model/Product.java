package com.shopper.ecomm.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 4, max = 50, message = "Product name must be between 4 and 50 characters")
    private String productName;

    @NotBlank
    @NonNull
    @Valid
    private String image;

    @NotBlank
    private String description;

    @PositiveOrZero
    private Integer quantity;

    @PositiveOrZero
    private double price;

    @PositiveOrZero
    private double discount;

    @Positive
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private EcommUser ecommUser;

}
