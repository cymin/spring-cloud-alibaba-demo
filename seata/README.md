### 项目说明：
- order-seata和stock-seata是普通项目，使用了mybatis，测试普通事务.（暂时用的同一个数据库seata_server，下面修改了成了两个）
- alibaba-order-seata和alibaba-stock-seata整合了openfeign、nacos集群、nginx反向代理nacos、seata等组件
&nbsp;

### 如何在项目中使用seata
#### 1.增加依赖
```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
</dependency>
```
#### 2.微服务对应的数据库中增加undo_log表
相关sql文件见`docker/seata/sql-files`.  
使用两个本地数据库`seata_order`和`seata_stock`模拟两个不同机器上的mysql服务。  
即给数据库`seata_order`和`seata_stock`分别增加undo_log表。  
undo_log表作用：把执行sql语句的前后原数据保存下来，用于后续做回滚操作。

#### 4.给微服务配置seata的事务分组配置
```yaml
spring:
  cloud:
    alibaba:
      seata:
        # 事务分组
        tx-service-group: my_test_tx_group
```
微服务中分别增加上述配置。

#### 5.设置seata的注册中心和配置中心
```yaml
seata:
  # 设置seata的注册中心,告诉seata client怎么与seata server（TC）通讯（在springboot2.1之前只能通过registry.conf文件配置，即与seata的相同的registry.conf复制到微服务的resources目录中一份）
  registry:
    type: nacos
    nacos:
      server-addr: localhost:8847 # 这就是seata server（TC）所在的nacos服务地址
      username: nacos
      password: nacos
      application: seata-server # 这就是seata server（TC）的服务名称，默认seata-server，如果未修改可以不配
      group: SEATA_GROUP # 这就是seata server（TC）所在的组，默认SEATA_GROUP，如果未修改可以不配
  # 还需要设置seata的配置中心，可以读取到nacos中配置列表中关于client*的配置项
  config:
    type: nacos
    nacos:
      server-addr: localhost:8847
      username: nacos
      password: nacos
      group: SEATA_GROUP
```
微服务中分别增加上述配置。

### 测试分布式事务所需的基础服务
- mysql
- nginx
- nacos
- seata


服务创建都在docker目录下有写。

### 开启全局事务
`@GlobalTransactional`