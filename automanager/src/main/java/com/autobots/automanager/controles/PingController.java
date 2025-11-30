package main.java.com.autobots.automanager.controles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/auth/ping")
    public String ping() {
        return "auth-ok";
    }
}