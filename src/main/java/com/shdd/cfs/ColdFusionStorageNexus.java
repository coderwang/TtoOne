package com.shdd.cfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class ColdFusionStorageNexus {

    public static void main(String[] args) {
        SpringApplication.run(ColdFusionStorageNexus.class, args);
    }
}
