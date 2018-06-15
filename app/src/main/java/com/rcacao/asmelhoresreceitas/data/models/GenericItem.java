package com.rcacao.asmelhoresreceitas.data.models;

public class GenericItem implements ListItem {


    private String title;
    private String auxText1;
    private String auxText2;

    public GenericItem(String title, String auxText1, String auxText2) {
        this.title = title;
        this.auxText1 = auxText1;
        this.auxText2 = auxText2;
    }

    @Override
    public String getType() {
        return ListItem.TYPE_GENERIC;
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuxText1() {
        return auxText1;
    }

    @Override
    public String getAuxText2() {
        return auxText2;
    }
}
