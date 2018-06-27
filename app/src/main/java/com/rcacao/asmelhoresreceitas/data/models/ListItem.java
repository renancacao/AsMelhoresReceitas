package com.rcacao.asmelhoresreceitas.data.models;

public interface ListItem {

    String TYPE_INGREDIENT = "ingredient";
    String TYPE_STEP = "step";
    String TYPE_GENERIC = "generic";

    String getType();
    int getId();
    String getTitle();
    String getAuxText1();
    String getAuxText2();

}
