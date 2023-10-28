package com.shangan.tradewebportal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.shangan"})
@MapperScan({"com.shangan.tradegoods.db.mappers","com.shangan.trade.order.dp.mappers"})
@SpringBootApplication
public class TradeWebPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeWebPortalApplication.class, args);
    }

}
