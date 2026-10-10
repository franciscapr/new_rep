package com.duoc.backend;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {

    @GetMapping(
        value = "/greetings",
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String greetings() {
        return "Hello";
    }
}