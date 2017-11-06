package com.flame239.dumbaccountservice;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                // Get rid of banner
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
