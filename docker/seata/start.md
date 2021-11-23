
## 1. 准备服务目录结构
```bash
➜  seata ~/compose/seata
config    docker-compose.yml script
➜  seata ls conf
registry.conf 
```
`script`文件夹来自于：https://gitee.com/cymin/seata/tree/1.3.0/script，最好把这个1.3.0的版本项目下载下来方便使用。

## 2. seata server配置文件
1. config/registry.conf
2. config.txt
修改下面几项即可
```properties
store.mode=db
store.db.url=jdbc:mysql://192.168.1.167:3306/seata_server?useUnicode=true
store.db.user=root
store.db.password=123456
```
需要注意的是，如果registry.conf文件的`cluster="default"`的值`dafault`发生变化的话，那么config.txt文件中`service.vgroupMapping.my_test_tx_group`的值也要跟着改变。
其中的my_test_tx_group可以自定义写什么名字都可以，比如gaungzhou


##3. 把config.txt中的属性注册到nacos配置中心
从github下载https://gitee.com/cymin/seata版本1.3.0的项目。
找到根目录下的script目录，linux环境下运行`script/config-center/nacos/nacos-config.sh`即可。需要注意的是，该脚本默认读取相对目录文件`./script/config-center/config.txt` ,
所以上面修改的seata的配置文件config.txt需要放到这里。

执行脚本命令：
```bash
➜  nacos ls ~/compose/seata/script/config-center
README.md          apollo             config.txt       consul             etcd3              nacos              zk
➜  nacos cd ~/compose/seata/script/config-center/nacos
➜  nacos ./nacos-config.sh -h 192.168.1.167 -p 8847 -u nacos -w nacos -g SEATA_GROUP
set nacosAddr=192.168.1.167:8847
set group=SEATA_GROUP
Set transport.type=TCP successfully
Set transport.server=NIO successfully
Set transport.heartbeat=true successfully
…….
=========================================================================
 Complete initialization parameters,  total-count:73 ,  failure-count:0
=========================================================================
 Init nacos config finished, please start seata-server.
```
查看nacos页面发现已经有了8页的配置列表。


## 4. 创建seata server容器
### 4.1 创建单机的seata server容器
见docker-compose.yml

- 启动seata server容器：
```bash
➜  seata docker-compose -f ~/compose/seata/docker-compose.yml up
Starting seata_seata-server_1 ... done
Attaching to seata_seata-server_1
seata-server_1  | 2021-11-23 05:10:51.489  INFO --- [           main] io.seata.server.Server                   : The server is running in container.
seata-server_1  | 2021-11-23 05:10:51.660  INFO --- [           main] io.seata.config.FileConfiguration        : The configuration file used is file:/root/seata-config/registry.conf
seata-server_1  | 2021-11-23 05:10:54.596  INFO --- [           main] i.s.core.rpc.netty.NettyServerBootstrap  : Server started, listen port: 8091

```
启动成功。
可以在nacos页面的服务列表看到有一个服务名为seata-server的服务，，集群数目1，实例数为1。

### 4.2 创建seata server集群  
需要修改端口号即可，SERVER_NODE可以自动生成  
见docker-compose-cluster.yml  

启动seata server集群：
```bash
➜  seata docker-compose -f ~/compose/seata/docker-compose-cluster.yml up
Starting seata_seata-server2_1 ... done
Starting seata_seata-server1_1 ... done
Attaching to seata_seata-server1_1, seata_seata-server2_1
seata-server1_1  | 2021-11-23 05:41:34.714  INFO --- [           main] io.seata.server.Server                   : The server is running in container.
seata-server2_1  | 2021-11-23 05:41:34.768  INFO --- [           main] io.seata.server.Server                   : The server is running in container.
seata-server1_1  | 2021-11-23 05:41:34.946  INFO --- [           main] io.seata.config.FileConfiguration        : The configuration file used is file:/root/seata-config/registry.conf
seata-server2_1  | 2021-11-23 05:41:35.011  INFO --- [           main] io.seata.config.FileConfiguration        : The configuration file used is file:/root/seata-config/registry.conf
seata-server1_1  | 2021-11-23 05:41:38.540  INFO --- [           main] i.s.core.rpc.netty.NettyServerBootstrap  : Server started, listen port: 8091
seata-server2_1  | 2021-11-23 05:41:38.704  INFO --- [           main] i.s.core.rpc.netty.NettyServerBootstrap  : Server started, listen port: 8091

```
启动成功。
可以在nacos页面的服务列表看到有一个服务名为seata-server的服务，集群数目1，实例数为2。





