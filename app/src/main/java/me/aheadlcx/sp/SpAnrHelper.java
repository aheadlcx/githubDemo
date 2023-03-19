package me.aheadlcx.sp;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description:
 * author: aheadlcx
 * Date:2023/3/19 6:46 下午
 */
public class SpAnrHelper {
   private static final String TAG = "SpAnrHelper";

   public static void tryHackActivityThreadH(){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
         //8.0 以及以上版本，暂时不处理，anr概率相对低点
         return;
      }
      try {
         Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
         Method currentAtyThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
         Object activityThread = currentAtyThreadMethod.invoke(null);

         Field mHField = activityThreadClass.getDeclaredField("mH");
         mHField.setAccessible(true);
         Handler handler = (Handler) mHField.get(activityThread);

         Field mCallbackField = Handler.class.getDeclaredField("mCallback");
         mCallbackField.setAccessible(true);
         mCallbackField.set(handler,new SpCompatCallback());
         Log.d(TAG,"hook success");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (NoSuchFieldException e) {
         e.printStackTrace();
      } catch (NoSuchMethodException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      } catch (InvocationTargetException e) {
         e.printStackTrace();
      } catch (Throwable e){
         e.printStackTrace();
      }
   }

}
