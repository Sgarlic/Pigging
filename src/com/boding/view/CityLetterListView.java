package com.boding.view;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CityLetterListView extends View {
	
	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	String[] letters = { "定位","历史","热门", 
			"A", "B", "C", "D", "E", "F", "G", 
			"H", "I", "J", "K", "L", "M", "N",
			"O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	int choosedLetterPos = -1;
	Paint paint = new Paint();
	boolean showBackground = false;

	public CityLetterListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CityLetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

	public CityLetterListView(Context context) {
		super(context);
	}
	
	public interface OnTouchingLetterChangedListener
	{
		public void onTouchingLetterChanged(String s);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (showBackground)
		{
			canvas.drawColor(R.color.white);
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / letters.length;
		for (int i = 0; i < letters.length; i++)
		{
			paint.setColor(R.color.darker_gray);
			paint.setTypeface(Typeface.SERIF);
			paint.setAntiAlias(true);
//			if (i == choosedLetterPos)
//			{
//				paint.setColor(Color.parseColor("#3399ff"));
//				paint.setFakeBoldText(true);
//			}
			float xPos = width / 2 - paint.measureText(letters[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(letters[i], xPos, yPos, paint);
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choosedLetterPos;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * letters.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBackground = true;
			if (oldChoose != c && listener != null)
			{
				if (c > 0 && c < letters.length)
				{
					listener.onTouchingLetterChanged(letters[c]);
					choosedLetterPos = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null)
			{
				if (c > 0 && c < letters.length)
				{
					listener.onTouchingLetterChanged(letters[c]);
					choosedLetterPos = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBackground = false;
			choosedLetterPos = -1;
			invalidate();
			break;
		}
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener)
	{
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
}