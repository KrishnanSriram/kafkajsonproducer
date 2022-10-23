package com.sb.kafkaproducer.kafkaproducer.handler;

import java.util.UUID;

import com.sb.kafkaproducer.kafkaproducer.service.KafkaCustomerPrdService;
import com.sb.kafkaproducer.kafkaproducer.service.KafkaStrPrdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sb.kafkaproducer.kafkaproducer.model.Customer;
import com.sb.kafkaproducer.kafkaproducer.model.CustomerRequest;
import com.sb.kafkaproducer.kafkaproducer.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {
  private Logger logger = LoggerFactory.getLogger(CustomerHandler.class);

  @Autowired
  private CustomerService customerService;

  @Autowired
  private KafkaStrPrdService stringProducerService;

  @Autowired
  private KafkaCustomerPrdService customerPrdService;

  public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
    logger.info("getAllCustomers");
    Flux<Customer> customers = customerService.getAllCustomers();
    return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(customers, Customer.class);
  }

  public Mono<ServerResponse> findCustomer(ServerRequest request) {
    logger.info("findCustomer");
    String customerId = request.pathVariable("customerId");
    logger.info("findCustomer - " + customerId);
    Mono<Customer> customer = customerService.findCustomerById(customerId);
    return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(customer, Customer.class);
           // .onErrorResume(e -> e)
           //   .flatMap(e -> ServerResponse.status(HttpStatus.NOT_FOUND).body(e, RuntimeException.class));
  }

  public Mono<ServerResponse> addCustomer(ServerRequest request) {
    logger.info("Add a new customer");
    var customerRequestMono = request.bodyToMono(CustomerRequest.class);
    String uuid = UUID.randomUUID().toString();
    logger.info("Create a new customer for - " + uuid);
    return customerRequestMono.map(cr -> cr.toCustomer(uuid))
            .flatMap(c -> {
              Mono<Customer> mc = customerService.addCustomer(c);
//              stringProducerService.sendMessage("Successfully added " + uuid);
              customerPrdService.sendMessage(c);
              return ServerResponse.ok()
                      .contentType(MediaType.APPLICATION_JSON)
                      .body(mc, Customer.class);
            });
  }
}