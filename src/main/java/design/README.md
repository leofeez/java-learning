# 设计模式

本身编程中是没有设计模式的，只是写代码的人多了，是很多人在编码过程中总结出来的提高开发和维护效率的套路。因为开发完成的软件是需要不停的迭代，
所以持续的重构是保持代码质量不下降的手段，而重构的重要手段就是使用设计模式来优化代码，保证代码的可扩展性，可维护性。

## 代码质量的评估标准

- 可维护性
  * 在不去破坏原有代码的设计以及不引入新的bug的前提下，能够快速的修改或增加代码。相反，可维护性差的代码，在添加或者修改原有逻辑的时候，存在极大可能性会引入新的bug，并且需要花费很长的时间。
- 可读性
  * 在软件开发过程中，大部门的时间都是花在读代码上，而不是在写代码
  * 检查代码风格和编程规范
  * 检查代码中是否存在常见坏味道，比如说大的方法
- 可扩展性：在不修改原有代码或者少量修改的情况下，能够通过扩展的方式添加新的功能（开闭原则）
- 简洁性：用简单的代码解决复杂度问题
- 可复用性：尽量减少重复代码。实现可复用性
- 可测试性：可使用测试用例进行单元测试
- 灵活性：在添加新代码时，能够不破坏原有代码结构，并且已有的代码不会对新加入的代码产生排斥，冲突，比如在使用某组接口时，能够应对多种场景

## 设计模式六大原则

设计原则是设计模式的抽象，设计模式是设计原则的具体实现。

### 单一职责

一个类或者接口仅有一个让它改变的原因，否则类应该被拆分。

不遵循单一职责的缺点：

- 如果需要修改该类的某一个职责，就会有几率影响到其他职责，削弱该类应对变化的能力。
- 如果其他类依赖了该类，那么其他类也会引入其他多余的职责，导致多余的依赖传递。
- 类的复杂度会更高，可读性变差，可维护性变低

所以遵循单一原则，一个类就可以实现高内聚，类与类之间的耦合度就会降低，并且复杂度降低，整个类的可读性就会变高，自然可维护性也随之变高。

如何判断一个类是否遵循了单一职责？

- 类中的代码行数，方法，或者属性是否过多
- 类过多的依赖其他类
- 私有方法过多
- 类中的大量方法总是操作其中的几个属性

### 开闭原则

对修改关闭，对扩展开放，即抽象约束、封装变化，要求利用抽象类或者接口定义一个稳定的抽象层来定义框架，而那些会产生变化的内容应该封装到具体的实现类中，当有新的需求，也只需要派生一个新的实现类即可，而无需去修改原有逻辑。

核心思想：

- 抽象
- 封装
- 扩展

### 里氏替换原则

如果S是T的子类型，对于S类型的任意对象，如果将他们看做是T类型的对象，那么对象的行为理应和期望的行为一致。

- 什么是替换
  替换的前提是要求面向对象的语言是支持多态的，同一个行为具有不同的表现形式的能力，即当一个方法的入参是一个接口类型，那么可以接收所有实现该接口的实现类。
- 什么是期望的行为理应一致
  在不了解派生类的情况下，仅通过接口或者基类方法即可清楚方法的行为，而不管哪一种派生类的实现，都与接口或者基类方法的期望行为一致。

### 依赖倒置

在设计代码架构时，要求高层模块不应该依赖于底层模块，二者都应该依赖于抽象，抽象不应该依赖于细节，细节应该依赖于抽象。因为在软件设计中，细节具有可变性，而抽象层要相对稳定，以抽象为基础搭建起来的架构要比以细节为基础搭建起来的架构要稳定的多。

### 接口隔离

一个类对另一个类的依赖应该是建立在最小的接口上，即：要为各个类建立它们需要的专用接口，而不要试图去建立一个很庞大的接口供所有依赖它的类去调用

场景：目前A系统依赖了B系统的一个`UserServer`的接口，里面有注册用户，查询用户信息的方法，然后有一天，需要增加一个删除用户的方法

错误的做法：直接在原有的`UserService`中增加`delete`方法，这样原先所有实现该接口的实现类都要实现`delete`方法，即使某些实现类根本不需要该方法，除此之外，还会将`delete`方法暴露给外部引入依赖该接口的其他类。

正确的做法：增加一个额外的`Service`，而不在原有`UserService`上增加方法，这样需要用到delete方法的实现类去多实现增加的`Service`即可

![image-20230419220721664](C:\projects\leofee\java-learning\src\main\java\design\interface.png)

1. 将胖接口分解为多个粒度小的接口，提高系统的灵活性，可维护性

### 迪米特法则

又叫最少知识原则，指的是一个类/模块对其他的类/模块了解的越少越好，即：不该有直接依赖关系的类之间，不要有依赖，有依赖关系的类之间，尽量只依赖必要的接口。

## 23种设计模式

#### 创建型

提供创建对象的机制，提升已有代码的灵活性和可复用性，如：单例Singleton，工厂Factory，建造者Builder，原型模式prototype

##### 单例模式 Singleton

保证某个类在运行期间，只有一个实例。构造方法私有化。

- 饿汉式：在类加载期间，就已经将实例初始化完毕，所以线程安全
- 懒汉式：懒加载，DCL才能保证线程安全，但是会引入加锁机制，影响效率。
- 静态内部类：通过静态内部类的方式实现单例模式是线程安全的，同时静态内部类不会在Singleton类加载时就加载，而是在调用getInstance()方法时才进行加载，达到了懒加载的效果。
- 枚举：利用枚举类实现，线程安全并且不会被反射和序列化机制破坏。

![image-20230419231443666](singleton\singleton.png)

**延伸：如何破坏单例模式？**

但是利用反射机制或者序列化机制会破坏该单例类。

反射破坏单例：

```java
// 利用反射破坏单例
Constructor<?>[] constructors = Singleton.class.getDeclaredConstructors();
Constructor<?> constructor = constructors[0];
constructor.setAccessible(true);
Singleton instance = Singleton.class.newInstance();
Singleton instance2 = Singleton.class.newInstance();
// 输出false
System.out.println(instance == instance2);
```

解决反射破坏单例

```java
private Singleton() {
    // 私有构造方法增加一层判断
    if (INSTANCE != null) {
        throw new RuntimeException("非法创建单例");
    }
}
```

序列化破坏单例

```java
// 利用序列化破坏单例
Singleton instance3 = Singleton.getInstance();
ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Singleton.obj"));
out.writeObject(instance3);

ObjectInputStream in = new ObjectInputStream(new FileInputStream("Singleton.obj"));
Singleton instance4 = (Singleton)in.readObject();
System.out.println(instance3);
System.out.println(instance4);
// 输出false
System.out.println(instance3 == instance4);
```

解决序列化破坏单例

```java
// 在单例类添加该方法
private Object readResolve() {
    return INSTANCE;
}
```

##### 工厂模式 Factory

对外不暴露对象创建的细节，对象创建的过程交给工厂类实现。

- 简单工厂 Simple Factory

  只适合于产品对象较少，且产品固定的需求，对于产品变化无常的需求来说显然不合适。

  ![Simple Factory](factory\simpleFactorty.png)
- 工厂方法 Factory Method

  定义一个工厂接口，对于每一个产品都有对应的工厂类的实现，当需要指定产品的时候只需要通过对应产品的工厂类进行创建。

  ![Factory Method](factory\factoryMethod.png)
- 抽象工厂 Abstract Factory

##### 建造者 Builder

将一个复杂对象的创建与表示相分离，使同样的构建过程可以创建不同的表示。

![Builder](builder\builder.png)

#### 结构型

代理，桥接，装饰器，适配器

##### 代理模式 Proxy

##### 适配器模式 Adapter

##### 桥接模式 Bridge

`FileInputStream -> InputStreamReader -> BufferedReader`

#### 行为型

负责对象间的高效沟通和职责传递委派

观察者，模板，策略，责任链，迭代器

##### 策略模式 Strategy

##### 策略 + 工厂模式 Strategy Factory

##### 责任链模式 Responsibility Chain

- 简单责任链（单向）
- Servlet Filter 责任链（双向）

##### 观察者模式

## 组合模式

适用于树形结构

## Visitor

##### Command

如菜单栏中的 cut -> 取消Ctrl+ Z 实现undo

## UML

类图
