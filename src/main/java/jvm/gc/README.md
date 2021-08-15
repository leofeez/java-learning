# Garbage Collector

## 什么是Garbage
没有任何引用指向一个对象或者多个对象（多个对象之间循环引用）

## 如何定位Garbage

- reference count: 引用计数，无法解决循环引用的问题
- root searching: 根可达算法，根对象包含 线程栈变量，静态变量，常量池，JNI指针

## 垃圾回收算法
- Mark sweep: 标记清除，容易内存碎片化
- Copying: 拷贝，将内存分为两份，当需要垃圾回收时，先将有效内存数据copy到另外一份，并且连续，然后将第一部门的内存整个回收 ，
  此算法存在内存浪费的情况。
- Mark compact: 标记压缩， 
