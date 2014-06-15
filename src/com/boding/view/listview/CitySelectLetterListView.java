package com.boding.view.listview;

import com.boding.R;

import android.R.style;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class CitySelectLetterListView extends View {
	
	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	String[] cityLetters = {"","定位","历史","热门","A","B","C","D","E","F","G","H","I","J","K","L"
			,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	int choose = -1;
	Paint paint = new Paint();
//	boolean showBkg = false;

	public CitySelectLetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CitySelectLetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CitySelectLetterListView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		if(showBkg){
//		    canvas.drawColor(Color.parseColor("#40000000"));
//		}
		
	    int height = getHeight();
	    int width = getWidth();
	    int singleHeight = height / cityLetters.length;
	    for(int i=0;i<cityLetters.length;i++){
	       paint.setColor(getResources().getColor(R.color.panelOrange));
	       Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
	       paint.setTypeface(font);
	       paint.setAntiAlias(true);
	       if(i == choose){
	    	   paint.setColor(getResources().getColor(R.color.panelOrange));
	    	   paint.setFakeBoldText(true);
	       }
	       float xPos = width/2  - paint.measureText(cityLetters[i])/2;
	       float yPos = singleHeight * i + singleHeight;
	       canvas.drawText(cityLetters[i], xPos, yPos, paint);
	       paint.reset();
	    }
	   
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
	    final float y = event.getY();
	    final int oldChoose = choose;
	    final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
	    final int c = (int) (y/getHeight()*cityLetters.length);
	    
		switch (action) {
			case MotionEvent.ACTION_DOWN:
//				showBkg = true;
				if(oldChoose != c && listener != null){
					if(c > 0 && c< cityLetters.length){
						listener.onTouchingLetterChanged(cityLetters[c]);
						choose = c;
						invalidate();
					}
				}
				
				break;
			case MotionEvent.ACTION_MOVE:
				if(oldChoose != c && listener != null){
					if(c > 0 && c< cityLetters.length){
						listener.onTouchingLetterChanged(cityLetters[c]);
						choose = c;
						invalidate();
					}
				}
				break;
			case MotionEvent.ACTION_UP:
//				showBkg = false;
				choose = -1;
				invalidate();
				break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener{
		public void onTouchingLetterChanged(String s);
	}
	
}
