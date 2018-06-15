package com.rcacao.asmelhoresreceitas.data.models;

public interface ListItem {

    public static final String TYPE_INGREDIENT = "ingredient";
    public static final String TYPE_STEP = "step";
    public static final String TYPE_GENERIC = "generic";

    String getType();
    int getId();
    String getTitle();
    String getAuxText1();
    String getAuxText2();

}
