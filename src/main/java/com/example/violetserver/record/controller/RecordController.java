package com.example.violetserver.record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RecordController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello!";
    }
}
