package me.aheadlcx.sp;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description:
 * author: aheadlcx
 * Date:2023/3/19 7:39 下午
 */
public class SpHelper {
    private static final String TAG = "SpHelper";
    private static boolean init = false;
    private static String CLASS_QUEUED_WORK = "android.app.QueuedWork";
    private static String FIELD_PENDING_FINISHERS = "sPendingWorkFinishers";
    private static ConcurrentLinkedQueue<Runnable> sPendingWorkFinishers = null;

    public static void beforeSpBlock(String tag){
        if(!init){
            getPendingWorkFinishers();
            init = true;
        }
        Log.d(TAG,"beforeSpBlock "+tag);
        if(sPendingWorkFinishers != null){
            int size = sPendingWorkFinishers.size();
            Log.i(TAG, "beforeSpBlock: size=" + size);
            sPendingWorkFinishers.clear();
        }
    }

    private static void getPendingWorkFinishers() {
        Log.d(TAG,"getPendingWorkFinishers");
        try {
            Class clazz = Class.forName(CLASS_QUEUED_WORK);
            Field field = clazz.getDeclaredField(FIELD_PENDING_FINISHERS);
            field.setAccessible(true);
            sPendingWorkFinishers = (ConcurrentLinkedQueue<Runnable>) field.get(null);
            Log.d(TAG,"getPendingWorkFinishers success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable e){
            e.printStackTrace();
        }

    }
}
