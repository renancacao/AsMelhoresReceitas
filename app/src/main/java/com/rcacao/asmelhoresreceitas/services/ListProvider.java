package com.rcacao.asmelhoresreceitas.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.local.DbHelper;
import com.rcacao.asmelhoresreceitas.data.models.Ingredient;
import com.rcacao.asmelhoresreceitas.ui.MainActivity;

class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Ingredient[] ingredients;
    private final Context context;

    ListProvider(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredients = new DbHelper(context).getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount()
    {
        if (ingredients != null){
            return ingredients.length;
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {

        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_widget_layout);

        Ingredient ingredient = ingredients[i];
        remoteView.setTextViewText(R.id.textViewIngredient, ingredient.getIngredient());
        remoteView.setTextViewText(R.id.textViewQuantity, String.valueOf(ingredient.getQuantity()));
        remoteView.setTextViewText(R.id.textViewMeasure, ingredient.getMeasure());

        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setPendingIntentTemplate(R.id.widget_list, appPendingIntent);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
