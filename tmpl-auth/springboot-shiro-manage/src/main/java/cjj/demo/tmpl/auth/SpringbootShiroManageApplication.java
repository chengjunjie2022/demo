package cjj.demo.tmpl.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"road.cjj.*", "cjj.demo.*"})
public class SpringbootShiroManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroManageApplication.class);
    }
}
