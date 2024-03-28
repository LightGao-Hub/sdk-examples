package org.example.sdk.app.web.service.impl;

import org.example.sdk.app.web.entity.Order;
import org.example.sdk.app.web.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${tenant.order.name}")
    private String orderName;
    @Override
    public Order getOrder() {
        return new Order(orderName);
    }
}
