package com.shangan.tradewebmanager.client;


import com.shangan.tradewebmanager.client.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
ContextID 属性使用来制定Feign客户端上下文的，其允许同一个微服务中定义多个‘@FeignClient' 来调用
同一个远程服务但是有不同的配置
保证在Spring应用上下文中Feign客户端的唯一性
 */

@FeignClient(name = "trade-order", contextId = "trade-lightning-deal")
public interface OrderFeignClient {

    @RequestMapping("/order/createOrder")
    Order createOrder(@RequestParam("userId") long userId, @RequestParam("goodsId") long goodsId);

    @RequestMapping("/order/queryOrder")
    Order queryOrder(@RequestParam("orderId") long orderId);

    @RequestMapping("/order/payOrder")
    void payOrder(@RequestParam("orderId") long orderId);

    @RequestMapping("/order/updateOrder")
    boolean updateOrder(@RequestBody Order order);
}
