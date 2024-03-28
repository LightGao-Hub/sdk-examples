package org.example.sdk.jsbank.service.impl;

import org.example.sdk.jsbank.service.CustomizeService;
import org.springframework.stereotype.Service;

@Service
public class CustomizeServiceImpl implements CustomizeService {

    @Override
    public String customize() {
        return "jsBankCustomize";
    }
}
