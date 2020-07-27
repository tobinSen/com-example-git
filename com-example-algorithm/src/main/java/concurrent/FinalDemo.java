package concurrent;


/**
 * 那些数据可以作为共享数据？
 * 成员变量和静态变量---->因为多个线程都是实用的同一个对象和类来进行调用的，所以是共享的数据，而局部变量是在本地内存中的，调用完就消失了
 * <p>
 * JMM:读取主内存数据的时候，拉取到本地内存(工作内存)执行完数据后，在推送到主内存中供其他线程再次进行调用，这里的操作必须是串行化操作
 * volatile: 优化点在于将本地内存中引用了共享变量的数据让其失效，从新拉去主内存中的最新数据
 * <p>
 * 假设一个线程A执行writer()方法，随后另一个线程B执行reader()方法。
 * 通过这两个线程的交互来说明写final域的规则。下图是一种可能的执行时序：
 * <p>
 * <p>
 * <p>
 * 写普通域的操作可以被编译器重排序到了构造函数，
 * ①写普通域和③把这个对象的引用赋值给引用变量finalDemo重排序，导致读线程B错误的读取了普通变量a的值。
 * <p>
 * <p>
 * 写final域的操作不能重排序到了构造函数外，
 * ②写final域和③把这个对象的引用赋值给引用变量finalDemo不能重排序，读线程B正确的读取了final变量b的值
 * <p>
 * <p>
 * ReentrantLock + condition ---> synchronized + wait/notify
 * <p>
 * 1、获取到了锁，其他的线程就在等待队列中，获取到锁后面，进入到condition的队列中，当激活后再继续想下执行
 * 2、notify激活了被wait的线程，让其从条件队列中添加等待队列中，还是需要进行抢占，抢占到了就会在wait处继续向下执行
 */
public class FinalDemo {

    private int a;  // 普通域
    private final int b; // final域
    private static FinalDemo finalDemo;

    private FinalDemo() {
        a = 1; // ①写普通域丨
        b = 2; // ②写final域
    }

    public static void writer() {
        // 两个操作：
        // 1）构造一个FinalExample类型的对象，①写普通域a=1，②写final域b=2
        // 2）③把这个对象的引用赋值给引用变量finalDemo
        finalDemo = new FinalDemo();
    }

    public static void reader() {
        FinalDemo demo = finalDemo; // ④读对象引用
        int a = demo.a;    // ⑤读普通域
        int b = demo.b;    // ⑥读final域
    }
}
