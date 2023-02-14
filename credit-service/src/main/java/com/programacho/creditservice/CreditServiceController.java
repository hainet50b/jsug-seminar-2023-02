package com.programacho.creditservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditServiceController {

    @PostMapping("/authorize")
    public String authorize() {
        return "ok";
    }
}
