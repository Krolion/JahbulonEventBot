package com.participants_bot_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/central/")
public class CentralController {

    @GetMapping("hello")
    public String hello(){
        return "Hi, i'm a CentralController";
    }

}
