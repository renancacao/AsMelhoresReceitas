package com.rcacao.asmelhoresreceitas.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rcacao.asmelhoresreceitas.data.local.IngredientContract.IngredientEntry;
import com.rcacao.asmelhoresreceitas.data.models.Ingredient;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 2;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String createTabIngredients = "CREATE TABLE " + IngredientEntry.TABLE_NAME + " (" +
                IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IngredientEntry.INGREDIENT + " TEXT," +
                IngredientEntry.QUANTITY + " TEXT," +
                IngredientEntry.MEASURE + " TEXT)";

        sqLiteDatabase.execSQL(createTabIngredients);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void removeIngredients(){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(IngredientEntry.TABLE_NAME, null,null);

    }

    public void changeIngredients(Ingredient[] ingredients){

        removeIngredients();

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values;

        for(Ingredient ing : ingredients){

            values = new ContentValues();
            values.put(IngredientEntry.INGREDIENT, ing.getIngredient());
            values.put(IngredientEntry.QUANTITY, String.valueOf(ing.getQuantity()));
            values.put(IngredientEntry.MEASURE, ing.getMeasure());
            db.insert(IngredientEntry.TABLE_NAME, null, values);

        }


    }

    public Ingredient[] getIngredients() {

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(IngredientEntry.TABLE_NAME,
                new String[]{IngredientEntry.INGREDIENT,
                        IngredientEntry.QUANTITY,IngredientEntry.MEASURE},
                null,
                null,
                null,
                null,
                IngredientEntry._ID,
                null);

        if (cursor != null) {


                Ingredient[] ingredients = new Ingredient[cursor.getCount()];
                int cont = 0;
                while (cursor.moveToNext()) {
                    ingredients[cont] = new Ingredient();
                    ingredients[cont].setIngredient(cursor.getString(
                            cursor.getColumnIndex(IngredientEntry.INGREDIENT)));
                    ingredients[cont].setMeasure(cursor.getString
                            (cursor.getColumnIndex(IngredientEntry.MEASURE)));
                    ingredients[cont].setQuantity(cursor.getString(
                            cursor.getColumnIndex(IngredientEntry.QUANTITY)));
                    cont++;
                }
                cursor.close();
                return ingredients;
        }
        else
        {
                return null;
        }



    }
}
