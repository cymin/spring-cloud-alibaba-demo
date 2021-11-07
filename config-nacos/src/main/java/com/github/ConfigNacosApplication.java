package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * 必须使用名称为bootstrap的配置文件来配置nacos server的地址
 * 一旦nacos注册中心发生了改变，客户端可以立马感知到
 * */
@SpringBootApplication
public class ConfigNacosApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(ConfigNacosApplication.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		// 测试：一旦nacos注册中心发生了改变，客户端可以立马感知到
		while (true) {
			String name = environment.getProperty("user.name");
			String age = environment.getProperty("user.age");
			System.out.println("name:" + name);
			System.out.println("age:" + age);
			// nacos的小bug，需要把namespace为public的配置注释掉，不然线程休眠3秒不起作用，其他的namespace不会有问题
			TimeUnit.SECONDS.sleep(3);
		}
	}

}
