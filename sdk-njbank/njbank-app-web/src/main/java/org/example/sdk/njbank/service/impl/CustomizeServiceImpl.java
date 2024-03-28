package org.example.sdk.njbank.service.impl;

import org.example.sdk.njbank.service.CustomizeService;
import org.springframework.stereotype.Service;

@Service
public class CustomizeServiceImpl implements CustomizeService {

    @Override
    public String customize() {
        return "njBankCustomize";
    }
}
