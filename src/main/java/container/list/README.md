#List
集合元素有序

主要有以下几个方法：
- add/addAll
- remove/removeAll
- set
- get

以上接口定义的方法在对应的子类实现机制是不一样的，下一面一一进行介绍

## ArrayList
底层实现是数组，初始容量为10，扩容机制为原来的1.5倍。

### 添加元素
- add(e)：在末尾添加元素，为O(1)
- add(index, e)：在指定下标位置添加元素，虽然定位插入位置为O(1)，但因为底层实现为数组，添加元素后，需要将后面的元素进行移动为O(n)，所以整体为O(n)
  
### 删除元素
- remove(index): 根据下标移除元素，查找元素复杂度为O(1)，但移除后需要将后面的元素往前移动，复杂度为O(n)，所以整体为O(n)
- remove(e):移除指定元素，复杂度为O(n)

### 修改元素
- set(index, e)

### 查找元素
- get(index): 根据下标获取元素，复杂度为O(1);

## LinkedList
底层实现是链表

### 添加元素
- add(e): 在链表尾部添加元素，返回true，复杂度为O(1)
- add(index, e)：在指定位置添加元素，需要遍历链表确定位置，所以复杂度为O(1)

### 删除元素
- remove(index): 删除指定位置的元素，需要遍历链表确定位置，所以复杂度为O(1)
- remove(e): 删除指定元素，需要遍历链表，所以复杂度为O(1)

## ArrayList 和 LinkedList 的区别
底层实现不同：ArrayList 底层实现是基于数组，LinkedList 底层实现是基于链表
数据存储方式：ArrayList 存储数据直接利用一个元素数组即可，而LinkedList 内部将元素封装为Node节点，并具有向前和向后的指针，所以LinkedList的占用空间也比ArrayList大

访问效率：ArrayList 基于数组，所以随机访问的效率高，并且数据元素的占用的空间是连续的，所以查询效率还是ArrayList更胜一筹。
添加/删除元素：如果是在尾部添加元素，那两者的效率是差不多的，但是如果是在中间进行元素的添加和删除，LinkedList效率要比ArrayList高，因为ArrayList
底层数组发生元素的增加删除，都需要移动后面的数据，而LinkedList只需要变动Node节点的指针即可。

### Vector
是线程安全的，每个方法都用了synchronized修饰

### CopyOnWriteList
写时复制，读不加锁，写是加锁的，适用于读多写少的情况