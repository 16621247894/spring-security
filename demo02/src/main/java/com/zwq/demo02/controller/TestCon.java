package com.zwq.demo02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCon {
    @GetMapping("test")
    public String getInfo(){

        System.out.println("进入了test");
        return "123";
    }
}
