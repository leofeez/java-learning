# Redis

- 硬盘的缺点

1. 寻址慢
2. 带宽

- 内存优点

1. 寻址
2. 带宽大

磁盘是由一个个扇区组成，一个扇区512bytes，索引4K 操作系统无论如何都会最少获取4K

- 关系型数据库

关系型数据库建表的时候必须给出列的类型和长度，因为关系型数据库倾向于行级存储（即存储数据是一行一行的），指定长度之后，即使那些为 null 的字段，也会用0去占位，这样在增删改的时候不需要去移动数据，只需要去覆写即可。

- 数据在磁盘和内存的体积是不一样的



数据库排名 ： https://www.db-engines.com/en/ranking/

memcached 中 value 没有类型的概念，在client取出比较复杂的value时，需要客户端自行解码，增加了复杂度。

而redis中的value都是有类型的，本身也有对应的方法去解析value。

- redis 单线程为什么快？
    * 内核kernel中的epoll
    * NIO

- redis 默认会有16个库



## Redis中value的类型

help 命令查看帮助，如 `help @string`

type key 可以得到value的类型

| type

|encoding

**Redis 是二进制安全的**，底层存放的是字节

### String

字符串类型操作

- set key value [nx|xx] : nx 当key不存在的时候设置（即只能新增），xx是当key存在时才设置（即只能更新）
- mset key value [key value]: 批量设置key-value
- mget key [key...] 批量获取
- append key value 追加字符
- setget 设置新的value并返回旧的value

数值操作

- incr:

  应用场景：抢购，秒杀，详情页，点赞，评论数

- decr

bitmap

使用场景：

1. 有用户系统，统计用户的登陆天数，且窗口随机：可以利用二进制表示，0表示未登陆，1表示登陆，即每天天代表一个二进制位。

   第一天登陆 setbit leofee 0 1

   第10天登陆 setbit leofee 9 1

   统计登陆天数 bitcount leofee 0 -1

2. 京东618做活动，送礼物，大库备货多少礼物，假设京东有2亿的用户。即统计活跃用户

   每个用户对应一个二进制位

   假设第2个用户在2022-01-01登陆了：setbit 2022-01-01 2 1

   假设第2个用户在2022-02-01登陆了：setbit 2022-02-01 2 1

   假设第3个用户在2022-01-01登陆了：setbit 2022-01-01 3 1

   bitop or result 2022-01-01 2022-02-01

- setbit key offset value
- bitcount key start end
- bitop operation destkey [keys...]

