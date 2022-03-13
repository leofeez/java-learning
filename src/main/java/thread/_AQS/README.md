## AQS

AQS指的就是juc包下的AbstractQueuedSynchronizer缩写，在AQS中有两个比较重要的概念：

- state：其实就是共享资源，用volatile修饰，上锁的时候就需要利用CAS更改这个值，释放锁的时候就需要减少该值，当state为0的时候表示锁被释放。

- FIFO线程等待队列：在AQS中维护了一个双向的链表，每个节点Node就是线程节点，多线程竞争共享资源state失败后会加入到该队列中。

![img](./AQS.png)

在AbstractQueuedSynchronizer中，线程竞争后进入队列，或者锁被释放唤醒出队已经有了实现，而对竞争资源state的获取与释放需要对应的子类去实现，具体需要实现的方法如下：
- tryAcquire(int arg)：以独占的方式尝试去获取共享资源，成功则返回true，失败则返回false。
- tryRelease(int arg): 尝试去释放共享资源，成功则返回true，失败则返回false。
- tryAcquireShared(int arg)：以共享的方式尝试去获取共享资源，成功则返回true，否则返回false。
- tryReleaseShared(int arg)：以共享的方式尝试去释放共享资源，成功则返回true，否则返回false。
- isHeldExclusively()：判断当前线程是否以独占的方式占有共享资源，只有在用到Condition的时候才需要实现。

AQS最典型的实现就是ReentrantLock，在ReentrantLock中，有公平锁和非公平锁，所以对于占有共享资源的方式也不一样，这里以非公平锁为例：


至于为什么是双向链表，是因为需要看一下前一个节点的状态

对于AQS典型的实现就是ReentrantLock，在当前线程在加锁时，先通过CAS操作将state设置为1，如果CAS失败，则通过tryAcquire，在tryAcquire中第一步先判断state的值是否为0（这里因为state是volatile的，所以这一步判断可以提高效率），如果为0，则再次通过CAS操作将state设置为1，如果这时候CAS又失败了，则通过自旋CAS的方式（addWaiter->compareAndSetTail方法）加入到同步队列的尾部。