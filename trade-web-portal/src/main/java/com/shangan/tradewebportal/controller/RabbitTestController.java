package com.shangan.tradewebportal.controller;

import com.shangan.tradeorder.mq.OrderMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class RabbitTestController {

    @Autowired
    private OrderMessageSender orderMessageSender;

    /*
    @RequestParam: 用户可以发送一个自定义消息
     */
    @GetMapping("/sendPayStatusCheckMessage")
    public String sendPayStatusCheckMessage() {
        orderMessageSender.sendOrderCreateMessage("Sending time:" + LocalDateTime.now() + "Content: Delay Queue Test");
        return "Delay message sent!";
    }



}
