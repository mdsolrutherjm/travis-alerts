package com.eaglesinterns.travisproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SlackFunctionsTest {

    SlackFunctions sf = new SlackFunctions();
    @Test
    public void sendsMessage()
    {
        sf.sendText("Send generic message text. ");

    }
    @Test
    public void sendsPassed()
    {
        sf.sendPassed("Passed Branch ");

    }
    @Test
    public void sendsFailed()
    {
        sf.sendFailed("Passed Branch ");

    }
}
