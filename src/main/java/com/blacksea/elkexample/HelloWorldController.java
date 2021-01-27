package com.blacksea.elkexample;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class HelloWorldController {

    @GetMapping
    public void helloWorld() {
        log.info("Hello world");
    }
}
