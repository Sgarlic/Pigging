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
	private Movie movie;
	private long movieStart;
    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }
    
    private void initializeView() {
        //R.drawable.loader - our animated GIF
        InputStream is = getContext().getResources().openRawResource(R.drawable.loadingpic);
        movie = Movie.decodeStream(is);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	long now = android.os.SystemClock.uptimeMillis();   
        
        if (movieStart == 0) { // first time   
        	movieStart = now;   
        }   
        if (movie != null) {   
              
            int dur = movie.duration();   
            if (dur == 0) {   
                dur = 1000;   
            }   
            int relTime = (int) ((now - movieStart) % dur);                  
            movie.setTime(relTime);   
            movie.draw(canvas, 0, 0);   
            invalidate();   
        }   
    }
}
