package com.tesi.client.controllers;

import com.tesi.client.entities.RequestMultipleCoils;
import com.tesi.client.services.WriteMultipleCoils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/writeMultipleCoils")
public class WriteMultipleCoilsController {

    @GetMapping
    public String get(){
        return "writeMultipleCoils Page";
    }

    @PostMapping
    public void writeMultipleCoils(@RequestBody RequestMultipleCoils request) throws IOException { //, int startingAddress, boolean value
        WriteMultipleCoils funct = new WriteMultipleCoils();
        funct.writeMultipleCoils(request);
    }

}
