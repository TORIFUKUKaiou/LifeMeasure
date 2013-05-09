package jp.torifuku.lifemesure;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class LifeMeasureAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Utility utility = new Utility();
		for (int appWidgetId : appWidgetIds) {
			 RemoteViews views = utility.createRemoteViews(context);
			 
			 appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
