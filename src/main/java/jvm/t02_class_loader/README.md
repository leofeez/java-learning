# 什么是类加载机制？
java虚拟机将描述类的数据从Class文件加载到内存，并对数据进行校验，转换、解析、初始化并最终形成可以被JVM
可以直接执行的指令，这个过程就可以被称之为虚拟机的类加载机制。

## 什么是类加载器？
实现通过一个类的全限定名来获取描述该类的二进制流的动作的代码就叫做类加载器。
类加载器主要分为以下几类：

- Bootstrap: 加载lib.rt charset.jar 等核心内容，获取Bootstrap类加载器会返回null，因为是由C++实现。
- Extension: 加载扩展jar包，jre/lib/ext/*.jar
- App: 加载classpath的内容
- Custom Class Loader: 自定义类加载器

其中Bootstrap Class loader是JVM中最顶级的，由JVM创建

## 双亲委派原则
一个类加载器收到类加载请求后不会立即先加载自己，而是先去让父级的加载器去检查缓存中，是否已经加载，层层迭代，到最顶层加载器都没有，
会往下进行委派去加载指定的类。

```
JVM 是按需动态加载，采用的是双亲委派机制。                               自顶向下进行实际查找和加载                          
                    ⬆                      Bootstrap ClassLoader              ⬇                      
                    ⬆                      Extension ClassLoader              ⬇
                    ⬆                      App ClassLoader                    ⬇
                    ⬆                      Custom ClassLoader                 ⬇
                    ⬆                                                         ⬇
自底向上检查，该类是否已经加载，parent方向                       找到则load到内存当中，找不到则抛出ClassNotFoundException
```

双亲委派是为了安全问题（比如自定义的ClassLoader加载一个自定义的java.lang.String），也解决了资源浪费的问题
- 避免重复加载
父类已经加载了，子类就不需要再次加载。 比如Object 类。它存放在 rt.jar 中，无论哪个类加载器要加载这个类，最终都是委派给处于模型顶端的启动类加载器加载，
因此 Object 类在程序的各种加载环境中都是同一个类。
  
- 更安全解决了各个类加载器的基础类的统一问题，如果不使用该种方式，那么用户可以随意定义类加载器来加载核心 API，会带来安全隐患。

*注：父加载器不是类加载器的加载器，也不是类加载器的父类加载器。*

如何打破双亲委派原则：


## Launcher
Launcher是java程序的入口，Launcher的ClassLoader是BootstrapClassLoader，在Launcher创建的同时，还会创建`ExtClassLoader`，
`AppClassLoader`。

```java
    public Launcher() {
        Launcher.ExtClassLoader var1;
        
        // 创建 ExtClassLoader
        try {
            var1 = Launcher.ExtClassLoader.getExtClassLoader();
        } catch (IOException var10) {
            throw new InternalError("Could not create extension class loader", var10);
        }

        // 创建 AppClassLoader
        try {
            this.loader = Launcher.AppClassLoader.getAppClassLoader(var1);
        } catch (IOException var9) {
            throw new InternalError("Could not create application class loader", var9);
        }

        // 设置当前 Thread 上下文 Class loader
        Thread.currentThread().setContextClassLoader(this.loader);
        String var2 = System.getProperty("java.security.manager");
        if (var2 != null) {
            SecurityManager var3 = null;
            if (!"".equals(var2) && !"default".equals(var2)) {
                try {
                    var3 = (SecurityManager)this.loader.loadClass(var2).newInstance();
                } catch (IllegalAccessException var5) {
                } catch (InstantiationException var6) {
                } catch (ClassNotFoundException var7) {
                } catch (ClassCastException var8) {
                }
            } else {
                var3 = new SecurityManager();
            }

            if (var3 == null) {
                throw new InternalError("Could not create SecurityManager: " + var2);
            }
            System.setSecurityManager(var3);
        }
    }
```

## ClassLoader源码
ClassLoader 是一个抽象类，像 ExtClassLoader，AppClassLoader 都是由该类派生出来，实现不同的类装载机制。
在ClassLoader 中的loadClass是类装载的入口：
```java
    protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        // 加锁，防止同一个名称的类加载冲突
        synchronized (getClassLoadingLock(name)) {
            // 查看是否已经加载过对应的class
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    // parent 表示父类加载器
                    // 这里类似于递归，一步步向parent查找
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    }
                    
                    // 当parent为null时，表示已经到最顶层的BootstrapClassLoader了
                    else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                // 最顶层的BootstrapClassLoader未找到则向下去查找class
                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    // children ClassLoader会实现findClass方法
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
```


## 自定义类加载器
实现ClassLoader的钩子函数findClass

## LazyInitializing
JVM规范中没有规定何时加载类，即按需加载
但是严格规定了什么时候必须初始化：
- new , get static, put static, invoke static指令，访问final修饰的变量除外
- java.lang.Reflect对类进行反射调用
- 初始化子类，必须先初始化父类
- 虚拟机启动时，被执行的主类必须初始化
- 动态语言支持java.lang.invoke.MethodHandle解析的结果为方法句柄时，该类必须初始化

## 编译
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

## 类加载的过程

Loading ->  Linking(verification -> preparation -> resolution) -> Initializing

- Loading: 装载 class 文件到内存。
  * 装载class文件的二进制到内存
  * 生成class类的对象，指向内存的二进制内容，我们使用Class类对象时，会自动找到指向内存中的二进制码，然后执行jvm指令。

- verification: 验证文件是否符合JVM规范

- preparation: class 静态变量赋默认值

- resolution: 将类、方法、属性等符号引用解析为直接引用
  constant pool 常量池中的各种符号引用解析为指针、偏移量等内存地址的直接引用

- Initializing: 赋初始值，执行静态代码块

## 面试题：对象的创建过程
1. class loading
2. class linking (verification, preparation, resolution)
3. class initializing
4. 申请对象内存
5. 成员变量赋默认值
6. 调用构造方法<init>
  - 成员比那里顺序赋初始值
  - 执行构造方法语句