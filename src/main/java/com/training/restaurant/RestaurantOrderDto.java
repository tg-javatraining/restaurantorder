package com.training.restaurant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class RestaurantOrderDto {
    private String customerId;
    private String orderDetails;
    private String message;
    private Boolean flag = Boolean.TRUE;


}
