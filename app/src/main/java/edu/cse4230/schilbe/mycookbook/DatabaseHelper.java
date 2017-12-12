package edu.cse4230.schilbe.mycookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "recipe.db";
    public static final int DATABASE_VERSION = 1;
    public static final String RECIPES_TABLE_NAME = "recipes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table recipes " +
                "(id integer primary key autoincrement, recipeName text not null, recipePath text not null, recipeIngredients text not null, recipeDirections text not null, recipeCookTime text not null, recipeServings text not null, allergenGF integer default 0, allergenDF integer default 0, allergenNF integer default 0, allergenV integer default 0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<Recipe> getAllRecipes() {
        String query = "SELECT * FROM " + RECIPES_TABLE_NAME;
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String recipe_name = c.getString(c.getColumnIndex("recipeName"));
                String recipe_path = c.getString(c.getColumnIndex("recipePath"));

                Recipe recipe = new Recipe();
                recipe.setId(id);
                recipe.setRecipeName(recipe_name);
                recipe.setRecipePath(recipe_path);
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    public ArrayList<Recipe> getFilteredRecipes(int filter_on_gf, int filter_on_df, int filter_on_nf, int filter_on_v) {
        String query = "SELECT * FROM " + RECIPES_TABLE_NAME;
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String recipe_name = c.getString(c.getColumnIndex("recipeName"));
                String recipe_path = c.getString(c.getColumnIndex("recipePath"));
                int recipe_gf = c.getInt(c.getColumnIndex("allergenGF"));
                int recipe_df = c.getInt(c.getColumnIndex("allergenDF"));
                int recipe_nf = c.getInt(c.getColumnIndex("allergenNF"));
                int recipe_v = c.getInt(c.getColumnIndex("allergenV"));

                if (((filter_on_gf == 1) && (recipe_gf == 1)) || ((filter_on_df == 1) && (recipe_df == 1)) || ((filter_on_nf == 1) && (recipe_nf == 1)) || ((filter_on_v == 1) && (recipe_v == 1)) || ((filter_on_gf == 0) && (filter_on_df == 0) && (filter_on_nf == 0) && (filter_on_v == 0))) {
                    Recipe recipe = new Recipe();
                    recipe.setId(id);
                    recipe.setRecipeName(recipe_name);
                    recipe.setRecipePath(recipe_path);
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    public boolean createRecipe(String recipe_name, String recipe_path, String recipe_ingredients, String recipe_directions, String recipe_cook_time, String recipe_servings, int allergen_gf, int allergen_df, int allergen_nf, int allergen_v) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recipeName", recipe_name);
        contentValues.put("recipePath", recipe_path);
        contentValues.put("recipeIngredients", recipe_ingredients);
        contentValues.put("recipeDirections", recipe_directions);
        contentValues.put("recipeCookTime", recipe_cook_time);
        contentValues.put("recipeServings", recipe_servings);
        contentValues.put("allergenGF", allergen_gf);
        contentValues.put("allergenDF", allergen_df);
        contentValues.put("allergenNF", allergen_nf);
        contentValues.put("allergenV", allergen_v);

        db.insert("recipes", null, contentValues);
        return true;
    }

    public int getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RECIPES_TABLE_NAME);
        return numRows;
    }

    public boolean updateRecipe(int id, String recipe_name, String recipe_path, String recipe_ingredients, String recipe_directions, String recipe_cook_time, String recipe_servings, int allergen_gf, int allergen_df, int allergen_nf, int allergen_v) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recipeName", recipe_name);
        contentValues.put("recipePath", recipe_path);
        contentValues.put("recipeIngredients", recipe_ingredients);
        contentValues.put("recipeDirections", recipe_directions);
        contentValues.put("recipeCookTime", recipe_cook_time);
        contentValues.put("recipeServings", recipe_servings);
        contentValues.put("allergenGF", allergen_gf);
        contentValues.put("allergenDF", allergen_df);
        contentValues.put("allergenNF", allergen_nf);
        contentValues.put("allergenV", allergen_v);
        db.update("recipes", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public int deleteRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("recipes", "id = ? ", new String[]{Integer.toString(id)});
    }

    public int getMaxId() {
        String query = "SELECT * FROM " + RECIPES_TABLE_NAME + " ORDER BY id DESC LIMIT 1";
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        int maxId = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            maxId = c.getInt(c.getColumnIndex("id"));
        }
        return maxId;
    }

    public Recipe getRecipeByCode(int code) {
        String query = "SELECT * FROM " + RECIPES_TABLE_NAME + " WHERE " + "id" + " = " + code;
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);

        Recipe recipe = new Recipe();
        if (c.getCount() > 0) {

            c.moveToFirst();
            int id = c.getInt(c.getColumnIndex("id"));
            String recipe_name = c.getString(c.getColumnIndex("recipeName"));
            String recipe_path = c.getString(c.getColumnIndex("recipePath"));
            String recipe_ingredients = c.getString(c.getColumnIndex("recipeIngredients"));
            String recipe_directions = c.getString(c.getColumnIndex("recipeDirections"));
            String recipe_cook_time = c.getString(c.getColumnIndex("recipeCookTime"));
            String recipe_servings = c.getString(c.getColumnIndex("recipeServings"));
            int allergen_gf = c.getInt(c.getColumnIndex("allergenGF"));
            int allergen_df = c.getInt(c.getColumnIndex("allergenDF"));
            int allergen_nf = c.getInt(c.getColumnIndex("allergenNF"));
            int allergen_v = c.getInt(c.getColumnIndex("allergenV"));

            recipe.setId(id);
            recipe.setRecipeName(recipe_name);
            recipe.setRecipePath(recipe_path);
            recipe.setRecipeIngredients(recipe_ingredients);
            recipe.setRecipeDirections(recipe_directions);
            recipe.setRecipeCookTime(recipe_cook_time);
            recipe.setRecipeServings(recipe_servings);
            recipe.setAllergenGF(allergen_gf);
            recipe.setAllergenDF(allergen_df);
            recipe.setAllergenNF(allergen_nf);
            recipe.setAllergenV(allergen_v);
        }
        return recipe;
    }
}
