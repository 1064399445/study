package cn.jun.dubbo.serviceImpl;

import cn.jun.service.model.Hello;
import cn.jun.service.service.DubboProducerService;
import com.alibaba.dubbo.config.annotation.Service;


@Service
public class DubboProducerServiceImpl implements DubboProducerService {

    @Override
    public Hello sayHello() {
        Hello hello = new Hello();
        hello.setName("小花");
        hello.setAge("19");
        hello.setSex("男");
        return hello;
    }
}
