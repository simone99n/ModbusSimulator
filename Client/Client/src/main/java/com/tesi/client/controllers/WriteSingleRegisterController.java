package com.tesi.client.controllers;

//import com.tesi.client.entities.RequestBase;
import com.tesi.client.entities.RequestSingleCoil;
import com.tesi.client.entities.RequestSingleRegister;
import com.tesi.client.services.WriteSingleRegister;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/writeSingleRegister")
public class WriteSingleRegisterController {

    @GetMapping
    public String get(){
        return "writeSingleRegister Page";
    }

    @PostMapping
    public void writeSingleCoil(@RequestBody RequestSingleRegister request) throws IOException {
        WriteSingleRegister funct = new WriteSingleRegister();
        funct.writeSingleRegister(request);
    }

}
