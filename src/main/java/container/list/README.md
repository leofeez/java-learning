## Collection

### List

### Vector
是线程安全的，每个方法都用了synchronized修饰

### CopyOnWriteList
写时复制，读不加锁，写是加锁的，适用于读多写少的情况