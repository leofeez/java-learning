# Set
是Collection的子接口，与List不同的是，Set中的元素都是不重复的；
底层由HashMap实现
```java
    public boolean add(E e) {
        // map就是一个hashMap, PRESENT 就是一个虚拟值
        return map.put(e, PRESENT)==null;
    }
```

## TreeSet
默认按照自然顺序进行排序，也可以支持传入Comparator比较器，或者元素实现Comparable
底层利用TreeMap

## LinkedHashSet
