package com.rcacao.asmelhoresreceitas.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.ui.MainActivity;
import com.rcacao.asmelhoresreceitas.ui.RecipeActivity;
import com.rcacao.asmelhoresreceitas.widget.service.WidgetService;

public class ReceitasWidgetProvider extends AppWidgetProvider{


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        String title = PreferenceManager.getDefaultSharedPreferences(context).
                getString(RecipeActivity.PREF_FAV,"");
        updateWidgets(context,appWidgetManager,appWidgetIds, title);

    }


    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager,
                                     int[] appWidgetIds, String title) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, title);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String title) {

        RemoteViews rv;
        rv = getRemoteView(context,appWidgetId,title);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getRemoteView(Context context, int appWidgetId, String title) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list, appPendingIntent);
        views.setEmptyView(R.id.widget_list, R.id.widget_empty_view);

            views.setTextViewText(R.id.widget_textView,title);

        Intent intent = new Intent(context, WidgetService.class);
        intent.setData( Uri.fromParts("content", String.valueOf(appWidgetId), null));
        views.setRemoteAdapter(R.id.widget_list, intent);

        return views;
    }
}
