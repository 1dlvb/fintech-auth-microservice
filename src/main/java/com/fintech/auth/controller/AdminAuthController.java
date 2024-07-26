package com.fintech.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    @GetMapping("/test1")
    public String adminTest1() {
        return "admin test1";
    }

}




