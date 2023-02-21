package com.example.lab4.Controllers;

import com.example.lab4.Entities.Device;
import com.example.lab4.Services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class FrontEndController {

     DeviceService deviceService;
     RestTemplate restTemplate;

    public void ControllerDevice(DeviceService deviceService){
        this.deviceService = deviceService;
    }
    @GetMapping("/allDevices")
    public String getAllDevices(Model model){
        String url = "http://localhost:8080/devices";
        RestTemplate restTemplate = new RestTemplate();
        Device[] devices = restTemplate.getForObject(url, Device[].class);
        model.addAttribute("devices", devices);
        return "records";
    }
}
