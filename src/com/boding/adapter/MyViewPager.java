package com.boding.adapter;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;

public class MyViewPager extends ViewPager {  
	  
    private Rect mRect = new Rect();//������¼��ʼλ��  
    private int pagerCount = 3;  
    private int currentItem = 0;  
    private boolean handleDefault = true;  
    private float preX = 0f;  
    private static final float RATIO = 0.5f;//Ħ��ϵ��  
    private static final float SCROLL_WIDTH = 30f;  
  
    public MyViewPager(Context context) {  
        super(context);  
    }  
  
    public MyViewPager(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    //�����ܹ��ж���ҳ,��ǵõ�����  
    public void setpagerCount(int pagerCount) {  
        this.pagerCount = pagerCount;  
    }  
  
    //���ǵ�ǰ�ǵڼ�ҳ������onPageSelect�����е�������  
    public void setCurrentIndex(int currentItem) {  
        this.currentItem = currentItem;  
    }  
      
    @Override  
    public boolean dispatchKeyEvent(KeyEvent event) {  
        return super.dispatchKeyEvent(event);  
    }  
  
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent arg0) {  
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {  
            preX = arg0.getX();//��¼���  
        }  
        return super.onInterceptTouchEvent(arg0);  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent arg0) {  
        switch (arg0.getAction()) {  
        case MotionEvent.ACTION_UP:  
            onTouchActionUp();  
            break;  
        case MotionEvent.ACTION_MOVE:  
            //��ʱ������һ����������һ���ʱ��  
            if ((currentItem == 0 || currentItem == pagerCount - 1)) {  
                float nowX = arg0.getX();  
                float offset = nowX - preX;  
                preX = nowX;  
                if (currentItem == 0) {  
                    if (offset > SCROLL_WIDTH) {//��ָ�����ľ�������趨ֵ  
                        whetherConditionIsRight(offset);  
                    } else if (!handleDefault) {//����������Ѿ����ֻ��������ˣ���ָ�����ָ������  
                        if (getLeft() + (int) (offset * RATIO) >= mRect.left) {  
                            layout(getLeft() + (int) (offset * RATIO), getTop(), getRight() + (int) (offset * RATIO), getBottom());  
                        }  
                    }  
                } else {  
                    if (offset < -SCROLL_WIDTH) {  
                        whetherConditionIsRight(offset);  
                    } else if (!handleDefault) {  
                        if (getRight() + (int) (offset * RATIO) <= mRect.right) {  
                            layout(getLeft() + (int) (offset * RATIO), getTop(), getRight() + (int) (offset * RATIO), getBottom());  
                        }  
                    }  
                }  
            } else {  
                handleDefault = true;  
            }  
  
            if (!handleDefault) {  
                return true;  
            }  
            break;  
  
        default:  
            break;  
        }  
        return super.onTouchEvent(arg0);  
    }  
    
    private boolean isDraw = false;
  //����canvas���󣬼�����Ĭ�ϵ���ʾ����  
    @Override  
    public void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        if(!mRect.isEmpty()){
//        	System.out.println(mRect.left + "   "+mRect.right + "   "+mRect.top + "    "+mRect.bottom);
//        	Paint paint = new Paint();
//        	paint.setColor(Color.WHITE);
//        	canvas.drawRect(mRect, paint);
//        	isDraw = true;
        }
    }
  
    private void whetherConditionIsRight(float offset) {  
        if (mRect.isEmpty()) {  
            mRect.set(getLeft(), getTop(), getRight(), getBottom());  
        }  
        handleDefault = false;  
        layout(getLeft() + (int) (offset * RATIO), getTop(), getRight() + (int) (offset * RATIO), getBottom());  
    }  
  
    private void onTouchActionUp() {  
        if (!mRect.isEmpty()) {  
            recoveryPosition();  
        }  
    }  
  
    private void recoveryPosition() {  
        TranslateAnimation ta = null;  
        ta = new TranslateAnimation(getLeft(), mRect.left, 0, 0);
        ta.setDuration(300);  
        startAnimation(ta);  
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);  
        mRect.setEmpty();  
        handleDefault = true;  
        isDraw = false;
    }  
  
}  