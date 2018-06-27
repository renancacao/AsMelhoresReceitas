package com.rcacao.asmelhoresreceitas.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.ui.RecipeActivity;
import com.rcacao.asmelhoresreceitas.providers.ReceitasWidgetProvider;
import com.squareup.picasso.Picasso;

public class MyUtils {

    public static void loadImage(ImageView imageView, String url) {

        if (url != null && !url.isEmpty()){
            Picasso.get().load(url).into(imageView);
            imageView.setTag(url);
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
            imageView.setTag(null);
        }

    }

    public static String getUserAgent(Context context, String applicationName){
        String versionName;
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "?";
        }
        return applicationName + "/" + versionName + " (Linux;Android " + Build.VERSION.RELEASE
                + ") " + "ExoPlayerLib/" + ExoPlayerLibraryInfo.VERSION;
    }

    public static void refreshWidget(String title, Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ReceitasWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        ReceitasWidgetProvider.updateWidgets(context, appWidgetManager,  appWidgetIds, title);

    }

    public static void refreshWidget(Context context) {

        String title = PreferenceManager.getDefaultSharedPreferences(context).
                getString(RecipeActivity.PREF_FAV,"");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ReceitasWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        ReceitasWidgetProvider.updateWidgets(context, appWidgetManager,  appWidgetIds, title);

    }
}
