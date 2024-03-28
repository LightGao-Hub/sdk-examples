package org.example.sdk.app.starter.web;

import org.example.sdk.app.starter.entity.Order;
import org.example.sdk.app.starter.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public Order selectOrderName() {
        return orderService.getOrder();
    }
}
