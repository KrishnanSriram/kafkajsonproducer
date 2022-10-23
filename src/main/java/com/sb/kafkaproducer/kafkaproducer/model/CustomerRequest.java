package com.sb.kafkaproducer.kafkaproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String state;
  private String zipcode;

  public Customer toCustomer(String customerId) {
    return new Customer(customerId, firstName, lastName, street, city, state, zipcode);
  }
}
