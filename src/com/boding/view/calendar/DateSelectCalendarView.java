package com.boding.view.calendar;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.boding.R;
import com.boding.constants.GlobalVariables;
import com.boding.util.DateUtil;

/**
 * 日历控件 功能：获得点选的日期区间
 * 
 */
public class DateSelectCalendarView extends View implements View.OnTouchListener {
	private final static String TAG = "anCalendar";
	private Date selectedStartDate;
	private Date selectedEndDate;
	private Date minClickableDate; // 设置可以点击的最小日期，该日期之前的日期为灰色
	private Date maxDate; // 设置可以显示的最大日期【整个月都可以显示】
	private Date curDate; // 当前日历显示的月
	private Date today; // 今天的日期文字显示红色
	private Date downDate; // 手指按下状态时临时日期
	private Date showFirstDate, showLastDate; // 日历显示的第一个日期和最后一个日期
	private int downIndex; // 按下的格子索引
	private Calendar calendar;
	private Surface surface;
	private int[] date = new int[42]; // 日历显示数字
	private int curStartIndex, curEndIndex; // 当前显示的日历起始的索引
	Resources resource = this.getResources();
	//private boolean completed = false; // 为false表示只选择了开始日期，true表示结束日期也选择了
	//给控件设置监听事件
	private OnItemClickListener onItemClickListener;
	
	public DateSelectCalendarView(Context context) {
		super(context);
		init();
	}

	public DateSelectCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		curDate = selectedStartDate = selectedEndDate = today = new Date();
		minClickableDate = DateUtil.getLastDate(curDate);
		Calendar minDateCalendar = Calendar.getInstance();
		minDateCalendar.add(Calendar.MONTH, 5);
		maxDate = minDateCalendar.getTime();
		calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		surface = new Surface();
		surface.density = getResources().getDisplayMetrics().density;
		setBackgroundColor(surface.bgColor);
		setOnTouchListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		surface.width = getResources().getDisplayMetrics().widthPixels;
		surface.height = (int) (getResources().getDisplayMetrics().heightPixels*1/2) + (int)surface.monthHeight;
//		if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
//			surface.width = View.MeasureSpec.getSize(widthMeasureSpec);
//		}
//		if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
//			surface.height = View.MeasureSpec.getSize(heightMeasureSpec);
//		}
		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width,
				View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height,
				View.MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			surface.init();
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;
		// 星期背景
		for (int i = 0; i < surface.weekText.length; i++) {
			float weekTextX = i
					* surface.cellWidth
					+ (surface.cellWidth - surface.weekPaint
							.measureText(surface.weekText[i])) / 2f;
			canvas.drawText(surface.weekText[i], weekTextX, weekTextY,
					surface.weekPaint);
		}
		
		// 计算日期
		calculateDate();
		// 按下状态，选择状态背景色
		drawDownOrSelectedBg(canvas);
		
		// 画框
		canvas.drawPath(surface.boxPath, surface.borderPaint);
		/**
		 * 获得今天的索引
		 */
		int todayIndex = -1;
		calendar.setTime(curDate);
		calendar.setTime(today);
		if (DateUtil.compareYearAndMonth(curDate, today) == 0) {
			int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
			todayIndex = curStartIndex + todayNumber - 1;
		}
		/**
		 * 获得出发日期的索引
		 */
		int fromDateIndex = -1;
		if(GlobalVariables.Fly_From_Date!=null){
			Date fromDate = DateUtil.getDateFromString(GlobalVariables.Fly_From_Date);
			if(DateUtil.compareYearAndMonth(curDate, fromDate) == 0){
				Calendar fromDateCalendar = Calendar.getInstance();
				fromDateCalendar.setTime(fromDate);
				int fromDateNumber = fromDateCalendar.get(Calendar.DAY_OF_MONTH);
				fromDateIndex = curStartIndex + fromDateNumber - 1;
			}
		}
		/**
		 * 获得返程日期的索引
		 */
		int returnDateIndex = -1;
		if(GlobalVariables.Fly_Return_Date!=null && GlobalVariables.isRoundWaySelection){
			Date returnDate = DateUtil.getDateFromString(GlobalVariables.Fly_Return_Date);
			if(DateUtil.compareYearAndMonth(curDate, returnDate) == 0){
				Calendar returnDateCalendar = Calendar.getInstance();
				returnDateCalendar.setTime(returnDate);
				int returnDateNumber = returnDateCalendar.get(Calendar.DAY_OF_MONTH);
				returnDateIndex = curStartIndex + returnDateNumber - 1;
			}
		}
		/**
		 * 获得可以点击的最小日期的索引
		 */
		int minClickableDateIndex = -1;
		int compareMinDateResult = DateUtil.compareYearAndMonth(curDate, minClickableDate);
		if( compareMinDateResult== 0){
			Calendar minClickableDateCalendar = Calendar.getInstance();
			minClickableDateCalendar.setTime(minClickableDate);
			int minClickableDateNumber = minClickableDateCalendar.get(Calendar.DAY_OF_MONTH);
			minClickableDateIndex = curStartIndex + minClickableDateNumber - 1;
		}else if( compareMinDateResult == -1)
			minClickableDateIndex = 42;
		
		/**
		 * 获得可以点击的最小日期的索引
		 */
		int maxClickableDateIndex = 42;
		int compareMaxDateResult = DateUtil.compareYearAndMonth(curDate, maxDate);
		if( compareMaxDateResult== 0){
			Calendar maxDateResultCalendar = Calendar.getInstance();
			maxDateResultCalendar.setTime(maxDate);
			int maxClickableNumber = maxDateResultCalendar.get(Calendar.DAY_OF_MONTH);
			maxClickableDateIndex = curStartIndex + maxClickableNumber - 1;
		}else if( compareMaxDateResult == 1)
			maxClickableDateIndex = -1;
		
		/**
		 * 绘制日期
		 */
		for (int i = 0; i < 42; i++) {
			int textColor = surface.textColor;
			String hintText = null;
			int hintTextColor = 0;
			// 上一个月下一个月都是灰色
			if (isLastMonth(i) || isNextMonth(i)) {
				textColor = resource.getColor(R.color.calendarNextMonthTextColor);
			} 
			
			if (todayIndex != -1 && todayIndex == i){
				textColor = resource.getColor(R.color.textOrange);
			}

			// 今天是橙色
			if (todayIndex != -1 && i == todayIndex) {
				textColor = surface.todayNumberColor;
				hintText ="今天";
			}
			
			// 已经选择的出发日期是橙色背景和白色字
			if (fromDateIndex!=-1 && fromDateIndex == i){
				textColor = resource.getColor(R.color.white);
				drawCellBg(canvas, i , resource.getColor(R.color.calendarCurrentMonthFlightDayColor));
				hintText ="出发";
			}
			
			if (returnDateIndex!=-1 && returnDateIndex == i){
				textColor = resource.getColor(R.color.white);
				drawCellBg(canvas, i , resource.getColor(R.color.calendarCurrentMonthFlightDayColor));
				hintText ="返回";
			}
			
			// 小于最小可点击日期的是灰色背景不可点击
			if ((minClickableDateIndex!=-1 && i < minClickableDateIndex)||(maxClickableDateIndex != 42 && i > maxClickableDateIndex)){
				textColor = resource.getColor(R.color.calendarPreMonthTextColor);
				drawCellBg(canvas, i , resource.getColor(R.color.calendarPreMonthBgColor));
			}
			drawCellText(canvas, i, date[i] + "", textColor, hintText);
		}
		super.onDraw(canvas);
	}

	private void calculateDate() {
		calendar.setTime(curDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int monthStart = dayInWeek;
		if (monthStart == 1) {
			monthStart = 8;
		}
		monthStart -= 1;  //以日为开头-1，以星期一为开头-2
		curStartIndex = monthStart;
		date[monthStart] = 1;
		// last month
		if (monthStart > 0) {
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
			for (int i = monthStart - 1; i >= 0; i--) {
				date[i] = dayInmonth;
				dayInmonth--;
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[0]);
		}
		showFirstDate = calendar.getTime();
		// this month
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		// calendar.get(Calendar.DAY_OF_MONTH));
		int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
		for (int i = 1; i < monthDay; i++) {
			date[monthStart + i] = i + 1;
//			Log.d("month",String.valueOf(monthStart + i)+":"+String.valueOf(date[monthStart + i]));
		}
		curEndIndex = monthStart + monthDay;
		// next month
		for (int i = monthStart + monthDay; i < 42; i++) {
			date[i] = i - (monthStart + monthDay) + 1;
//			Log.d("nextmonth",String.valueOf(i)+":"+String.valueOf(date[i]));
		}
		if (curEndIndex < 42) {
			// 显示了下一月的
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_MONTH, date[41]);
		showLastDate = calendar.getTime();
	}

	/**
	 * 
	 * @param canvas
	 * @param index
	 * @param text
	 */
	private void drawCellText(Canvas canvas, int index, String text, int color, String hintText) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.datePaint.setColor(color);
		float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.cellHeight * 2 / 5f;
		float cellX = (surface.cellWidth * (x - 1))
				+ (surface.cellWidth - surface.datePaint.measureText(text))
				/ 2f;
		canvas.drawText(text, cellX, cellY, surface.datePaint);
		
		if(hintText != null){
			cellY = surface.monthHeight + surface.weekHeight + (y - 1)
					* surface.cellHeight + surface.cellHeight * 4 / 5f ;
			cellX = (surface.cellWidth * (x - 1))
					+ (surface.cellWidth - surface.datePaint.measureText(hintText))
					/ 2f;
			canvas.drawText(hintText, cellX, cellY, surface.datePaint);
		}
	}

	/**
	 * 
	 * @param canvas
	 * @param index
	 * @param color
	 */
	private void drawCellBg(Canvas canvas, int index, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.cellBgPaint.setColor(color);
		float left = surface.cellWidth * (x - 1);
		float top = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight;
		canvas.drawRect(left, top, left + surface.cellWidth + 1, top + surface.cellHeight, surface.cellBgPaint);
	}
	
	private void drawMonthContent(Canvas canvas, String currentMonth, String currentYear){
		// draw month
		float textSize = 50;
		Typeface monthFont = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		surface.monthContentPaint.setTextSize(textSize);
		surface.monthContentPaint.setTypeface(monthFont);
		float monthY = surface.monthHeight  * 4 / 5f;
		float monthX = (surface.width - surface.monthContentPaint.measureText(currentMonth))/2f;
		surface.monthSelectorStart = monthX;
		float monthXRight = monthX + surface.monthContentPaint.measureText(currentMonth);
		canvas.drawText(currentMonth, monthX, monthY, surface.monthContentPaint);
		
		
		// draw year
		String yearS1 = currentYear.substring(0,1);
		textSize = 15;
		surface.monthContentPaint.setColor(resource.getColor(R.color.calendarCurrentMonthTodayHintColor));
		surface.monthContentPaint.setTextSize(textSize);
		surface.monthContentPaint.setTypeface(Typeface.SANS_SERIF);
		float yearY = surface.monthHeight  * 1 / 2f;
		float yearX = monthX - surface.monthContentPaint.measureText(yearS1) - 15;
		canvas.drawText(yearS1, yearX, yearY, surface.datePaint);
		
		String yearS2 = currentYear.substring(1,2);
		yearY = surface.monthHeight  * 3 / 4f;
		canvas.drawText(yearS2, yearX, yearY, surface.datePaint);

		// draw icon
		float iconY = surface.monthHeight  * 2 / 5f;
		float iconX = monthXRight + 15;
		surface.monthSelectorEnd = iconX + 20;
		Bitmap  bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.arrow_down_orange_small);
		canvas.drawBitmap(bitmap, iconX, iconY, surface.datePaint);
		
	}

	private void drawDownOrSelectedBg(Canvas canvas) {
		// down and not up
		if (downDate != null) {
			drawCellBg(canvas, downIndex, surface.cellDownColor);
		}
		// selected bg color
		if (!selectedEndDate.before(showFirstDate)
				&& !selectedStartDate.after(showLastDate)) {
			int[] section = new int[] { -1, -1 };
			calendar.setTime(curDate);
			calendar.add(Calendar.MONTH, -1);
			findSelectedIndex(0, curStartIndex, calendar, section);
			if (section[1] == -1) {
				calendar.setTime(curDate);
				findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
			}
			if (section[1] == -1) {
				calendar.setTime(curDate);
				calendar.add(Calendar.MONTH, 1);
				findSelectedIndex(curEndIndex, 42, calendar, section);
			}
			if (section[0] == -1) {
				section[0] = 0;
			}
			if (section[1] == -1) {
				section[1] = 41;
			}
			for (int i = section[0]; i <= section[1]; i++) {
				drawCellBg(canvas, i, surface.cellSelectedColor);
			}
		}
	}

	private void findSelectedIndex(int startIndex, int endIndex,
			Calendar calendar, int[] section) {
		for (int i = startIndex; i < endIndex; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, date[i]);
			Date temp = calendar.getTime();
			// Log.d(TAG, "temp:" + temp.toLocaleString());
			if (temp.compareTo(selectedStartDate) == 0) {
				section[0] = i;
			}
			if (temp.compareTo(selectedEndDate) == 0) {
				section[1] = i;
				return;
			}
		}
	}

	public Date getSelectedStartDate() {
		return selectedStartDate;
	}

	public Date getSelectedEndDate() {
		return selectedEndDate;
	}
	
	private boolean isLastMonth(int i) {
		if (i < curStartIndex) {
			return true;
		}
		return false;
	}

	private boolean isNextMonth(int i) {
		if (i >= curEndIndex) {
			return true;
		}
		return false;
	}

	private int getXByIndex(int i) {
		return i % 7 + 1; // 1 2 3 4 5 6 7
	}

	private int getYByIndex(int i) {
		return i / 7 + 1; // 1 2 3 4 5 6
	}

	// 获得当前应该显示的年月
	public String getYearAndmonth() {
		calendar.setTime(curDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		return year + "年" + surface.monthText[month];
	}
	
	public Date getDate(){
		return curDate;
	}
	public String setDate(Date curDate){
		calendar.setTime(curDate);
		this.curDate = calendar.getTime();
		this.invalidate();
		return getYearAndmonth();
	}
	public void setMinClickableDate(Date minClickableDate){
		this.minClickableDate = minClickableDate;
		this.invalidate();
	}
	public void setMaxDate(Date maxDate){
		this.maxDate = maxDate;
//		this.invalidate();
	}
	
	
	public boolean isLastMonthAvailable(){
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, -1);
		if(DateUtil.compareYearAndMonth(calendar.getTime(), today) > -1){
			return true;
		}
		return false;
	}
	
	/**
	 * if n < 0, it means go to previous N month
	 * @param n
	 */
	public void gotoNextNMonth(int n){
		if(n < 0){
			for(int i=0;i < (0-n); i++){
				gotoLastMonth();
			}
		}else{
			for(int i=0;i < n; i++){
				gotoNextMonth();
			}
		}
		
	}
	
	//上一月
	public String gotoLastMonth(){
		if(isLastMonthAvailable()){
			curDate = calendar.getTime();
			this.invalidate();
		}
		return getYearAndmonth();
	}
	
	public boolean isNextMonthAvaliable(){
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		if(DateUtil.compareYearAndMonth(calendar.getTime(), maxDate) < 1){
			return true;
		}
		return false;
	}
	
	//下一月
	public String gotoNextMonth(){
		if(isNextMonthAvaliable()){
//			calendar.setTime(curDate);
//			calendar.add(Calendar.MONTH, 1);
			curDate = calendar.getTime();
			this.invalidate();
		}
		return getYearAndmonth();
	}

	private void setSelectedDateByCoor(float x, float y) {
		// cell click down
		if (y > surface.monthHeight + surface.weekHeight) {
			int m = (int) (Math.floor(x / surface.cellWidth) + 1);
			int n = (int) (Math
					.floor((y - (surface.monthHeight + surface.weekHeight))
							/ Float.valueOf(surface.cellHeight)) + 1);
			downIndex = (n - 1) * 7 + m - 1;
			Log.d(TAG, "downIndex:" + downIndex);
			calendar.setTime(curDate);
			if (isLastMonth(downIndex)) {
				calendar.add(Calendar.MONTH, -1);
			} else if (isNextMonth(downIndex)) {
				calendar.add(Calendar.MONTH, 1);
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]);
			downDate = calendar.getTime();
		}
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setSelectedDateByCoor(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_UP:
			if (downDate != null) {
				selectedStartDate = selectedEndDate = downDate;
				if(selectedStartDate.before(minClickableDate))
					return false;
				//响应监听事件
				if(onItemClickListener!=null)
					onItemClickListener.OnItemClick(selectedStartDate);
				downDate = null;
				invalidate();
			}
//			else{
//				if (event.getY() < surface.monthHeight) {
//					if(event.getX()>=surface.monthSelectorStart && event.getX()<=surface.monthSelectorEnd){
//						
//					}
//				}
//				invalidate();
//			}
			break;
		}
		return true;
	}
	
	//给控件设置监听事件
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener =  onItemClickListener;
	}
	//监听接口
	public interface OnItemClickListener {
		void OnItemClick(Date date);
	}

	/**
	 * 
	 * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
	 */
	private class Surface {
		public float density;
		public int width; // 整个控件的宽度
		public int height; // 整个控件的高度
		public float monthHeight = 0f; // 显示月的高度
		public float monthSelectorStart;
		public float monthSelectorEnd;
		//public float monthChangeWidth; // 上一月、下一月按钮宽度
		public float weekHeight; // 显示星期的高度
		public float cellWidth; // 日期方框宽度
		public float cellHeight; // 日期方框高度	
		public float borderWidth;
		public int bgColor = Color.parseColor("#FFFFFF");
		private int textColor = resource.getColor(R.color.calendarCurrentMonthTextColor);
		//private int textColorUnimportant = Color.parseColor("#666666");
//		private int btnColor = Color.parseColor("#666666");
//		private int borderColor = Color.parseColor("#CCCCCC");
//		public int todayNumberColor = Color.RED;
//		public int cellDownColor = Color.parseColor("#CCFFFF");
//		public int cellSelectedColor = Color.parseColor("#99CCFF");
		private int btnColor = Color.parseColor("#ffffff");
		private int borderColor = resource.getColor(R.color.calendarBorderColor);
		public int todayNumberColor = resource.getColor(R.color.calendarCurrentMonthTodayHintColor);
		public int cellDownColor = Color.parseColor("#ffffff");
		public int cellSelectedColor = Color.parseColor("#ffffff");
		public Paint borderPaint;
		public Paint monthPaint;
		public Paint weekPaint;
		public Paint datePaint;
		public Paint monthChangeBtnPaint;
		public Paint cellBgPaint;
		public Paint monthContentPaint;
		public Path boxPath; // 边框路径
		//public Path preMonthBtnPath; // 上一月按钮三角形
		//public Path nextMonthBtnPath; // 下一月按钮三角形
		public String[] weekText = { "日","一", "二", "三", "四", "五", "六"};
		public String[] monthText = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
		   
		public void init() {
			float temp = (height - surface.monthHeight)/ 7f;
			//monthChangeWidth = monthHeight * 1.5f;
			weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
			cellHeight = (height - monthHeight - weekHeight) / 6f;
			cellWidth = width / 7f;
			
			borderPaint = new Paint();
			borderPaint.setColor(borderColor);
			borderPaint.setStyle(Paint.Style.STROKE);
			borderWidth = (float) (1 * density);
			// Log.d(TAG, "borderwidth:" + borderWidth);
			borderWidth = borderWidth < 1 ? 1 : borderWidth;
			borderPaint.setStrokeWidth(borderWidth);
			
			
			monthPaint = new Paint();
			monthPaint.setColor(textColor);
			monthPaint.setAntiAlias(true);
			float textSize = cellHeight * 0.4f;
			Log.d(TAG, "text size:" + textSize);
			monthPaint.setTextSize(textSize);
			monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
			
			monthContentPaint = new Paint();
			monthContentPaint.setColor(todayNumberColor);
			monthPaint.setAntiAlias(true);
			
			weekPaint = new Paint();
			weekPaint.setColor(resource.getColor(R.color.calendarWeekTextColor));
			weekPaint.setAntiAlias(true);
//			float weekTextSize = weekHeight * 0.3f;
			float weekTextSize = 20;
			weekPaint.setTextSize(weekTextSize);
			Typeface weekFont = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
			weekPaint.setTypeface(weekFont);
			
			
			datePaint = new Paint();
			datePaint.setColor(textColor);
			datePaint.setAntiAlias(true);
			Typeface dateFont = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
			datePaint.setTypeface(dateFont);
//			float cellTextSize = cellHeight * 0.5f;
			float cellTextSize = 19;
			datePaint.setTextSize(cellTextSize);
			datePaint.setTypeface(Typeface.DEFAULT_BOLD);
			
			
			boxPath = new Path();
			//boxPath.addRect(0, 0, width, height, Direction.CW);
			//boxPath.moveTo(0, monthHeight);
			//boxPath.rLineTo(width, 0);
			boxPath.moveTo(0, monthHeight + weekHeight);
			boxPath.rLineTo(width, 0);
			for (int i = 1; i < 6; i++) {
				boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
				boxPath.rLineTo(width, 0);
				boxPath.moveTo(i * cellWidth, monthHeight + weekHeight);
				boxPath.rLineTo(0, height - monthHeight - weekHeight);
			}
			boxPath.moveTo(6 * cellWidth, monthHeight + weekHeight);
			boxPath.rLineTo(0, height - monthHeight - weekHeight);
			boxPath.moveTo(0, monthHeight + weekHeight + cellHeight*6);
			boxPath.rLineTo(width, 0);
			Log.d("poding","surfacedrawpath");
			//preMonthBtnPath = new Path();
			//int btnHeight = (int) (monthHeight * 0.6f);
			//preMonthBtnPath.moveTo(monthChangeWidth / 2f, monthHeight / 2f);
			//preMonthBtnPath.rLineTo(btnHeight / 2f, -btnHeight / 2f);
			//preMonthBtnPath.rLineTo(0, btnHeight);
			//preMonthBtnPath.close();
			//nextMonthBtnPath = new Path();
			//nextMonthBtnPath.moveTo(width - monthChangeWidth / 2f,
			//		monthHeight / 2f);
			//nextMonthBtnPath.rLineTo(-btnHeight / 2f, -btnHeight / 2f);
			//nextMonthBtnPath.rLineTo(0, btnHeight);
			//nextMonthBtnPath.close();
			monthChangeBtnPaint = new Paint();
			monthChangeBtnPaint.setAntiAlias(true);
			monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			monthChangeBtnPaint.setColor(btnColor);
			cellBgPaint = new Paint();
			cellBgPaint.setAntiAlias(true);
			cellBgPaint.setStyle(Paint.Style.FILL);
			cellBgPaint.setColor(cellSelectedColor);
		}
	}
}
