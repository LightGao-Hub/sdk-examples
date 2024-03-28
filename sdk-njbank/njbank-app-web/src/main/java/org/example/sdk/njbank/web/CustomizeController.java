package org.example.sdk.njbank.web;

import org.example.sdk.njbank.service.CustomizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomizeController {
    @Autowired
    private CustomizeService customizeService;

    @GetMapping("/customize")
    public String customize() {
        return customizeService.customize();
    }
}
