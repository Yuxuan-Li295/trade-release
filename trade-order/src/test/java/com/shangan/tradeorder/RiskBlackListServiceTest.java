package com.shangan.tradeorder;

import com.shangan.tradeorder.service.RiskBlackListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RiskBlackListServiceTest {

    @Autowired
    private RiskBlackListService riskBlackListService;

    private long testUserId;

    @Before
    public void setUp() {
        testUserId = 123456L; //The choosen test userId
    }

    @Test
    public void addAndCheckRiskBlackListMemberTest() {
        //Add user to the black user list
        riskBlackListService.addRiskBlackListMember(testUserId);
        //Check whether the user is in the black list or not
        boolean isInBlacklist = riskBlackListService.isUserInBlackList(testUserId);
        assertTrue("User should be in the blacklist", isInBlacklist);
    }

    @Test
    public void removeAndCheckRiskBlackListMemberTest() {
        //First ensure the user is in the blacklist
        riskBlackListService.addRiskBlackListMember(testUserId);
        //Then remove it from the blacklist
        riskBlackListService.removeRiskBlackListMember(testUserId);
        //Check whether the user is still in the black list
        boolean isInBlacklist = riskBlackListService.isUserInBlackList(testUserId);
        assertFalse("User should not be in the blacklist anymore", isInBlacklist);
    }

}
