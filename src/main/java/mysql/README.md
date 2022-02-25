# MySql

## SQL 剖析工具 Profile
`
mysql> set profiling=1;
mysql> show profiles;
mysql> show profile;

show variables like '%commit';
`

## performance_schema 监控 Mysql
默认开启： show variables like 'performance%';

- show processlist\G 显示连接数；

## 数据类型优化
1. 更小的通常更好，
2. 简单就好
    简单的数据类型通常需要CPU的周期更少，字符串需要校验字符编码
    CHAR 和 Varchar
3. date datetime timestamp
4. 使用枚举enum代替字符串类型




