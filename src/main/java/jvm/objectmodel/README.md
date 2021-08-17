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
     -XX:+UseCompressedClassPointers        // class pointer 压缩
     -XX:+UseCompressedOops                 // ordinary object pointer 普通对象的指针压缩
     -XX:+UseParallelGC 
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

## 对象的大小
class 在 load 到内存中的时候可以在这个过程中增加一个 agent，通过 agent 可以获取到对象的大小；

可以利用 ObjectAgent来查询出对象的大小
1. 新建一个 Module，添加ObjectAgent.java
```java
    public class ObjectAgent {
    
        static Instrumentation inst;
    
        public static void premain(String args, Instrumentation _inst) {
            inst = _inst;
        }
    
        public static long sizeof(Object o) {
            return inst.getObjectSize(o);
        }
    }
```
2. 在 src 目录下添加 META-INF/MANIFEST.MF文件，内容为(注：最后必须另一一个空行)：
```
Manifest-Version: 1.0
Premain-Class: object_agent.ObjectAgent

```
3. 将该module打成jar包，添加到需要使用的module的library中
4. 添加启动VM参数：-javaagent:${ObjectAgent.jar的路径}
5. 调用ObjectAgent.sizeof(o);

*以下内容基于64位OS*

要想知道一个对象具体占用多少个字节，首先得知道JVM以下两个参数值：
- -XX:+UseCompressedClassPointers: 表示开启 class pointer 指针压缩，class pointer 原始占用8个字节，开启压缩指针后占用4个字节，
  可以通过-UseCompressedClassPointers关闭指针压缩。
- -XX:+UseCompressedClassPointers: 表示开启普通对象的指针压缩，原始对象引用指针占用8个字节，开启压缩指针后占用4个字节，
  可以通过-UseCompressedClassPointers关闭指针压缩。

## 对象头markword具体包括什么

 

