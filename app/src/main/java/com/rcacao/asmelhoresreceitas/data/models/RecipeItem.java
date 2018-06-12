package com.rcacao.asmelhoresreceitas.data.models;

public interface RecipeItem {

    public static final String TYPE_INGREDIENT = "ingredient";
    public static final String TYPE_STEP = "step";

    String getType();
    int getId();
    String getText();
    String getTextAux1();
    String getTextAux2();
    String getImageUrl();
    String getVideoUrl();
}
