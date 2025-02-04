package com.github.fernandoteixxeira.exampleapi.service;

import org.springframework.stereotype.Service;

@Service
public class PrinterService {
    public void execute() {
        System.out.println("executing bean");
    }
}
