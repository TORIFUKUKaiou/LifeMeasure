package jp.torifuku.lifemesure;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;

public class LifeMeasureAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Utility utility = new Utility();
		for (int appWidgetId : appWidgetIds) {
			 RemoteViews views = utility.createRemoteViews(context);
			 
			 appWidgetManager.updateAppWidget(appWidgetId, views);
		}
		
		/** AlarmManager#set */
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.set(AlarmManager.ELAPSED_REALTIME, (SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES), operation);
	}	
}
