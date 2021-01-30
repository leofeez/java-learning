## ThreadLocal
ThreadLocal叫做线程变量，意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的。ThreadLocal为变量在每个线程中都创建了
一个副本，那么每个线程可以访问自己内部的副本变量。

### 用途
- 线程间的数据隔离，实现线程安全：如SimpleDateFormat
- 用于存储事务信息，如Spring的声明式事务，保证拿到的都是同一个Connection从而形成一个完整的事务。
- 对象跨层传递：如存储用户信息，UserInfo，防止层与层之间多余的传递。

### 原理
每个`Thread`线程对象都持有一个`ThreadLocalMap`变量用来存储线程内部的一些属性，如下：
```java
    /* ThreadLocal values pertaining to this thread. This map is maintained
     * by the ThreadLocal class. */
    ThreadLocal.ThreadLocalMap threadLocals = null;
```
真正用来保存数据的就是`ThreadLocal.ThreadLocalMap`，是ThreadLocal的静态内部类底层用的Entry数组对象存储数据，其实就是一个个的key-value结构：
```java
public class ThreadLocal<T> {

    // 静态内部类
    static class ThreadLocalMap {
        /**
         * The table, resized as necessary.
         * table.length MUST always be a power of two.
         */
        private Entry[] table;
        
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            // key为ThreadLocal对象
            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
    }
}
```
在通过`set()` 或者 `get()`方法其实操作的都是ThreadLocalMap这个对象，

`set()`方法源码如下：
```java
    public void set(T value) {
        // 1. 获取当前线程对象
        Thread t = Thread.currentThread();
        // 2. 获取当前线程对象的 threadLocals，即t.threadLocals
        ThreadLocalMap map = getMap(t);
        if (map != null)
            // 以 ThreadLocal对象为key，将对应的value放入到Entry中
            map.set(this, value);
        else
            // 创建ThreadLocalMap，即 new ThreadLocalMap();
            createMap(t, value);
    }
```

`get()` 方法源码如下：
```java

    public T get() {
        // 1.当前线程
        Thread t = Thread.currentThread();
        // 2.当前线程的线程变量map
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            // 根据当前ThreadLocal为key获取Entry对象
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                // 返回Entry中的value
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
```

### WeakReference在ThreadLocal中的应用：
从上面的ThreadLocal定义中可以看到最底层的Entry是继承于WeakReference，在set()方法中向ThreadLocalMap设置值的时候，
如果在Entry数组中不存在对应的key，则会new Entry(key, value)，添加到Entry数组中：
```java
    public void set(T value) {
        // 1. 获取当前线程对象
        Thread t = Thread.currentThread();
        // 2. 获取当前线程对象的 threadLocals，即t.threadLocals
        ThreadLocalMap map = getMap(t);
        if (map != null)
            // 向ThreadLocalMap中set值
            map.set(this, value);
        else
            // 创建ThreadLocalMap，即 new ThreadLocalMap();
            createMap(t, value);
    }

    private void set(ThreadLocal<?> key, Object value) {
            // entry 数组
            Entry[] tab = table;
            int len = tab.length;
            // 根据key的hash值确定数组下标
            int i = key.threadLocalHashCode & (len-1);
            // 遍历entry数组
            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();
                
                // key已经存在则直接覆盖
                if (k == key) {
                    e.value = value;
                    return;
                }
                // key 不存在则new Entry(key, value)
                if (k == null) {
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }
    
            tab[i] = new Entry(key, value);
            int sz = ++size;
            if (!cleanSomeSlots(i, sz) && sz >= threshold)
                rehash();
    }
```
由于Entry继承于WeakReference，在new Entry(key, value)的时候会调用父类的构造方法：
```java
        Entry(ThreadLocal<?> k, Object v) {
            // 调用WeakReference的构造方法
            super(k);
            value = v;
        }
```
调用WeakReference的构造方法表示key即ThreadLocal对象是被一个弱引用指向，所以当外部的tl指向设置为null，只剩下弱引用指向ThreadLocal对象，
最后就会自动被回收。

### 内存泄漏
如果Entry的key不是弱引用，即使外部的tl设置为null，这时候ThreadLocal对象依旧被key强引用指向(原因为：当引用传递设置为null时无法影响传递内的结果)，
则不会被回收，就会发生内存泄漏。
还有，由于ThreadLocalMap是线程对象的一个变量，如果在有线程池的情况下，由于线程并不是执行完就立马释放，所以线程中的ThreadLocalMap是一直
存在的，所以当key被回收时，即key为null了，value这时候就永远都访问不到了，也就一直无法被回收，所以在使用ThreadLocal后如果对应的value已经
不需要了，需要手工`remove()`;