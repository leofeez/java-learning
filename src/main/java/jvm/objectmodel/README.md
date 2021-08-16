## 对象的创建过程
1. [class loading](../class_loader)
2. class linking (verification, preparation, resolution)
3. class initializing
4. 申请对象内存
5. 成员变量赋默认值
6. 调用构造方法<init>
    - 成员比那里顺序赋初始值
    - 执行构造方法语句

## 对象在内存中的存储布局

> https://www.jianshu.com/p/d42ac3ab41f7

对象的大小和虚拟机的配置有关联
```java
 java -XX:+PrintCommandLineFlags -version

输出如下:
     -XX:InitialHeapSize=268435456
     -XX:MaxHeapSize=4294967296
     -XX:+PrintCommandLineFlags
     -XX:+UseCompressedClassPointers
     -XX:+UseCompressedOops -XX:+UseParallelGC 
java version "1.8.0_271"
Java(TM) SE Runtime Environment (build 1.8.0_271-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)
```

普通对象：

- 对象头
   * markword: 8个字节
   * class pointer: 属于哪个class，-XX:+UseCompressedClassPointers 为4字节，否则为8字节。
   * 实例数据：
      - 引用类型: -XX:+UseCompressOops 为4字节，否则为8字节
     
   * 对齐填充：保证对象的大小为8的倍数

数组对象:
   * markword: 8个字节
   * class pointer: 属于哪个class，-XX:+UseCompressedClassPointers 为4字节，否则为8字节。
   * 数组长度: 4个字节
   * 数组数据
   * 对齐填充：保证对象的大小为8的倍数