# Queue
Queue在Java中可以充当队列的（FIFO），是一种线性的数据结构，即一端进，另一端出。

在Queue接口中主要有以下方法：
- add: 在没有超出容量限制的情况下，返回true，否则抛出异常
- offer: 添加成功返回true，否则返回false，不会抛出异常
- remove：从队列的右边移除元素，如果队列中没有元素，则会抛出异常
- poll：从队列的右边移除元素，如果队列中没有元素，则返回null
- peek：查看队列的右边是否有元素，如果队列中没有元素，则返回null
- element: 查看队列的右边是否有元素，如果队列中没有元素，则抛出异常

## Deque
Queue还有个子接口为Deque，和Queue不同的是Deque支持左右两边都进行元素的添加和移除操作。那既然是支持两端操作，必然有First和Last的操作：

- addFirst(e)
- addLast(e)
- offerFirst(e)
- offerLast(e)

- removeFirst()
- removeLast()
- pollFirst()
- pollLast()

- getFirst()
- getLast()
- peekFirst()
- peekLast()

## BlockingQueue
BlockingQueue提供了线程安全的阻塞队列

- put(e) : 当试图往已经满了的队列中添加元素，就会阻塞
- take() : 当队列已经空了，就会阻塞

### LinkedBlockingQueue
容量最大为Integer.MAX_VALUE ,对线程友好的消费者/生产者模式：

### ArrayBlockingQueue
初始化时必须指定容量

## ConcurrentLinkedQueue
底层利用了CAS无锁的操作

## Queue 和 List 的区别
Queue的数据结构是一个队列，即从头部添加，从尾部移除，以先进先出的方式进行数据管理

## DelayQueue
按照在队列中等待的时间进行排序，适用场景就是按时间调度任务。

## PriorityQueue
支持排序的队列，可以指定Comparator，或者元素实现Comparable接口

## SynchronousQueue
put进元素之后就等待消费者去take，如果消费者不进行消费则主线程就会等待。所以容量会一直为0

## LinkedTransferQueue