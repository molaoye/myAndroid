package com.android.widget;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import com.android.R;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * 
 * 简单桌面小部件Widget
 * 
 * AndroidManifest.xml：
 *         <receiver android:name="com.android.Widget.WidgetTest1" android:label="DaysToWorldCup" >
            <intent-filter>
                <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
            </intent-filter>
            <meta-data  android:name="android.appwidget.provider" android:resource="@xml/widget1" />
        </receiver>
          注意：取消activity的LAUNCHER和MAIN
 *
 */
public class WidgetTest1 extends AppWidgetProvider {
	/** Called when the activity is first created. */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 60000);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	private class MyTime extends TimerTask {
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;

		public MyTime(Context context, AppWidgetManager appWidgetManager) {
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgettest1);
			thisWidget = new ComponentName(context, WidgetTest1.class);
		}

		public void run() {
			Date date = new Date();
			Calendar calendar = new GregorianCalendar(2014, 06, 12);//2014年6月12
			long days = (((calendar.getTimeInMillis() - date.getTime()) / 1000)) / 86400;
			remoteViews.setTextViewText(R.id.wordcup, "距离巴西世界杯还有" + days + "天");
			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		}
	}
}