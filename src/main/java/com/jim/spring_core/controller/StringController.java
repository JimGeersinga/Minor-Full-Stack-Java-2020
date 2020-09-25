package com.jim.spring_core.controller;

import com.jim.spring_core.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringController {

    @Autowired
    private StringService _stringService;

    @GetMapping("/countWords")
    public int countWords(@RequestParam(name="value") String value){
        return _stringService.countWords(value);
    }

    @GetMapping("/reverse")
    public  String reverse(@RequestParam(name="value") String value){
        return _stringService.reverse(value);
    }
}
