package com.sb.kafkaproducer.kafkaproducer.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.sb.kafkaproducer.kafkaproducer.handler.CustomerHandler;

@Component
public class CustomerRouter {
  @Autowired
  private CustomerHandler customerHandler;

  @Bean
  public RouterFunction<ServerResponse> appRoutes() {
    return RouterFunctions
                .route(RequestPredicates.GET("/api/customers"), customerHandler::getAllCustomers)
                .andRoute(RequestPredicates.GET("/api/customers/{customerId}"), customerHandler::findCustomer)
                .andRoute(RequestPredicates.POST("/api/customers"), customerHandler::addCustomer);
  }

  @Bean
    public RouterFunction<ServerResponse> orderRoutes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/api/orders"), customerHandler::getAllCustomers);
    }
}
