## 容器

### Collection

#### 1. HashTable
实现于Map接口，是一种存储key-value对的容器，的底层实现是基于哈希表，数组，链表，
- 继承于`Dictionary`类
- 是线程安全的，几乎所有的方法都加上了synchronized
- key 和 value 都不允许为 `null`
- 默认的初始容量为 11，负载因子为0.75
- 扩容机制为原容量的两倍 + 1

#### 2. HashMap
底层数据结构为哈希表，数组，链表，红黑树（当链表的长度大于等8时，链表会转换为红黑树结构）。
- 继承于 `AbstractMap`类
- 非线程安全的
- key 和 value 可以为 `null`
- 默认的初始容量为16，负载因子为0.75，扩容的阈值为 capacity * loadFactor
- 扩容机制为原容量的两倍

HashMap重要的几个属性：
```java
    // 默认的初始化容量，16，必须为2的幂
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    // 最大支持的容量1073741824
    static final int MAXIMUM_CAPACITY = 1 << 30;

    // 默认的负载因子0.75f
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // 当链表的长度大于8会将链表转换为红黑树结构
    static final int TREEIFY_THRESHOLD = 8;

    // 当链表的长度小于6时会将红黑树转换为链表
    static final int UNTREEIFY_THRESHOLD = 6;

    // 当entry数组的长度大于64才允许将链表转换为红黑树，
    // 否则应该直接扩容而不是将链表进行树化
    static final int MIN_TREEIFY_CAPACITY = 64;

    // 存放entry的数组
    transient Node<K,V>[] table;

    // key-value对的数量
    transient int size;

    // 扩容时的阈值
    int threshold;

    // 负载因子
    final float loadFactor;
```

HashMap提供了无参构造和指定初始容量的构造方法。
HashMap初始化的时候默认的容量是16，扩容的阈值为12
```java
    public HashMap(int initialCapacity, float loadFactor) {
        // 初始容量小于0则抛出异常
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        // 初始化容量如果超出最大容量则设置为最大容量
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        // 负载因子小于等于0则抛出异常
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        // 主要功能是返回一个比给定整数大且最接近的2的幂次方整数，
        // 如给定3，则返回2的2次方4
        //  给定10，返回2的4次方16
        this.threshold = tableSizeFor(initialCapacity);
    }
```

关于HashMap的容量为什么要设置为2的幂次方整数，原因是在putVal的时候需要计算table的index，即entry在table的索引，
计算index的原理就是利用(capacity -1) & hash，当容量capacity设置为2的幂次方时，(capacity-1)之后的二进制码
最高为之后可以保证全是1，如：
 - 容量为8， 对应的7 的二进制码为0000 0111
 - 容量为16，对应的15的二进制码为0000 1111
 
这样对hashCode进行按位与&计算之后可以保证结果既可以是基数也可以是偶数，只要对应的key的hashCode算法足够好，就可以
有效减少hash碰撞的几率。

HashMap在初始化建议根据实际情况设置初始容量大小，规则为 expectedSize/loadFactor + 1，而实际上HashMap会采用第一个大于该数值的2的幂作为初始化容量，
指定初始化容量可以有效减少HashMap的扩容resize次数，因为在进行扩容时会导致重建hash表非常影响性能。




1.8 对HashMap的改进
- 1.7 中 HashMap的底层实现是数组 + 单链表，1.8底层实现增加了红黑树，当链表的长度大于等于8（默认）时将链表转换为红黑树。
- 1.7 中 HashMap的链表使用头插法，get()时会出现死循环。

#### 3. Collections.synchronizedMap
`Collections.synchronizedMap`方法是jdk提供的用于返回一个线程安全的Map.
底层实现原理就是`Collections`里维护了一个`SynchronizedMap`内部类，该内部类有两个属性，一个就是传入的map对象，另外一个就是锁对象，默认情况下
锁对象就是`SynchronizedMap`，然后对传入map的操作时都会在外层加上synchronized代码块，锁对象默认就是`SynchronizedMap`本身，从而实现线程安全。

#### 3. ConcurrentHashMap
多线程读取元素快

#### 4. Vector

#### 5. Queue
多线程程序多用queue少考虑List

#### 6. TreeMap

#### 7. ConcurrentSkipListMap
利用跳表的数据结构

#### 8. CopyOnWriteArrayList
写时复制

#### 9. BlockingQueue
生产者/消费者

#### 10. DelayQueue/PriorityQueue
支持排序