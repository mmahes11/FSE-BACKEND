package com.vaccination.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VaccinationController {
        @GetMapping("/home")
        public String home() {
            return "home";
        }
}
