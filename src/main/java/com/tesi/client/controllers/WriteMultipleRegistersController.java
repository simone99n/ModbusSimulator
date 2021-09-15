package com.tesi.client.controllers;

import com.tesi.client.entities.RequestMultipleRegisters;
import com.tesi.client.services.WriteMultipleRegisters;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/writeMultipleRegisters")
public class WriteMultipleRegistersController {

    @GetMapping
    public String get(){
        return "writeMultipleRegisters Page";
    }

    @PostMapping
    public void writeMultipleRegisters(@RequestBody RequestMultipleRegisters request) throws IOException { //, int startingAddress, boolean value
        WriteMultipleRegisters funct = new WriteMultipleRegisters();
        funct.writeMultipleRegisters(request);
    }

}
