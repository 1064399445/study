package cn.jun.dubbo.serviceImpl;

import cn.jun.service.model.Hello;
import cn.jun.service.service.DubboProducerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class DubboCustomerService{

    @Reference(url = "dubbo://127.0.0.1:20880")
    DubboProducerService dubboProducerService;

    public void hello() {

        Hello hello = dubboProducerService.sayHello();

        System.out.println("你好呀！我是"+hello.getName()+"！我今年"+hello.getAge()+"岁了！我是"+hello.getSex()+"生哦！");

    }

}
