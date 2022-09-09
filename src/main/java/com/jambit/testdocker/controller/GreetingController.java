package com.jambit.testdocker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting/{name}")
    public String greeting(@PathVariable String name) {
        return "Hello dear " + name + ", this is a useless controller so don't focus on this.";
    }

}
