package com.rcacao.asmelhoresreceitas.data.local;

import android.provider.BaseColumns;

class IngredientContract{

    class IngredientEntry implements BaseColumns {
        public static final String TABLE_NAME = "tabIngredients";

        public static final String INGREDIENT = "ingredient";
        public static final String QUANTITY = "quantity";
        public static final String MEASURE = "measure";
    }
}

