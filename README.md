# trade-release

## Assignment for Class 13

### Rate Limiting and AWS Cloud Configuration(Optional)

**Completion Date:** Nov 30

1. **Implemntation Hystrix in the Project**:

I added necessary pom dependency into `trade-web-portal` module and add the annotation `@EnableCircuitBreaker` it is used to start the breaker and prevent that one service failed caused the entire service down. Then I configure the necessary configuration for the rate limiting and using the `SEMAPHORE` isolation strategy and set the maximum concurrency allowed to be 1. 


2. **Test for Rate Limiting**:

For this testing, I still use  `JMeter` to simulate the large concurrency request.  The configuration are:
![JmeterConfigue141](Images/JMeterConfigue14.png)
![JmeterConfigue142](Images/JmeterConfigue214.png)

Then we test the corresponidng path from our application and can verify that the system indeed triggered the rate limiting: 
![RateLimitingTriggered14](Images/RateLimitTriggered14.png)
After the ramp-up period is expired, I also verifed that the system can continue to handle normal seckill buy request normally:
![NoRateLimitTriggered14](Images/NoRateLimitTriggered14.png)

3. **AWS Cloud Configuration**:

For this part, I first create my EC2 instance in AWS, for the consideration of configure whole application into the cloud, I choose `t2.medium` as the instance type. 

![CreateInstance6](Images/CreateInstance16.png)

After got my fixed elastic IP address, I use `EC2 instance connect` to connect to my instance and connected to the server successfully and have updated my password:

![ConnectToServer16](Images/ConnectToServer116.png)

Set the passowrd for the 'super root' and switch to the normal user and modify the `sshd` configure filw and enable the 'root' login, and login remotely with ssh root:

![SSHRemoteLogin16](Images/SSHRemoteLogin16.png)

Add the new security rule:
![EditInBoundRule16](Images/EditInBoundRule16.png)

Upload JDK to the remote server:

![UploadJDKLinux16](Images/UploadJDKLinux16.png)


Install SQL and open remote connection and change the necessary configuration file and restart MySQL, I can verifed my MySQL connect to my remote host successfully:

![MySQLConnection15](Images/MySQLConnection15.png)

Then I transfer the data from local database into AWS's MySQL

![TransferData15](Images/TransferData15.png)

## Install RabbitMQ ##

Install `erlang`, add the public key and RabbitMQ. Then install the necessary Web plug-in. 

![RabbitMQStep1](Images/RabbitMQStep1.png)

Add user and assigned the administrator role and insure the security group for the server is opening the 15672 port.
![RabbitMQStep2](Images/RabbitMQStep2.png)

## Install Redis##
![RedisInstall](Images/RedisInstallation15.png)


## Install ElasticSearch ##

First downloaded and decompressed the elasticsearch version `7.4.0`. Then add necessary configuration files and set the maximum concurrent number for all users to be `4096`. As ES cannot run as `root`, we need to create an 'non-root' user and here I create an user with name `es` and the specific user could got the foler's permission. And we just switch as es and enter the installation folder for the es and start the `es`'s servies:

![ElasticSearchInstall](Images/ElasticSearch15.png)


