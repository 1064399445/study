package cn.jun.dubbo;

import cn.jun.dubbo.serviceImpl.DubboCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("cn.jun.dubbo")
@SpringBootApplication
public class DubboApplication {

    @Autowired
    static DubboCustomerService dubboCustomerService;

    public static void main(String[] args) {
        SpringApplication.run(DubboApplication.class, args);
        dubboCustomerService.hello();
    }

}
