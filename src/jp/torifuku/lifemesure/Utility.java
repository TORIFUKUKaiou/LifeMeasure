package jp.torifuku.lifemesure;

import java.util.GregorianCalendar;
import jp.torifuku.lifemeasure.R;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class Utility {

	String createMsg(GregorianCalendar baseDate, Context context, int pattern) {
		GregorianCalendar now = new GregorianCalendar();
		//android.util.Log.i("TEST", "baseDate: " + baseDate.toString());
		//android.util.Log.i("TEST", "now: " + now.toString());
		final long life = now.getTimeInMillis () - baseDate.getTimeInMillis();
		final long DAY = 1000/*1000ms->1s*/ * 60/*1s->1minute*/ * 60 /*1minute->1hour*/ * 24/*1hour->1day*/;
		final long HOUR = DAY / 24;
		final long MINUTE = HOUR / 60;
		final long SECOND = MINUTE / 60;
		long aliveDay = life / DAY;
		long remainder = life % DAY;
		long aliveHour = remainder / HOUR;
		remainder = remainder % HOUR;
		long aliveMinute = remainder / MINUTE;
		remainder = remainder % MINUTE;
		long aliveSecond = remainder / SECOND;
		
		boolean future = false;
		if ((aliveDay < 0) || (aliveHour < 0) || (aliveMinute < 0) || (aliveSecond < 0)) {
			future = true;
			aliveDay *= -1;
			aliveHour *= -1;
			aliveMinute *= -1;
			aliveSecond *= -1;
		}
		
		String msg = null;
		String time = context.getString(R.string.time, aliveDay, aliveHour, aliveMinute, aliveSecond);
		if (future) {
			msg = context.getString(R.string.rest, time);
		} else {
			final int[] passedArray = { R.string.passed, R.string.passed_dazai };
			msg = context.getString(passedArray[pattern], time);
		}
		
		return msg;
	}
	
	RemoteViews createRemoteViews(Context context) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_appwidget);
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		 
		int year = sp.getInt(MainActivity.KEY_YEAR, 1970);
		int month = sp.getInt(MainActivity.KEY_MONTH, 0);
		int dayOfMonth = sp.getInt(MainActivity.KEY_DAY_OF_MONTH, 1);
		int hourOfDay = sp.getInt(MainActivity.KEY_HOUR, 0);
		int minute = sp.getInt(MainActivity.KEY_MINUTE, 0);
		boolean set = sp.getBoolean(MainActivity.KEY_SAVED, false);
			 
		GregorianCalendar baseDate = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
		GregorianCalendar now = new GregorianCalendar();
			 
		long diff = now.getTimeInMillis() - baseDate.getTimeInMillis();
		final long DAY = 1000/*1000ms->1s*/ * 60/*1s->1minute*/ * 60 /*1minute->1hour*/ * 24/*1hour->1day*/;
		long day = diff / DAY;
		String msg = null;
		if (set) {
			if (day >= 0) {
				msg = context.getString(R.string.elapse, "" + day);
			} else {
				msg = context.getString(R.string.rest_widget, "" + (day*(-1)));
			}
		} else {
			msg = context.getString(R.string.please_setting);
		}
		views.setTextViewText(R.id.widget_textView, msg);
		views.setTextColor(R.id.widget_textView, 0xff000000);

		/** Activity起動用のPendingIntentを設定する。 */
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.widget_ll, pendingIntent);
		 
		return views;
	}
}
