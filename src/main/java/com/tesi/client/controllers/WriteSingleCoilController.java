package com.tesi.client.controllers;

import com.tesi.client.entities.RequestSingleCoil;
import com.tesi.client.services.WriteSingleCoil;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/writeSingleCoil")
public class WriteSingleCoilController {

    @GetMapping
    public String get(){
        return "writeSingleCoil Page";
    }

    @PostMapping
    public void writeSingleCoil(@RequestBody RequestSingleCoil request) throws IOException { //, int startingAddress, boolean value
        WriteSingleCoil funct = new WriteSingleCoil();
        funct.writeSingleCoil(request);
    }

}
