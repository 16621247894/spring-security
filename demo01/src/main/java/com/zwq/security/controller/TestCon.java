package com.zwq.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCon {

    @GetMapping("test")
    public String test(){
        System.out.println("金UR");
        return "test";
    }

}
