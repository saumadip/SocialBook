package com.hsbc.sb;

import com.hsbc.sb.config.SwaggerConfig;
import com.hsbc.sb.memory.SocialBookInMemoryService;
import com.hsbc.sb.rest.SocialBookRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {SwaggerConfig.class, SocialBookInMemoryService.class, SocialBookRestController.class})
public class SocialBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialBookApplication.class, args);
    }

}
