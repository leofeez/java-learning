# class 文件结构
二进制字节流

16进制内容如下：
```
                   0a代表 10,则表明标记位
                         ⬆
                         ⬆
cafe babe 0000 0034 0010 0a00 0300 0d07
000e 0700 0f01 0006 3c69 6e69 743e 0100
......
|------| |-----|
|access| |this |
|flags | |class|
|------| |-----|
```
- cafe babe：magic number(魔数)，4个字节，文件类型标识，标识为class文件
- 0000     ：minor version 小版本，2个字节
- 0034     ：major version 大版本，2个字节，比如java8 是52.xx大版本
- 0010     ：(constant_pool_count -1, 之所以减一是因为0位作为预留位)，常量池中有多少个常量，比如上面的就是15个
- constant pool里面的内容

利用javap -v class路径输出，class文件结构如下，或者通过IDEA插件jclass lib：
```
  Last modified 2021-4-27; size 284 bytes
  MD5 checksum 0f67da71e28c64825a0733c5f45a9734
  Compiled from "ClassByteCode.java"
public class jvm.clazz.ClassByteCode
  minor version: 0                        // 小版本号
  major version: 52                       // 大版本号
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #3.#13         // java/lang/Object."<init>":()V  -----> -----------      // 方法申明引用
   #2 = Class              #14            // jvm/clazz/ClassByteCode             | #3        |      // 类的全称
   #3 = Class              #15            // java/lang/Object               <----|           |      // #1方法的申明类
   #4 = Utf8               <init>                                                            |      // 构造方法
   #5 = Utf8               ()V                                                               |      // 构造方法`()V` 表示方法参数为空，V 代表 void，L代表Object
   #6 = Utf8               Code                                                              |      // 属性表的名字
   #7 = Utf8               LineNumberTable                                                   |#13   // 
   #8 = Utf8               LocalVariableTable                                                |      // 
   #9 = Utf8               this                                                              |
  #10 = Utf8               Ljvm/clazz/ClassByteCode;                                         |
  #11 = Utf8               SourceFile                                                        |
  #12 = Utf8               ClassByteCode.java                                                |
  #13 = NameAndType        #4:#5          // "<init>":()V                    <---------------|
  #14 = Utf8               jvm/clazz/ClassByteCode                                                  // 全限定名称
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
Interfaces
Fields
Methods
Attributes
```