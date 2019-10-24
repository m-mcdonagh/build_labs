package cerulean.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class BuildLabsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildLabsApplication.class, args);
    }

}
