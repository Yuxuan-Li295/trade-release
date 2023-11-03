package com.shangan.tradeorder;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@ComponentScan(basePackages = {"com.shangan"})
@MapperScan({"com.shangan.tradeorder.db.mappers","com.shangan.tradegoods.db.mappers"})
@SpringBootApplication
public class TradeOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeOrderApplication.class, args);
    }

}
