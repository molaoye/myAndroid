package com.android.widget;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.android.R;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

/**
 * 
 * updatePeriodMillis设置了频率高于30分钟每次的话是没用的，低于这个值应该才有效。
 * 一般情况用Service在后台更新。
 *
 */
public class WidgetTest2 extends AppWidgetProvider {

	/** Called when the activity is first created. */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		context.startService(new Intent(context, UpdateService.class));
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	public static class UpdateService extends Service {
		@Override
		public void onStart(Intent intent, int startId) {
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new MyTime(this, manager), 1000, 1000);
		}

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}

		private class MyTime extends TimerTask {
			RemoteViews remoteViews;
			AppWidgetManager appWidgetManager;
			ComponentName thisWidget;

			public MyTime(Context context, AppWidgetManager appWidgetManager) {
				this.appWidgetManager = appWidgetManager;
				remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgettest2);
				thisWidget = new ComponentName(context, WidgetTest2.class);
			}

			public void run() {
				Calendar cal=Calendar.getInstance();  
				remoteViews.setTextViewText(R.id.time, cal.getTime().toLocaleString());
				appWidgetManager.updateAppWidget(thisWidget, remoteViews);
			}
		}

	}

}