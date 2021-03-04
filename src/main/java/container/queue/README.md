# Queue
在多线程的情况少用List，多用Queue

## ConcurrentLinkedQueue
poll()：底层利用了CAS无锁的操作

## LinkedBlockingQueue
对线程友好的消费者/生产者模式：
put:当容器满了线程就会阻塞
take：当容器空了，线程就会阻塞


## Queue 和 List 的区别

## DelayQueue
按照在队列中等待的时间进行排序，适用场景就是按时间调度任务。

## PriorityQueue
排序

## SynchronousQueue
put进元素之后就等待消费者去take，如果消费者不进行消费则主线程就会等待。所以容量会一直为0

## LinkedTransferQueue