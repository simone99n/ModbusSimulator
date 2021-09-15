package com.tesi.client.controllers;

//import com.tesi.client.entities.RequestBase;
import com.tesi.client.entities.RequestRead;
import com.tesi.client.services.ReadHoldingRegisters;
import com.tesi.client.services.ReadInputRegisters;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/readInputRegister")
public class ReadInputRegisterController {

    @GetMapping
    public int[] get(RequestRead request) throws IOException {
        ReadInputRegisters funct = new ReadInputRegisters();
        return funct.readInputRegisters(request);
    }
}
