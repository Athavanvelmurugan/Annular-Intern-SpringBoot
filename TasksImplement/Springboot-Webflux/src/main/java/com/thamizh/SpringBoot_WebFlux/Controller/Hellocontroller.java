package com.thamizh.SpringBoot_WebFlux.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class Hellocontroller {

    @GetMapping
    public Mono<String> hello() {
        return Mono.just("Hello World").delayElement(Duration.ofSeconds(5));
    }

    @GetMapping("/fulx")
    public Flux<String> fulx() {
        Flux<String> flux = Flux.just("one","two","three","four","five","six","seven","eight","nine","ten").delayElements(Duration.ofSeconds(2));
        return flux;
    }
}
