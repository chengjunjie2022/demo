package site.chengjunjie.demo.java.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;

public class TwinsLockTest {
    @Test
    public void test(){
        final Lock lock = new TwinsLock();

        // 定义线程
        class Worker extends Thread{
            @Override
            public void run() {
                while (true){
                    lock.lock();
                    try {
                        Thread.currentThread().sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                        Thread.currentThread().sleep(1000);
                    } catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }

        //启动线程
        for(int i = 0 ; i < 10 ; i++){
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        //每隔1s换行
        for(int i = 0 ; i < 10 ; i++){
            try {
                Thread.currentThread().sleep(1000);
                System.out.println();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
