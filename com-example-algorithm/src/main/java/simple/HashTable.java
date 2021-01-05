package simple;

public class HashTable {

    private static final int INIT_CAPACITY = 16;

    private static final float LOAD_FACTOR = 0.75F;

    private Entry[] table = new Entry[INIT_CAPACITY];
    private int size = 0; //散列表元素个数
    private int use = 0; //散列表使用地址个数


    public void put(int key, int value) {
        //1、先对key进行hash,然后获取这个table[] 中的索引
        int index = hash(key);
        if (table[index] == null) {
            //2、如果不存在就说明，该位置是空的，然后创建一个空的链表
            table[index] = new Entry(-1, -1, null);//数组中不存储值，只存储一个链表的指向
        }
        //3、定位到table[]中的位置索引
        Entry e = table[index];
        //4、这里说明是某个index位置第一次插入值
        if (e.next == null) {
            table[index].next = new Entry(key, value, null);
            size++;
            use++;
            //先加，然后判断这个加后的地址数是否超过了阈值
            if (use > table.length * LOAD_FACTOR) {
                //这里进行扩容
                resize();
            }
        } else {
            //5、找到链表中key相同的然后进行覆盖
            for (e = e.next; e != null; e = e.next) {
                int k = e.key;
                if (k == key) {
                    e.value = value;
                    return;
                }
            }
            Entry temp = table[index].next;
            //6、这里使用的是头插法
            Entry newEntry = new Entry(key, value, temp);
            table[index].next = newEntry;
            size++;
        }
    }

    public int get(int key) {
        int index = hash(key);
        //存在
        if (table[index] != null) {
            Entry e;
            for (e = table[index].next; e != null; e = e.next) {
                if (e.key == key) {
                    return e.value;
                }
            }
            return -1;
        }
        //不存在
        return -1;
    }

    private int hash(int key) {
        return key % table.length;
    }

    private void resize() {
        int newLength = table.length * 2; //2倍扩容
        Entry[] oldTable = table;
        table = new Entry[newLength];
        use = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && oldTable[i].next != null) {
                Entry e = oldTable[i];
                while (null != e.next) {
                    Entry next = e.next;
                    int index = hash(next.key);
                    if (table[index] == null) {
                        use++;
                        table[index] = new Entry(-1, -1, null);
                    }
                    Entry temp = table[index].next;
                    Entry newEntry = new Entry(next.key, next.value, temp);
                    table[index].next = newEntry;

                    e = next;
                }
            }
        }
    }

}
