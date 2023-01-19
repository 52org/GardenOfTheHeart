package com.garden.heart.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/check")
    public String check() {
        return "Garden Of The Heart is running !";
    }

}
