package com.boding.view.dialog;

import java.io.InputStream;

import com.boding.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

public class GIFView extends View {
	private Movie mMovie;
	private long movieStart;
    public GIFView(Context context) {
        super(context);
        initializeView();
    }
 
    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }
 
    public GIFView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }
    
    private void initializeView() {
        //R.drawable.loader - our animated GIF
        InputStream is = getContext().getResources().openRawResource(R.drawable.loadingpic);
        mMovie = Movie.decodeStream(is);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight() - mMovie.height());
            this.invalidate();
        }
    }
}
