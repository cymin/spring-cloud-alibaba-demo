使用nginx反向代理nacos集群，并实现nacos集群中服务器的的负载均衡

## 1. 创建nginx服务

配置文件见`conf.d/nginx.conf`

创建容器：
```bash
docker run -tid --name nginx -v ~/compose/nginx/conf.d:/etc/nginx/conf.d -v  ~/compose/nginx/logs:/var/log/nginx -p 8847:80 --network=service nginx
```
如果需要，修改nginx容器中的镜像源
`echo "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ stretch main contrib non-free" > /etc/apt/sources.list`


重新加载nginx：
```bash
docker exec -it nginx bash
root@6cb2b3b407ea:/# nginx -s reload


#nginx在容器中的安装位置，nginx.conf是默认的配置文件, 会默认加载conf.d中的配置文件
root@6cb2b3b407ea:/# ls /etc/nginx/
conf.d		koi-utf  mime.types  nginx.conf   uwsgi_params
fastcgi_params	koi-win  modules     scgi_params  win-utf
```

## 2. 访问nginx  
http://127.0.0.1:8847/nacos/


然后，可以修改springboot应用中的nacos地址为nginx代理的地址
```yaml
spring.cloud.nacos.server-addr=127.0.0.1:8847
```

