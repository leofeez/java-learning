# JVM Runtime Data Area and JVM Instructions

## Runtime Data Area 运行时数据区
- JVM stacks：每个线程独享
- frame：栈帧，每一个方法对应一个栈帧，线程独享，存储了以下内容：
  * Local Variables Table，局部变量表
  * Operand Stack
  * Dynamic Linking：动态链接，比如在A() 方法中调用了B()，这时候需要去查找B()的符号链接。
  * return address: 返回地址，比如在A() 方法中调用了B()，B() 的返回值放在什么地方
- Program Counter：线程独享，主要是因为线程切换后，再切换回来知道下一步应该执行什么指令
  存放下一条指令，虚拟机的运行，类似于这样的循环
  ```java
        while(not end) {
            // 取 PC 中的位置，找到对应位置的指令
            // 执行指令
            // PC ++
        }
  ```
- method area: 方法区，线程共享
  JVM线程共享区域，存放 class 的结构
  具体实现分为：
  * JDK1.8之前的实现方式为 Perm Space 永久区，Full GC 不会清理
  * JDK1.8及之后的实现方式为 Meta Space 字符串常量位于堆，会触发GC 
- runtime constant pool
- native method stacks: 本地方法栈，线程独享
- Direct memory
  直接内存，JVM可以直接访问内核空间的内存，这个内存归操作系统管理，如网络IO，提高效率，实现0拷贝
- Heap：堆，线程共享


