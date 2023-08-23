package com.ecommerce.productmanagement.request;

import lombok.Data;

@Data
public class InventoryRequest {

    private int productQuantity;

    private Long productId;
}
