package com.training.cardealership.status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/private/status")
    public String status(){
        return "OK";
    }
}
