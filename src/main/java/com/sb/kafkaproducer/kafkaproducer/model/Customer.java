package com.sb.kafkaproducer.kafkaproducer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  private String customerId;
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String state;
  private String zipcode;
}