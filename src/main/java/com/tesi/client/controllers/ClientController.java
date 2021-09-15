package com.tesi.client.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/client")
public class ClientController {

    @GetMapping
    public String hello(){
        return "Tesi di laurea: MODBUS SIMULATOR";
    }

}
