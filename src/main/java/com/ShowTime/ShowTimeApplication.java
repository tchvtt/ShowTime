package com.ShowTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
public class ShowTimeApplication {
    public static void main(String[] args) {
      SpringApplication.run(ShowTimeApplication.class, args);
    }

    @GetMapping("/ShowTime")
    public String showtime() {
      return String.format("It's ShowTime !");
    }
}
