## 创建mysql容器
见docker-compose.yml

## 创建数据库相关信息
1.nacos相关的数据库信息
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

2.seata server相关的数据库信息
创建数据库:seata_server
用户名密码:root/123456
执行上面的sql脚本: https://gitee.com/cymin/seata/blob/1.3.0/script/server/db/mysql.sql  

最好把这个1.3.0的版本项目下载下来方便使用。

