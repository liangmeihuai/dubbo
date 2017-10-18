//package com.alibaba;
//
//import com.alibaba.meihuai.provider.test.DemoService;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// * Created by ken.lj on 2017/7/31.
// */
//public class Consumer {
//
//    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-demo-consumer.xml");
//        context.start();
//
//        DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
//        String hello = demoService.sayHello("world"); // 执行远程方法
//
//        System.out.println(hello); // 显示调用结果
//    }
//}
