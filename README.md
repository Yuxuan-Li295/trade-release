# trade-release

## Assignment for Class 8 
**Completion Date:** Nov 3

1. **Flash Sale Activity**:
  
   - **Database Design**: 

   We first create the flash sale activity table:

  ```sql
CREATE TABLE `seckill_activity` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '秒杀活动id',
  `activity_name` VARCHAR(255) DEFAULT NULL COMMENT '秒杀活动名',
  `goods_id` BIGINT DEFAULT NULL COMMENT '商品id',
  `start_time` DATETIME DEFAULT NULL COMMENT '秒杀活动开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '秒杀活动结束时间',
  `available_stock` INT DEFAULT 0 COMMENT '可用库存数量',
  `lock_stock` INT DEFAULT 0 COMMENT '锁定库存数量',
  `activity_status` INT DEFAULT NULL COMMENT '活动状态 0-下架 1-正常',
  `seckill_price` INT DEFAULT NULL COMMENT '秒杀价格，单位为分',
  `old_price` INT DEFAULT NULL COMMENT '商品价格，单位为分',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='秒杀活动表';
   ```
     
   - **Flash Sale Activity Creation**: 

   In this part, we first configure the `application.properties` and the `mybatis-generator-config.xml` file and the tool for the inverse generation `GeneratorSqlmap` and finally the main start class. 

   - **Flash Sale CRUD development for the database and the `SecSkillActivityDao` interface and its corresponding implementations. 

   - **Add flash sale activity page: In this part, we modifty the 'pom.xml' and add the controller part for adding a new skill activity info. Then, we import the necessary template files for the flash sale pages.

  For the testing, I tried to add a new skill activity "黑色星期五“ with goods_id 12345 start time today (11-04) and the ending time (11-11) one week after the starting time to create an one week period of sale and we can verify from both the console and the database that the test for adding skill activity is successful and the data is added to the database successfully:

  ![SecSkillDatabaseAdding](Images/SecskillDatabase7.png)
  
  ![AddingSecSkillActivityTest](Images/AddingSecSkillActivityTest.png)
