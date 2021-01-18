# 容器

## Collection

### 1. HashTable

### 2. HashMap
实现于Map接口，是一种存储key-value对的容器。
底层数据结构为哈希表，数组，链表，红黑树（当链表的长度大于等8时，链表会转换为红黑树结构）。
数组和链表都有其各自的优点和缺点，数组连续存储，寻址容易，插入删除操作相对困难；而链表离散存储，寻址相对困难，而插入删除操作容易；
而HashMap结合了这两种数据结构，保留了各自的优点，又弥补了各自的缺点


#### HashMap重要的几个属性：
在了解HashMap底层实现原理之前，需要对HashMap中几个重要的属性有一定的认知，具体属性如下：

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

    // 扩容时的阈值，计算规则为 容量*负载因子
    int threshold;

    // 负载因子，用于计算当HashMap的容量达到多少进行扩容
    final float loadFactor;
```

#### HashMap的初始化
HashMap一共提供了三个构造方法，无参构造，指定初始容量和指定初始容量及负载因子的构造方法。

- 无参构造：所有的属性都取默认值，如loadFactory = 0.75F，默认的初始容量为`DEFAULT_INITIAL_CAPACITY`即16。
- 指定初始容量：负载因为为默认的0.75F，而指定初始容量之后，HashMap并没有直接将指定的初始容量作为真正的HashMap的容量，而是经过
计算获取一个比给定整数大或者等于的最接近的2的幂次方整数，如给定3，则设置为4，给定9，则设置为16。
- 指定初始容量及负载因子：其实该构造方法并不常用，因为负载因子的设定关系到扩容的时机，负载因子设置过大会导致HashMap的hash冲突比较严重，影响查询的效率，
设置过小，会导致频繁地进行扩容，影响添加元素的效率。
```java
    // 无参构造
    public HashMap() {
        // 负载因子默认0.75F
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }
    // 指定初始容量，负载因子默认0.75F
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    // 指定初始容量以及负载因子
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

#### HashMap容量为什么要设置为2的幂次方整数
HashMap其实就是哈希表，而哈希表数据结构的优点就是存储元素和查询元素的效率特别高，在不考虑hash冲突的情况下时间复杂度可以降为`O(1)`，
所以为了避免hash冲突，HashMap内部进行了很多处理，如容量设置为2的幂次方整数，具体见：HashMap的`tableSizeFor`。

```java
    static final int tableSizeFor(int cap) {
        // cap - 1 是为了防止cap已经是2的幂次方，导致下方位运算之后进行了翻倍
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
```
关于上述源码即可以拿到大于给定的`cap`最接近的2的幂次方整数。

关于HashMap的容量为什么要设置为2的幂次方整数，其实具体原因需要结合HashMap在put元素的操作，HashMap在putVal的时候需要计算table的index，
即entry桶在table的索引，计算index的原理就是利用(capacity -1) & hash，当容量capacity设置为2的幂次方时，(capacity-1)之后的二进制码
最高为之后可以保证全是1，如：
 - 容量为8， 对应的7 的二进制码为0000 0111
 - 容量为16，对应的15的二进制码为0000 1111
 
这样对hashCode进行按位与&计算之后可以保证结果既可以是基数也可以是偶数，而且结果和hashCode的后几位有关系，只要对应的key的hashCode算法足够好，就可以
有效减少hash碰撞的几率。

HashMap在初始化建议根据实际情况设置初始容量大小，实际上HashMap会采用第一个大于该数值的2的幂作为初始化容量，
指定初始化容量可以有效减少HashMap的扩容resize次数，因为在进行扩容时会导致重建hash表，重建hash表需要重新计算这些数据在新table数组中的位置并进行复制处理。

#### HashMap的哈希算法
在HashMap添加元素的时候需要根据key计算对应的hashCode然后再确定该元素对应的数组下标，计算下标的算法为`(capacity -1) & hash`，
其中`capacity`设置为2的幂次方整数是为了减少hash冲突的第一步，在HashMap的哈希算法中对key的hashCode也进行了一些处理，
HashMap的hash算法源码如下：
```java
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```
引用自[HashMap的hash算法](https://www.cnblogs.com/LiaHon/p/11149644.html)
为了减少hash碰撞的几率，HashMap并不是直接用key.hashCode()作为hash值，而是通过上述的hash(Object key)算法将
hashcode 与 hashcode的低16位做异或运算，混合了高位和低位得出的最终hash值，冲突的概率就小多了。

举个例子：
有个蒸笼，第一层是猪肉包、牛肉包、鸡肉包，第二层是白菜包，第三层是豆沙包，第四层是香菇包。这时你来买早餐，你指着第一层说除了猪肉包，随便给我一个包子，
因为外表无法分辨，这时拿到猪肉包的概率就有1/3，如果将二层、三层、四层与一层混合在一起了，那么拿到猪肉包的概率就小多了。

我们的hash(Object key)算法一个道理，最终的hash值混合了高位和低位的信息，掺杂的元素多了，那么最终hash值的随机性越大，
而HashMap的table下标依赖于最终hash值与capacity-1的&运算，这里的&运算类似于挑包子的过程，自然冲突就小得多了。

计算过程如下：

- 最开始的hashCode              `1111 1111 1111 1111 0100 1100 0000 1010`
-                                       >>> 无符号右移16位
- 右移16位的hashCode            `0000 0000 0000 0000 1111 1111 1111 1111`
-                                    hashCode ^ (hashCode >>> 16)
- 异或运算后的hash值             `1111 1111 1111 1111 1011 0011 1111 0101`
-                                      (capacity - 1) & hash
- capacity-1，如16，的hash值    `0000 0000 0000 0000 0000 0000 0000 1111`

- 得到最终的数组下标index         `0000 0000 0000 0000 0000 0000 0000 0101`

这样的过程叫做扰动函数。

#### HashMap添加元素的过程
HashMap对table的初始化是在第一次put元素的时候，HashMap进行put元素的过程如下：
1. table为空，为空则调用`resize()`方法初始化table数组。
2. table不为空，则根据key的`(table的长度-1) & hashCode`定位到table数组对应的元素
    - 如果table对应的元素为null，则直接放置在table对应的位置
    - 如果table对应的元素不为null，这时候说明产生了hash碰撞，而HashMap对hash碰撞的处理方式是利用链表或者红黑树，
        当table对应的元素为TreeNode，则放置到红黑树对应的节点，如果不是红黑树结构，这时候就需要遍历链表
        * 遍历链表时发现对应的key已经存在，则进行value覆盖
        * 遍历链表时发现对应的key不存在，则新建一个节点追加到链表的尾部，当追加后的链表长度大于树形阈值，则将链表转换为红黑树。
3. 添加元素后如果table的长度大于扩容的阈值threshold，则进行resize扩容。
```java
    public V put(K key, V value) {
        // 计算key对应的hashCode再put
        return putVal(hash(key), key, value, false, true);
    }
      
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        // 如果table为空则调用resize进行初始化
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;

        // 计算index，然后获取index位置所在元素
        // 如果index对应的元素为null
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 新建一个Node，将Node放置在table[index]位置
            tab[i] = newNode(hash, key, value, null);

        // 如果index对应的元素不为null
        else {
            Node<K,V> e; K k;
            // 则判断已经存在的元素的hashCode，key是否和新元素相等
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                // hashCode 和 key 都相等则直接覆盖
                e = p;
            // 如果已经存在的元素为红黑树的实例，则放置到树对应的节点
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            // 已经存在的元素的hashCode相等
            else {
                // 遍历链表
                for (int binCount = 0; ; ++binCount) {
                    // 下一个节点为null则追加到链表尾部（尾插法）
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        // 追加后链表的长度如果大于转换红黑树的阈值，则将链表转换为红黑树结构
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    // 如果遍历链表时发现链表元素的hashCode 和 key相等则直接覆盖
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            // 返回覆盖前的value
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    // 对value赋值
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        // size加1
        // size增加后如果大于扩容的阈值，则进行扩容
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```


#### 1.8 对HashMap的改进
- 1.7 中 HashMap的底层实现是数组 + 单链表，1.8底层实现增加了红黑树，当链表的长度大于等于8（默认）时将链表转换为红黑树。
- 1.7 中 HashMap的链表使用头插法，多线程的情况下可能会产生链表的闭环，导致死循环，1.8之后，链表添加元素使用尾插法。

#### HashMap和HashTable的区别
HashTable:
- 继承于`Dictionary`类
- 是线程安全的，几乎所有的方法都加上了synchronized
- key 和 value 都不允许为 `null`
- 默认的初始容量为 11，负载因子为0.75
- 扩容机制为原容量的两倍 + 1

HashMap:
- 继承于 `AbstractMap`类
- 非线程安全的
- key 和 value 可以为 `null`
- 默认的初始容量为16，负载因子为0.75，扩容的阈值为 capacity * loadFactor
- 扩容机制为原容量的两倍

### 3. Collections.synchronizedMap
`Collections.synchronizedMap`方法是jdk提供的用于返回一个线程安全的Map.
底层实现原理就是`Collections`里维护了一个`SynchronizedMap`内部类，该内部类有两个属性，一个就是传入的map对象，另外一个就是锁对象，默认情况下
锁对象就是`SynchronizedMap`，然后对传入map的操作时都会在外层加上synchronized代码块，锁对象默认就是`SynchronizedMap`本身，从而实现线程安全。

### 3. ConcurrentHashMap
多线程读取元素快

### 4. Vector

### 5. Queue
多线程程序多用queue少考虑List

### 6. TreeMap

### 7. ConcurrentSkipListMap
利用跳表的数据结构

### 8. CopyOnWriteArrayList
写时复制

### 9. BlockingQueue
生产者/消费者

### 10. DelayQueue/PriorityQueue
支持排序