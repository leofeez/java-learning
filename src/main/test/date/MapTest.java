package date;

import java.util.HashMap;

/**
 * @author leofee
 */
public class MapTest {

    /**
     * 测试目的：理解HashMap发生resize扩容的时候对于链表的优化处理：
     * 初始化一个长度为8的HashMap，因此threshold为6，所以当添加第7个数据的时候会发生扩容；
     * Map的Key为Integer，因为整数型的hash等于自身；
     * 由于hashMap是根据hash &（n - 1）来确定key所在的数组下标位置的，因此根据公式 m（m >= 1）* capacity + hash碰撞的数组索引下标index，可以拿到一组发生hash碰撞的数据；
     * 例如本例子capacity = 8， index = 7，数据为：15，23，31，39，47，55，63；
     * 有兴趣的读者，可以自己动手过后选择一组不同的数据样本进行测试。
     * 根据hash &（n - 1）， n = 8 二进制1000 扩容后 n = 16 二进制10000， 当8的时候由后3位决定位置，16由后4位。
     *
     * n - 1 :    0111  &  index  resize-->     1111  &  index
     * 15    :    1111  =  0111   resize-->     1111  =  1111
     * 23    :   10111  =  0111   resize-->    10111  =  0111
     * 31    :   11111  =  0111   resize-->    11111  =  1111
     * 39    :  100111  =  0111   resize-->   100111  =  0111
     * 47    :  101111  =  0111   resize-->   101111  =  1111
     * 55    :  110111  =  0111   resize-->   110111  =  0111
     * 63    :  111111  =  0111   resize-->   111111  =  1111
     *
     * 按照传统的方式扩容的话那么需要去遍历链表，然后跟put的时候一样对比key，==，equals，最后再放入新的索引位置；
     * 但是从上面数据可以发现原先所有的数据都落在了7的位置上，当发生扩容时候只有15，31，47，63需要移动（index发生了变化），其他的不需要移动；
     * 那么如何区分哪些需要移动，哪些不需要移动呢？
     * 通过key的hash值直接对old capacity进行按位与&操作如果结果等于0，那么不需要移动反之需要进行移动并且移动的位置等于old capacity + 当前index。
     *
     * hash & old capacity（8）
     * n     :    1000  &  index
     * 15    :    1111  =  1000
     * 23    :   10111  =  0000
     * 31    :   11111  =  1000
     * 39    :  100111  =  0000
     * 47    :  101111  =  1000
     * 55    :  110111  =  0000
     * 63    :  111111  =  1000
     *
     * 从下面截图可以看到通过源码中的处理方式可以拿到两个链表，需要移动的链表15->31->47->63，不需要移动的链表23->39->55；
     * 因此扩容的时候只需要把loHead放到原来的下标索引j（本例j=7），hiHead放到oldCap + j（本例为8 + 7 = 15）
     *
     * @param args
     */
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>(8);
        for (int i = 1; i <= 7; i++) {
            int sevenSlot = i * 8 + 7;
            map.put(sevenSlot, sevenSlot);
        }
    }
}
