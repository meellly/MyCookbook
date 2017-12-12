package edu.cse4230.schilbe.mycookbook;

public class Recipe {
    int id;
    String recipeName, recipePath, recipeIngredients, recipeDirections, recipeCookTime, recipeServings;
    int allergenGF = 0, allergenDF = 0, allergenNF = 0, allergenV = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipePath() {
        return recipePath;
    }

    public void setRecipePath(String recipePath) {
        this.recipePath = recipePath;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipeDirections() {
        return recipeDirections;
    }

    public void setRecipeDirections(String recipeDirections) {
        this.recipeDirections = recipeDirections;
    }

    public String getRecipeCookTime() {
        return recipeCookTime;
    }

    public void setRecipeCookTime(String recipeCookTime) {
        this.recipeCookTime = recipeCookTime;
    }

    public String getRecipeServings() {
        return recipeServings;
    }

    public void setRecipeServings(String recipeServings) {
        this.recipeServings = recipeServings;
    }

    public int getAllergenGF() {
        return allergenGF;
    }

    public void setAllergenGF(int allergenGF) {
        this.allergenGF = allergenGF;
    }

    public int getAllergenDF() {
        return allergenDF;
    }

    public void setAllergenDF(int allergenDF) {
        this.allergenDF = allergenDF;
    }

    public int getAllergenNF() {
        return allergenNF;
    }

    public void setAllergenNF(int allergenNF) {
        this.allergenNF = allergenNF;
    }

    public int getAllergenV() {
        return allergenV;
    }

    public void setAllergenV(int allergenV) {
        this.allergenV = allergenV;
    }
}
