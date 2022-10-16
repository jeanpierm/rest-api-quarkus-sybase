package org.acme.customer;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    public static final String COLUMN_ID = "customer_id";
    public static final String COLUMN_NAME = "name";

    private Integer customerId;

    private String name;
}
