package com.rcacao.asmelhoresreceitas.data.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.Gson;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JsonAsyncLoader extends AsyncTaskLoader<Recipe[]> {

    private final String TAG = getClass().getName();

    private static final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private Recipe[] recipes;

    public JsonAsyncLoader(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onStartLoading() {

        if (recipes != null){
            deliverResult(recipes);
        }
        else{
            forceLoad();
        }

    }

    @Nullable
    @Override
    public Recipe[] loadInBackground() {

        String content = getContentFromUrl(URL);
        recipes = new Gson().fromJson(content, Recipe[].class);

        return recipes;

    }

    private String getContentFromUrl(String url){

        String content = "";

        try {

            URLConnection urlConnection = new URL(url).openConnection();
            InputStream stream = urlConnection.getInputStream();
            content = convertStreamToString(stream);

        } catch (IOException e) {
            Log.e(TAG,e.getMessage(), e);
        }

        return content;
    }

    private String convertStreamToString(InputStream stream) {

        BufferedReader reader = new BufferedReader( new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e){
            Log.e(TAG,e.getMessage(), e);
            return "";
        }

        return sb.toString();
    }


    @Override
    public void deliverResult(@Nullable Recipe[] data) {
        recipes = data;
        super.deliverResult(data);
    }
}
