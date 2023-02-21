package com.example.lab4.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String indexPage() {
        return "measureForm";
    }
//    @GetMapping("/display")
//    public String measurementsPage() {
//        return "measurements";
//    }
@GetMapping("/display")
public String devicesPage() {
    return "records";
}
}