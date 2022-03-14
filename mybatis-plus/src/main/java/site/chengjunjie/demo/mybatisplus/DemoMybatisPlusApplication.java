package site.chengjunjie.demo.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan({"site.chengjunjie.demo.mybatisplus"})
public class DemoMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisPlusApplication.class);
    }
}
