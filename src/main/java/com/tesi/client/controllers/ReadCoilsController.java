package com.tesi.client.controllers;

import com.tesi.client.entities.RequestRead;
import com.tesi.client.services.ReadCoils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/readCoils")
public class ReadCoilsController {

    @GetMapping
    public boolean[] get(RequestRead request) throws IOException {
        System.out.println("prova");
        ReadCoils funct = new ReadCoils();
        return funct.readCoils(request);
    }
}
