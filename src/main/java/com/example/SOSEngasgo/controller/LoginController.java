package com.example.SOSEngasgo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/gestao")
    public String validarLogin(){
        return "dashboard";
    }

}
