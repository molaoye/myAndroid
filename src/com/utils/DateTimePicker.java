package com.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.LinearLayout.LayoutParams;

public class DateTimePicker extends LinearLayout{

	private static final long serialVersionUID = 432696068949837471L;
	public static final String YYYY_MM_DD = "YYYY_MM_DD";
	public static final String YYYYMMDD = "YYYYMMDD";
	public static final String YMD = "YMD";
	public static final String YYYY_MM_DD_HH_MM_SS = "YYYY_MM_DD_HH_MM_SS";
	public static final String YYYY_MM_DD_HH_MM = "YYYY_MM_DD_HH_MM";
	public static final String YMD_HM = "YMD_HM";
	public static final String Y="Y";
	public static final String YM="YM";
	public static final String YMD_HMS="YMD_HMS";
	public static final String HMS="HMS";
	
	public static final int MODE_DATE = 1;
	public static final int MODE_TIME = 2;
	public static final int MODE_DATE_TIME = 3;
	
	private Context context;
	private String patten;
	protected static final Calendar currentDate = Calendar.getInstance();;
	private String oldValue;
	private String dateTimeVal;
	private boolean refresh;
//	private int tabIndex;
//	private String fieldName;
	private int mode;
	private boolean enable;
	private boolean hidden;
	
//	private EditText tEditText;
//	private ImageButton imageButton;
	private DatePicker dPicker;
	private TimePicker tPicker;
	private LinearLayout tLinearlayout;
	private LinearLayout.LayoutParams params;
	
	private TextView textView;
	
	public DateTimePicker(Context context,int mode,TextView textView) {
		super(context);
		
//		tEditText = new EditText(context);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.weight = 1;
//		tEditText.setLayoutParams(layoutParams);
//		tEditText.setEnabled(false);
//		tEditText.setFocusable(false);
//		this.addView(tEditText);
		
//		imageButton = new ImageButton(context);
//		imageButton.setBackgroundResource(R.drawable.date_icon);
//		imageButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//		imageButton.setOnClickListener(new imageButtonOnClickListener());
//		this.addView(imageButton);
		
		this.context = context;
		this.mode = mode;
//		this.tabIndex = -1;
//		this.fieldName = "";
		this.oldValue = "";
		this.patten = YYYY_MM_DD_HH_MM_SS;
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setBackgroundResource(android.R.color.darker_gray);
//		setEnable(enable);
		
		this.textView=textView;
		this.textView.setOnClickListener(new listener());
//		setValue("");
	}
	
	
	public int getInputMode() {
		return mode;
	}
	
	private class listener implements OnClickListener{
		@Override
		public void onClick(View v) {
			DateTimePicker.this.setOldValue(textView.getText().toString());//tEditText.getText().toString()
			 switch (getInputMode()) {
				case MODE_DATE_TIME:
					showDateTime();
					break;
					
				case MODE_TIME:
					showTime();
					break;
					
				case MODE_DATE:
				default:
					showDate();
					break;
				}
		}
		
	}
	
	public Date getDate() {
		return currentDate.getTime();
	}
	
	private void showTime() {
		if (getDate() != null) {
			currentDate.setTime(getDate());
		}
		int mYear = currentDate.get(Calendar.YEAR);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int mHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int mMinute = currentDate.get(Calendar.MINUTE);
        currentDate.set(mYear, mMonth, mDay, mHour, mMinute);
		TimePickerDialog tpd = new TimePickerDialog(context,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						int year = currentDate.get(Calendar.YEAR);
				        int month = currentDate.get(Calendar.MONTH);
				        int day = currentDate.get(Calendar.DAY_OF_MONTH);
				        currentDate.set(year, month, day, hourOfDay, minute);
					}
				}, mHour, mMinute, true){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				super.onClick(dialog, which);
    			setValue(dateToString(currentDate.getTime(), "HH:mm"));
//    			((MbFormWindow)context).itemStateChanged(MbDateField.this);
			}
		};
		tpd.show();
	}
	
	private void showDate() {
		if (getDate() != null) {
			currentDate.setTime(getDate());
		}
		int mYear = currentDate.get(Calendar.YEAR);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int mHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int mMinute = currentDate.get(Calendar.MINUTE);
        currentDate.set(mYear, mMonth, mDay, mHour, mMinute);
        DatePickerDialog dpd = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						currentDate.set(year, monthOfYear, dayOfMonth);
					}
				}, mYear, mMonth, mDay){
        		@Override
				public void onClick(DialogInterface dialog, int which) {
        			super.onClick(dialog, which);
        			setValue(dateToString(currentDate.getTime(), "yyyy-MM-dd"));
//        			if(refresh)
//        				((MbFormWindow)context).itemStateChanged(MbDateField.this);
				}};
		dpd.show();
	}
	
	private void showDateTime() {
		if (getDate() != null) {
			currentDate.setTime(getDate());
		}
		int mYear = currentDate.get(Calendar.YEAR);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int mHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int mMinute = currentDate.get(Calendar.MINUTE);
        if(oldValue!=null && !oldValue.equals("")){
        	if(oldValue.indexOf(" ")!=-1){
        		String[] strs = oldValue.split(" ");
        		String strs0 = strs[0];
        		String strs1 = strs[1];
        		//鏃ユ湡
        		if(strs0!=null && !strs0.equals("") && strs0.indexOf("-")!=-1){
        			String[] dates = strs0.split("-");
        			if(dates[0]!=null && !dates[0].equals(""))
        				mYear = Integer.parseInt(dates[0]);
        			if(dates[1]!=null && !dates[1].equals(""))
        				mMonth = Integer.parseInt(dates[1]);
        			if(dates[2]!=null && !dates[2].equals(""))
        				mDay = Integer.parseInt(dates[2]);
        		}
        		//鏃堕棿
        		if(strs1!=null && !strs1.equals("") && strs1.indexOf("-")!=-1){
        			String[] dates = strs0.split(":");
        			if(dates[0]!=null && !dates[0].equals(""))
        				mHour = Integer.parseInt(dates[0]);
        			if(dates[1]!=null && !dates[1].equals(""))
        				mMinute = Integer.parseInt(dates[1]);
        			//if(dates[2]!=null && !dates[2].equals(""))
        				//mDay = Integer.parseInt(dates[2]);
        		}
        		
        	}else{
        		//鏃ユ湡
        		if(oldValue!=null && !oldValue.equals("") && oldValue.indexOf("-")!=-1){
        			String[] dates = oldValue.split("-");
        			if(dates[0]!=null && !dates[0].equals(""))
        				mYear = Integer.parseInt(dates[0]);
        			if(dates[1]!=null && !dates[1].equals(""))
        				mMonth = Integer.parseInt(dates[1]);
        			if(dates[2]!=null && !dates[2].equals(""))
        				mDay = Integer.parseInt(dates[2]);
        		}
        	}
        }
        currentDate.set(mYear, mMonth, mDay, mHour, mMinute);
        
		dPicker = new DatePicker(context);
		dPicker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		tPicker = new TimePicker(context);
		tPicker.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		tPicker.setIs24HourView(true); // 鏃堕棿璁句负24灏忔椂鍒?
		tPicker.setCurrentHour(mHour);
		tPicker.setCurrentMinute(mMinute);

		dPicker.init(mYear, mMonth, mDay,
				new DatePicker.OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						currentDate.set(year, monthOfYear, dayOfMonth);
						setDate(currentDate.getTime());
					}
				});
		tPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
					@Override
					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {
						int year = currentDate.get(Calendar.YEAR);
				        int month = currentDate.get(Calendar.MONTH);
				        int day = currentDate.get(Calendar.DAY_OF_MONTH);
				        currentDate.set(year, month, day, hourOfDay, minute);
				        setDate(currentDate.getTime());
					}
				});
		tLinearlayout = new LinearLayout(context);
		tLinearlayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		tLinearlayout.setOrientation(LinearLayout.VERTICAL);
		params = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		params.gravity = Gravity.CENTER;
		tLinearlayout.addView(dPicker,params);
		tLinearlayout.addView(tPicker,params);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//		dialog.setTitle(getChoseDateTitle());
		dialog.setView(tLinearlayout);
		dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			     public void onClick(DialogInterface dialog, int which) {
			    	 setValue(dateToString(currentDate.getTime(), "yyyy-MM-dd HH:mm:ss"));
//			    	 if(refresh)
//							((MbFormWindow)context).itemStateChanged(MbDateField.this);
			     }
		});
		dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			     public void onClick(DialogInterface dialog, int which) {
//			    	 if(refresh)
//							((MbFormWindow)context).itemStateChanged(MbDateField.this);
			     }
		});
		dialog.create().show();
	}
	
	public void setPatten(String patten) {
		if (patten.equals(YYYY_MM_DD_HH_MM_SS) || patten.equals(YMD_HMS)){
			this.patten = "yyyy-MM-dd HH:mm:ss";
			setInputMode(MODE_DATE_TIME);
		}else if(patten.equals(YYYY_MM_DD_HH_MM) || patten.equals(YMD_HM)) {
			this.patten = "yyyy-MM-dd HH:mm";
			setInputMode(MODE_DATE_TIME);
		}else if(patten.equals(Y)){
			this.patten = "yyyy";
			setInputMode(MODE_DATE);
		}else if(patten.equals(YM)){
			this.patten = "yyyy-MM";
			setInputMode(MODE_DATE);
		}else if(patten.equals(YMD)){
			this.patten = "yyyy-MM-dd";
			setInputMode(MODE_DATE);
		}else if(patten.equals(HMS)){
			this.patten = "HH:mm:ss";
			setInputMode(MODE_TIME);
		}else if(patten.equals(YYYY_MM_DD)){
			this.patten = "yyyy-MM-dd";
			setInputMode(MODE_DATE);
		}else{
			this.patten = "yyyy-MM-dd HH:mm:ss";
			setInputMode(MODE_DATE_TIME);
		}
	}
	
	public String getPatten() {
		if (patten == null) {
			patten = YYYY_MM_DD_HH_MM_SS;
		}
		return patten;
	}
	
	public void setInputMode(int mode) {
		if (mode != 1 && mode != 2 && mode != 3)
			throw new IllegalArgumentException("Invalid input mode");
		//synchronized (Component.UILock) {
			if (this.mode != mode) {
				this.mode = mode;
				if (mode == 2) {
					currentDate.set(1, 2012);
					currentDate.set(2, 0);
					currentDate.set(5, 1);
				} else if (mode == 1) {
					currentDate.set(11, 0);
					currentDate.set(12, 0);
				}
				//invalidate();
			}
		//}
	}

//	public String getFieldName() {
//		return fieldName;
//	}

//	public void setFieldName(String fieldName) {
//		this.fieldName = fieldName;
//	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

//	public int getTabIndex() {
//		return tabIndex;
//	}

//	public void setTabIndex(int tabIndex) {
//		this.tabIndex = tabIndex;
//	}
	
	public void setDate(Date date){
		currentDate.setTime(date);
//		setValue(DateUtil.dateToString(currentDate.getTime(), patten));
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue){
		this.oldValue = oldValue;
	}
	
//	public String getChoseDateTitle() {
//		if (!StringUtil.isBlank(fieldName)) return fieldName;
//		return Res.getLabel("label.select.date");
//	}

	public String getDateTimeVal() {
		return dateTimeVal;
	}


	public String getString() {
		return "";//this.tEditText.getText().toString();
	}

	public boolean isEnable() {
		return this.enable;
	}

	public boolean isHidden() {
		return this.hidden;
	}

	public void setEnable(boolean b) {
		this.enable = b;
//		imageButton.setEnabled(b);
	}

	public void setHidden(boolean b) {
		this.hidden = b;
		if(b){
			this.setVisibility(View.GONE);
		}else{
			this.setVisibility(View.VISIBLE);
		}
	}

	public void setValue(String value) {
//		this.tEditText.setText(value);
		this.textView.setText(value);
		this.dateTimeVal=value;
	}
	
	public static final String dateToString(Date date, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception e) {
			Log.i("DateUtil", "dateToString-->", e);
			if (date != null) {
				return date.toString();
			}
		}
		return null;
	}
	
}
