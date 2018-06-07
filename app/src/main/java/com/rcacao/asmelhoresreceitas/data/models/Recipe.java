package com.rcacao.asmelhoresreceitas.data.models.models;

public class Recipe {

    private int id;
    private String name;
    private Ingredient ingredients[];
    private Step steps[];
    private Integer servings;
    private String image;

    private class Ingredient {

        private double quantity;
        private String measure;
        private String ingredient;

    }

    private class Step {

        private int id;
        private String shortDescription;
        private String description;
        private String videoURL;
        private String thumbnailURL;

    }
}
