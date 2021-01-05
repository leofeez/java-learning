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
底层实现是哈希表，数组，链表，红黑树，当链表的长度大于等8时，链表会转换为红黑树结构。
- 继承于 `AbstractMap`类
- 非线程安全的
- key 和 value 可以为 `null`
- 默认的初始容量为16，负载因子为0.75，扩容的阈值为 capacity * loadFactor
- 扩容机制为原容量的两倍

HashMap在初始化建议设置初始容量大小，规则为 expectedSize/loadFactor + 1，而实际上HashMap会采用第一个大于该数值的2的幂作为初始化容量，
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