# JVM java 虚拟机
常见的JVM实现 **Hotspot**，命令行输入 `java -version` 会输出以下信息：
```java
java version "1.8.0_271"
Java(TM) SE Runtime Environment (build 1.8.0_271-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)
```
HotSpot是JVM的一种实现

### 什么是类加载机制？
java虚拟机将描述类的数据从Class文件加载到内存，并对数据进行校验，转换、解析、初始化并最终形成可以被JVM
可以直接执行的类型，这个过程就可以被称之为虚拟机的类加载机制。

### 类加载的过程
Loading ->  Linking(verification -> preparation -> resolution) -> Initializing

- Loading: 装载 class 文件到内存。
  * 装载class文件的二进制到内存
  * 生成class类的对象，指向内存的二进制内容，我们使用Class类对象时，会自动找到指向内存中的二进制码，然后执行jvm指令。
    
- preparation: class 静态变量赋默认值
  
- resolution:
  
- Initializing: 赋初始值，执行静态代码块

### 什么是类加载器？
实现通过一个类的全限定名来获取描述该类的二进制流的动作的代码就叫做类加载器。

Bootstrap: 加载lib.rt charset.jar 等核心内容，获取Bootstrap类加载器会返回null，因为是由C++实现。
Extension: 加载扩展jar包，jre/lib/ext/*.jar
App: 加载classpath的内容
Custom Class Loader: 自定义类加载器

```
JVM 是按需动态加载，采用的是双亲委派机制。                           自顶向下进行实际查找和加载                          
                    ^                                                  |                      
                    |                                                  |
                    |                                                  |
                    |                                                  |
                    |                                                  
自底向上检查，该类是否已经加载，parent方向                                  
```


### 双亲委派原则
一个类加载器收到类加载请求后不会立即先加载自己，而是先去让父级的加载器去检查缓存中，是否已经加载，层层迭代，到最顶层加载器都没有，
会往下进行委派去加载指定的类。

双亲委派是为了安全问题（比如自定义的ClassLoader加载一个自定义的java.lang.String），也解决了资源浪费的问题
- 避免重复加载
父类已经加载了，子类就不需要再次加载。 比如Object 类。它存放在 rt.jar 中，无论哪个类加载器要加载这个类，最终都是委派给处于模型顶端的启动类加载器加载，
因此 Object 类在程序的各种加载环境中都是同一个类。
  
- 更安全解决了各个类加载器的基础类的统一问题，如果不使用该种方式，那么用户可以随意定义类加载器来加载核心 API，会带来安全隐患。

*注：父加载器不是类加载器的加载器，也不是类加载器的父类加载器。*

### Launcher

### ClassLoader源码


### 自定义类加载器
实现ClassLoader的钩子函数findClass

### LazyInitializing
JVM规范中没有规定何时加载类，即按需加载
但是严格规定了什么时候必须初始化：
- new , get static, put static, invoke static指令，访问final修饰的变量除外
- java.lang.Reflect对类进行反射调用
- 初始化子类，必须先初始化父类
- 虚拟机启动时，被执行的主类必须初始化
- 动态语言支持java.lang.invoke.MethodHandle解析的结果为方法句柄时，该类必须初始化

### 编译
JVM中有三种编译模式
- 混合模式：-Xmixed，默认为混合模式
  * 混合使用解释器 + 热点代码编译
  * 起始阶段使用解释执行
  * 热点代码检测 
    1. 多次被调用的方法（方法计数器：检测方法执行频率）
    2. 多次被调用的循环（循环计数器：检测循环执行频率）
    3. 进行编译，会将这些字节码实时编译成目标机器码，以便提升性能
  
- 解释模式：-Xint: 使用解释执行模式，启动很快，执行稍慢
  
- 纯编译模式：-Xcomp:使用纯编译模式，执行很快，启动很慢


### TLAB(Thread Local Allocation Buffer)
线程本地分配缓存
一个线程专用的内存分配区域，为了加速对象分配
每一个线程，都会产生一个TLAB，该线程独享的工作区域
每一个线程，都会默认使用TLAB区域
TLAB用来避免多线程冲突问题，提高对象分配效率。
TLAB缺省情况下仅占有整个Eden空间的1%，也可以通过选项-XX:TLABWasteTargetPercent设置TLAB空间所占用Eden空间的百分比大小。

### 内存模型
1. 程序计数器：指向当前程序正在执行的字节码指令的地址，每个线程都有独立的程序计数器。
2. 虚拟机栈：
3. 