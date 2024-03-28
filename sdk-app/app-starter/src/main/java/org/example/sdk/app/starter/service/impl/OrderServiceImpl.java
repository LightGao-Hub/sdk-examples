package org.example.sdk.app.starter.service.impl;

import org.example.sdk.app.starter.config.AppWebConfig;
import org.example.sdk.app.starter.entity.Order;
import org.example.sdk.app.starter.service.OrderService;

import javax.annotation.Resource;

public class OrderServiceImpl implements OrderService {
    @Resource
    private AppWebConfig appWebConfig;
    @Override
    public Order getOrder() {
        return new Order(appWebConfig.getOrderName());
    }
}
