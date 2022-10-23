package com.sb.kafkaproducer.kafkaproducer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.sb.kafkaproducer.kafkaproducer.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
  private Logger logger = LoggerFactory.getLogger(CustomerService.class);
  private List<Customer> customers;

  @PostConstruct
  private void initCustomers() {
    logger.info("init customers......load 4 customers");
    customers = new ArrayList<Customer>();
    customers.add(new Customer(UUID.randomUUID().toString(), "Krishnan", "Sriram", "6540, Westbury Dy", "Dublin", "OH", "43016"));
    customers.add(new Customer(UUID.randomUUID().toString(), "Indhra", "Vedantham", "6540, Westbury Dy", "Dublin", "OH", "43016"));
    customers.add(new Customer(UUID.randomUUID().toString(), "Avyukth", "Krishnan", "6540, Westbury Dy", "Dublin", "OH", "43016"));
    customers.add(new Customer(UUID.randomUUID().toString(), "Sripratha", "Sriram", "6540, Westbury Dy", "Dublin", "OH", "43016"));
  }

  public Flux<Customer> getAllCustomers() {
    logger.info("List all customers");
    Flux<Customer> fCustomers = Flux.fromIterable(customers);
    return fCustomers;
  }

  public Mono<Customer> findCustomerById(String customerId) {
    Flux<Customer> fCustomers = Flux.fromIterable(customers);
    String errorMessage = "Customer you are looking for - " + customerId + " is not found. Please try again later";
    return fCustomers.filter(c -> c.getCustomerId().equals(customerId))
                      .singleOrEmpty()
                      .switchIfEmpty(Mono.error(new RuntimeException(errorMessage)));
  }

  public Mono<Customer> addCustomer(Customer customer) {
    logger.info("Add new customer - " + customer.getCustomerId());
    customers.add(customer);
    return Mono.just(customer);
  }

}
