package com.qizy.volatileexample;


import java.util.concurrent.TimeUnit;

public class VolatileVisible {

    public static void main(String[] args) {
//        new Thread(()-> {
//            System.out.println("thread start");
////            while (isRun){
////
////            }
//        },"t1").start();
        InnerRunnable thread = new InnerRunnable();

        new Thread(thread).start();
        // 一定要先主线程休眠下，不然其他线程还没启动 isRun已经修改了，观察不到效果
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread.setRun(false);
    }


}

class InnerRunnable implements Runnable{
    // 观察volatile 工作内存同步主内存的情况
    private
 //   volatile
    boolean isRun = true;
    int i = 0;
    @Override
    public void run() {
        System.out.println("start");
        while(isRun){
//            i++;
//            i--;
            // 执行变量赋值操作，不会让出CPU去同步主内存和副本内存
            // while死循环，占用了CPU的大量时间，代码在while死循环中增加了System.out.println()，
            // 由于是同步的，在IO过程中，CPU空闲时间比较多就有可能有时间去保证内存的可见性。
            System.out.println();
        }
        System.out.println("end");
    }

    public void setRun(boolean run) {
        this.isRun = run;
    }
}
