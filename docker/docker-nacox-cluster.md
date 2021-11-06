## 启动mysql
- 使用docker创建mysql
- 添加数据

```sql
# 创建nacos数据库
create database nacos;

# 创建nacos用户
CREATE USER 'nacos'@'%' IDENTIFIED BY 'nacos';

# 给创建的nacos用户分配远程访问权限,只授权nacos数据库的权限
GRANT ALL ON nacos.* TO 'nacos'@'%';

# 运行nacos-server-1.4.1.tar.gz包里面的nacos-mysql.sql文件，创建出表结构
use nacos;

# 执行nacos-mysql.sql
```
在docker中创建一个network:
```bash
docker network ls
docker network create service
```

## 创建集群
- docker-compose.yml
```bash
version: "3"
services:
  nacos8849:
    hostname: nacos8849
    container_name: nacos8849
    image: nacos/nacos-server
    volumes:
      - ./cluster-logs/nacos8849:/home/nacos/logs
      - ./init.d/custom.properties:/home/nacos/init.d/custom.properties
    ports:
      - "8849:8848"
      - "9848:9848"
      - "9555:9555"
    env_file:
      - env/nacos-hostname.env
  nacos8850:
    hostname: nacos8850
    container_name: nacos8850
    image: nacos/nacos-server
    volumes:
      - ./cluster-logs/nacos8850:/home/nacos/logs
      - ./init.d/custom.properties:/home/nacos/init.d/custom.properties
    ports:
      - "8850:8848"
      - "9849:9848"
    env_file:
      - env/nacos-hostname.env
  nacos8851:
    hostname: nacos8851
    container_name: nacos8851
    image: nacos/nacos-server
    volumes:
      - ./cluster-logs/nacos8851:/home/nacos/logs
      - ./init.d/custom.properties:/home/nacos/init.d/custom.properties
    ports:
      - "8851:8848"
      - "9850:9848"
    env_file:
      - env/nacos-hostname.env

networks:
  default:
    external:
      name: service
```

- env/nacos-hostname.env
```yaml
#nacos dev env
SPRING_DATASOURCE_PLATFORM=mysql
PREFER_HOST_MODE=hostname
NACOS_SERVERS=nacos8849:8848 nacos8850:8848 nacos8851:8848
MYSQL_SERVICE_HOST=mysql
MYSQL_SERVICE_DB_NAME=nacos
MYSQL_SERVICE_PORT=3306
MYSQL_SERVICE_USER=nacos
MYSQL_SERVICE_PASSWORD=nacos
```

- 运行
```bash
docker-compose up
```


## 访问nacos网页
下面网址都可以访问：  
http://127.0.0.1:8849/nacos/index.html
8850、8851都可以访问

默认用户名和密码都是: `nacos`


## 创建nginx服务
```bash
docker run -tid --name nginx -v ~/compose/nginx/conf.d:/etc/nginx/conf.d -v  ~/compose/nginx/logs:/var/log/nginx -p 8847:80 --network=service nginx
```
如果需要，修改nginx容器中的镜像源
`echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ stretch main contrib non-free" > sources.list`


~/compose/nginx/conf.d/nginx.conf
```yaml
upstream nacoscluster {
  server  nacos8849:8848;
  server  nacos8850:8848;
  server  nacos8851:8848;
}

server {
  listen  80;
  server_name localhost;

  location /nacos/{
    proxy_pass http://nacoscluster/nacos/;
  }
}
```

reload nginx：
```bash
docker exec -it nginx bash
root@6cb2b3b407ea:/# nginx -s reload


#nginx在容器中的安装位置，nginx.conf是默认的配置文件, 会默认加载conf.d中的配置文件
root@6cb2b3b407ea:/# ls /etc/nginx/
conf.d		koi-utf  mime.types  nginx.conf   uwsgi_params
fastcgi_params	koi-win  modules     scgi_params  win-utf
```

## 访问nginx  
http://127.0.0.1:8847/nacos/


## 修改springboot应用中的nacos地址为nginx代理的地址
```yaml
spring.cloud.nacos.server-addr=127.0.0.1:8847
```

## 高级配置  
如果上面的属性列表无法满足你的需求时,可以挂载custom.properties到/home/nacos/init.d/ 目录,然后在里面像使用
Spring Boot的application.properties文件一样配置属性, 并且这个文件配置的属性优先级高于application.properties.




