package com.shangan.tradeorder;

import com.shangan.tradeorder.service.LimitBuyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LimitBuyTest {
    @Autowired
    public LimitBuyService limitBuyService;

    @Test
    public void addLimitMemberTest() {
        limitBuyService.addLimitMember(4L, 1999L);
    }

    @Test
    public void isInLimitMemberTest() {
        limitBuyService.isInLimitMember(4L, 1234571L);
    }

    @Test
    public void removeLimitMemberTest() {
        limitBuyService.removeLimitMember(4L, 1234571L);
    }
}