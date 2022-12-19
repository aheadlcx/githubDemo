package me.aheadlcx.github.test;

import android.os.Debug;
import android.util.Log;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/12/18 10:18 上午
 */
public class TestThread {
   private static final String TAG = "TestThread";

   public static void testThread2(){
   }

   public static void testThread(){
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
            Log.i("aheadlcx", "aheadlcx.run:---- ");
            try {
               Thread.sleep(2000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            Log.i("aheadlcx", "aheadlcx.run:++++++ ");
         }
      });
      thread.setName("lopend-thread");
      thread.start();
   }
}
