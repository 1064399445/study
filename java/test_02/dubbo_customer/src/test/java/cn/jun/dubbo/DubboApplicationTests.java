package cn.jun.dubbo;

import cn.jun.dubbo.serviceImpl.DubboCustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("cn.jun.dubbo")
public class DubboApplicationTests {

    @Autowired
    DubboCustomerService dubboCustomerService;

    @Test
    public void contextLoads() {
        dubboCustomerService.hello();
    }

}
