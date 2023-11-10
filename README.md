# trade-release

## Assignment for Class 10
**Completion Date:** Nov 10

1. **Asynchronisation of seconds orders**:
  
   - **Ceate order message configuration**:
   In this part, we first modify the `RabbitMqConfig` to create the normal order queue for receive the order request and bidning it with the order status check to the exchanger. Then we modify the `OrderMessangeSensder` to first send the order message to the `to.create.order` queue and then utilize the 'sendpaystatusCheckDelayMessage' to send the message to another `order.create` queue to check for the order payment stauts. 

   - **Order Create logic modification**:
   For this part, we modify the `CreateOrder` function and instead of using `OrderDao` to insertOrder directly, we just first call the order message sender to send create order message and then return. Finally we create the createorderReceiver to process the newely created order and call the `orderMessageSender` to send the order status check message. We also conductedd the order create test:
   ![OrderCrateTest9](Images/OrderCreatTest9.png)
   As shown in the above image, the order experience the create order message process, checking the order creation of the sending order and send the payment status checking message. The message is received correctly, and after timeout, the order will closed and the corresponding message log will prompted. 
 
2. **Jmeter**:

In this part we first download and install JMeter and go to the `bin` directory and running the jmeter application.

Then we create our own threading group and add the corresponding http request for the stress test and set the corresponding protocol, iP , port and the request path as follows(here we mock the behaviour for userId 12345 and the seckill activity also 4 in the request path):

![Jmeter8](Images/JmeterConfigInterface8.png)
Then we can demonstrate the overselling process by simulate 1000 concurrent request coming together:

![ConcurrentTestConfig8](Images/ConcurrentTestConfig8.png)
And then after strat running we can find that the stock is chane from the original 100 into a negative value:
![OverSellSimulation8](Images/OverSellSimulation8.png)



3. **Oversell Solution**:

To resolve this issue and ensure the high performance in the high concurrent situation, we decided to use the RedisLua script solution. We can first download redis(http://redis.io/download), choose the version 6.0.16 and decompress it and got the `src` directory `./redis-server` and start it:

![RedisInstall8](Images/RedisInstall8.png)

Then, we may start to integrate the change into our project:
First of all, for the `trade-lightning-deal` module, we can add the dependency into `pom.xml` and the `JedisConfig` file. 
Then we may start to implement some basic of the redis worker class and conduct some unit test as follws:

```java
public class RedisTest {
    @Autowired
    public RedisWorker redisWorker;

    @Test
    public void testSetKeyValue() {
        String key = "Yuxuan";
        String value = "The best SDE";
        //Call the method to set
        redisWorker.setKeyValue(key, value);
    }

    @Test
    public void testGetValueByKey() {
        String key = "Yuxuan";
        String expectedValue = "The best SDE";
        String actualValue = redisWorker.getValueByKey(key);
        System.out.println(actualValue);
        assertEquals(expectedValue, actualValue);
    }
}
```
The test are both successful with the `The best SDE` string being printed successfully:

![testGetValueByKey](Images/RedisTestGetValueByKey8.png)

We also implement another `setValue` function to receive the second parameter `long` type as an arugument and conduct the corresponding test:
```java
    @Test
    public void setStockTest() {
        redisWorker.setValue("stock:12345", 100L);
    }

    @Test
    public void stockCheckTest() {
        redisWorker.stockDeductCheck("stock:12345");
        System.out.println(redisWorker.getValueByKey("stock:12345"));
    }
```

The result shown that the inventory are deducted succesfully and "抢购成功“：
![SetStock](Images/SetStockTest8.png)
The stock content is changed from '100' to '99'.

Finally, we just combine the logic for stock deduction adn the lua and write a new method named `processSecKillSolution` to utilize the redisWorker to deduct the stock and use stock lua to verify first before deduct the stock and also update to use this method in our portal controller.

For the test, we again simulate the situation for the 1000 requests and could find this time the stock just deduct to zero and without the stock oversell problem anymore:

![StockDeductSol](Images/StockDeductSol9.png)

Now, if we try to run stockCheckTest again we can also verify the result that the stock just deduct to zero and we cannot checkout any stock anymore since there is no such available:

![StockDeductCheck9](Images/StockDeductCheck9.png)