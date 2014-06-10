package com.boding.view;

import java.util.ArrayList;
import java.util.List;

import com.boding.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.view.ViewGroup;

public class MonthSelectSpinner extends Spinner implements OnItemClickListener{
	public static MonthSelectDialog monthDialog = null;  
    private List<String> monthList;  
    public static String text;  
    private String currentMonth = "12月";
    private String currentYear = "今年";

	public MonthSelectSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);

		monthList = new ArrayList<String>();
		monthList.add("1月");
		monthList.add("2月");
		monthList.add("3月");
		monthList.add("4月");
		monthList.add("5月");
		monthList.add("6月");
		setAdapter();
	}
	
	private void setAdapter(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item, monthList);
		adapter.setDropDownViewResource(R.layout.spinner_dialog_month_select);
//		     @Override  
//		     public View getDropDownView(int position, View convertView, ViewGroup parent) {  
//		       if(convertView==null){  
//		    	   convertView = LayoutInflater.from(MonthSelectSpinner.this.getContext()).inflate(R.layout.spinner_dialog_month_select, parent, false);  
//		       }  
//		       TextView label = (TextView) convertView.findViewById(R.id.current_month_selector_dialog_textView);  
//		       label.setText(getItem(position));  
//		         
//		       	return convertView;  
//		       }  
//		    };   
		this.setAdapter(adapter);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
	    int width = getWidth();
	    Paint bgPaint = new Paint();
	    bgPaint.setColor(this.getResources().getColor(R.color.white));
	    canvas.drawRect(0, 0, width, height, bgPaint);
	    
	    // draw year
	    Paint paint = new Paint();
	 	String yearS1 = currentYear.substring(0,1);
	 	float textSize = 15;
	 	paint.setColor(this.getResources().getColor(R.color.calendarCurrentMonthTodayHintColor));
	 	paint.setTextSize(textSize);
	 	paint.setTypeface(Typeface.SANS_SERIF);
	 	float yearY = height * 1 / 2f;
	 	float yearX = 0;
	 	float yearXRight = paint.measureText(yearS1);
		canvas.drawText(yearS1, yearX, yearY, paint);
	 		
	 	String yearS2 = currentYear.substring(1,2);
	 	yearY = height  * 3 / 4f;
 		canvas.drawText(yearS2, yearX, yearY, paint);
	   
	    
	    //draw month
		textSize = 50;
		paint.setColor(this.getResources().getColor(R.color.calendarCurrentMonthTodayHintColor));
		Typeface monthFont = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		paint.setTextSize(textSize);
		paint.setTypeface(monthFont);
		float monthY = height * 4 / 5f;
		float monthX = yearXRight + 5.0f ;
		
		float monthXRight = monthX + paint.measureText(currentMonth);
		canvas.drawText(currentMonth, monthX, monthY, paint);
	    
		// draw icon
		float iconY = height * 2 / 5f;
		float iconX = monthXRight + 10;
		Bitmap  bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.datechoice_2x);
		canvas.drawBitmap(bitmap, iconX, iconY, paint);
	}
	

	@Override
	public void onItemClick(AdapterView<?> view, View itemView, int position, long id) {
		
		
	}
	
	
	
//	//如果视图定义了OnClickListener监听器，调用此方法来执行  
//    @Override  
//    public boolean performClick() {  
////    	Log.d("poding","clicked");
////        Context context = getContext();  
////        final LayoutInflater inflater = LayoutInflater.from(getContext());  
////        final View view = inflater.inflate(R.layout.spinner_dialog_month_select, null);  
////        final ListView listview = (ListView) view  
////                .findViewById(R.id.formcustomspinner_list);  
////        ListviewAdapter adapters = new ListviewAdapter(context, getList());  
////        listview.setAdapter(adapters);  
////        listview.setOnItemClickListener(this);  
////        dialog = new SelectDialog(context, R.style.dialog);//创建Dialog并设置样式主题  
////        LayoutParams params = new LayoutParams(650, LayoutParams.FILL_PARENT);  
////        dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog  
////        dialog.show();  
////        dialog.addContentView(view, params);  
//        return true;  
//    } 

	private class MonthSelectDialog extends AlertDialog{

		protected MonthSelectDialog(Context context) {
			super(context);
		}

		@Override  
		protected void onCreate(Bundle savedInstanceState) {  
			 super.onCreate(savedInstanceState);  
		     // setContentView(R.layout.slt_cnt_type);  
	    } 
	}
}
