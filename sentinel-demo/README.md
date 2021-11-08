### 下载sentinel-dashboard-1.8.2.jar
启动：
```shell script
java -Dserver.port=8858 -jar sentinel-dashboard-1.8.2.jar

```


### 整合sentinel控制台
在SentinelApplication启动参数配置:  
Environment->VM options->-Dcsp.sentinel.dashboard.server=localhost:8858


### 访问
http://localhost:8858  
随意访问一个接口，就可以看到应用监控信息

### tip
流控规则和熔断规则已经在代码中写了，所以可以在控制台中看到


