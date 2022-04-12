# Map

## HashMap

## HashTable

## SynchronizedMap
SynchronizedMap是Collections的一个静态内部类

## ConcurrentHashMap
支持高并发，在多线程的情况下读取效率高，但是插入的效率并不一定比HashTable或者Collections.synchronizedMap高

## ConcurrentSkipListMap
支持高并发，并且有序，排序利用的数据结构为跳表

## LinkedHashMap
在Hash表的基础上，增加了链表的特性，即每个Node节点拥有before和after的指针，可用于LRU缓存机制（最少使用的，优先过期）