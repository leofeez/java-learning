# Java 虚拟机
常见的JVM实现 **Hotspot**，命令行输入 `java -version` 会输出以下信息：
```java
java version "1.8.0_271"
Java(TM) SE Runtime Environment (build 1.8.0_271-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)
```
## class 文件结构
二进制字节流

16进制内容如下：
```java
cafe babe 0000 0034 0010 0a00 0300 0d07
000e 0700 0f01 0006 3c69 6e69 743e 0100
......
```
- cafe babe：magic number(魔数)，4个字节，文件类型标识，标识为class文件
- 0000     ：minor version 小版本，
- 0034     ：major version 大版本，比如java8 是52.xx大版本
- 0010     ：(constant_pool_count -1) 常量池中有多少个常量，比如上面的就是15个

利用javap -v class路径输出：
```
lixiufeideMacBook-Pro:clazz leofee$ javap -v /Users/leofee/projects/leofee/java-learning/out/production/java-learning/jvm/clazz/ClassByteCode.class
Classfile /Users/leofee/projects/leofee/java-learning/out/production/java-learning/jvm/clazz/ClassByteCode.class
  Last modified 2021-4-27; size 284 bytes
  MD5 checksum 0f67da71e28c64825a0733c5f45a9734
  Compiled from "ClassByteCode.java"
public class jvm.clazz.ClassByteCode
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #3.#13         // java/lang/Object."<init>":()V
   #2 = Class              #14            // jvm/clazz/ClassByteCode
   #3 = Class              #15            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               LocalVariableTable
   #9 = Utf8               this
  #10 = Utf8               Ljvm/clazz/ClassByteCode;
  #11 = Utf8               SourceFile
  #12 = Utf8               ClassByteCode.java
  #13 = NameAndType        #4:#5          // "<init>":()V
  #14 = Utf8               jvm/clazz/ClassByteCode
  #15 = Utf8               java/lang/Object
{
  public jvm.clazz.ClassByteCode();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 7: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Ljvm/clazz/ClassByteCode;
}
SourceFile: "ClassByteCode.java"
```