package com.jim.spring_core.controller;

import com.jim.spring_core.service.StringTransformService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("string")
@RequiredArgsConstructor
public class StringController {

    private final StringTransformService _stringTransformService;

    @GetMapping("/countWords")
    public int countWords(@RequestParam("value") String input){
        return _stringTransformService.countWords(input);
    }

    @GetMapping("/transform")
    public  String transform(@RequestParam("value") String input){
        return _stringTransformService.transform(input);
    }
}
