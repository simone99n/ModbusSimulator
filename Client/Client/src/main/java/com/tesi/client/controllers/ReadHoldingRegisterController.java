package com.tesi.client.controllers;

import com.tesi.client.entities.RequestRead;
import com.tesi.client.services.ReadHoldingRegisters;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/readHoldingRegister")
public class ReadHoldingRegisterController {

    @GetMapping
    public int[] get(RequestRead request) throws IOException {
        System.out.println("Richiesta GET ricevuta!");
        ReadHoldingRegisters funct = new ReadHoldingRegisters();
        System.out.println("Creazione Modbus Request...");
        return funct.readHoldingRegisters(request);
    }
}
