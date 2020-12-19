## 容器

### Collection

#### 1. HashTable
实现于Map接口，是一种存储key-value对的容器，的底层实现是基于哈希表，数组，链表，
- 继承于`Dictionary`类
- 是线程安全的，几乎所有的方法都加上了synchronized
- key 和 value 都不允许为 `null`

#### 2. HashMap
- 继承于 `AbstractMap`类
- 是非线程安全的
- key 和 value 可以为 `null`
- 

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