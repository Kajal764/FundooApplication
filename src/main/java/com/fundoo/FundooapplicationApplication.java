package com.fundoo;

import com.fundoo.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({FileProperties.class})
@SpringBootApplication
public class FundooapplicationApplication {
    public static void main(String[] args) {
        SpringApplication.run(FundooapplicationApplication.class, args);
    }
}
