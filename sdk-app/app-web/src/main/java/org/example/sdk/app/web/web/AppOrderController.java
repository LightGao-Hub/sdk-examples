package org.example.sdk.app.web.web;

import org.example.sdk.app.web.entity.Order;
import org.example.sdk.app.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public Order selectOrder() {
        return orderService.getOrder();
    }
}
