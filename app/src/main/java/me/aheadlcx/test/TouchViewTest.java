package me.aheadlcx.test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description:
 * author: aheadlcx
 * Date:2023/2/16 11:59 上午
 */
class TouchViewTest extends View {

   public TouchViewTest(Context context) {
      super(context);
   }

   public TouchViewTest(Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
   }

   public TouchViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   public TouchViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
      super(context, attrs, defStyleAttr, defStyleRes);
   }

   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
   }
}
