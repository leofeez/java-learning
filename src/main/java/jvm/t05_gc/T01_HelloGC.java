package jvm.t05_gc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author leofee
 * @date 2021/8/23
 */
public class T01_HelloGC {

    public static void main(String[] args) {
        System.out.println("Hello GC");
        List<byte[]> list = new LinkedList<>();

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        threadPool.submit(() -> {
            for (; ; ) {
                byte[] b = new byte[1024 * 1024];
                list.add(b);
                System.out.println(Thread.currentThread().getName() + "添加了");
            }
        });

        threadPool.execute(() -> {
            for (; ; ) {
                System.out.println(Thread.currentThread().getName());
                try {
                    TimeUnit.MILLISECONDS.sleep(100) ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


//    public static void main(String[] args) {
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 3, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2)) ;
//        pool.execute(() -> {
//            int i = 0 ;
//            for (;;) {
//                System.out.println(Thread.currentThread().getName() + ", i = " + (i++) + "," + pool) ;
//                try {
//                    TimeUnit.MILLISECONDS.sleep(50) ;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        pool.execute(() -> {
//            int j = 0 ;
//            for (;;) {
//                System.out.println(Thread.currentThread().getName() + ", j = " + (j++) + "," + pool) ;
//                try {
//                    TimeUnit.MILLISECONDS.sleep(50) ;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        pool.execute(() -> {
//            int k = 0 ;
//            List<byte[]> datas = new ArrayList<>() ;
//            for (;;) {
//                System.out.println(Thread.currentThread().getName() + ", k = " + (k++) + "," + pool) ;
//                byte[] buf = new byte[1024 * 100] ;
//                datas.add(buf) ;
//                try {
//                    TimeUnit.MILLISECONDS.sleep(20) ;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
