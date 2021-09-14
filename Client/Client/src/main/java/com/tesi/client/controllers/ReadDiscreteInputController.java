package com.tesi.client.controllers;

import com.tesi.client.entities.RequestRead;
import com.tesi.client.services.ReadDiscreteInputs;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/client/readDiscreteInput")
public class ReadDiscreteInputController {

    @GetMapping
    public boolean[] get(RequestRead request) throws IOException {
        ReadDiscreteInputs funct = new ReadDiscreteInputs();
        return  funct.readDiscreteInputs(request);
    }
}
