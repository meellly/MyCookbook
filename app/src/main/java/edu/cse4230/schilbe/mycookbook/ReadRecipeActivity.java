package edu.cse4230.schilbe.mycookbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ReadRecipeActivity extends AppCompatActivity {
    TextView ingredientsView = null, directionsView = null, servingsView = null, cookTimeView = null;
    ImageView recipeImage = null, gfImage = null, dfImage = null, nfImage = null, vImage = null;
    Intent indexIntent = null;
    String name = null, imagePath = null, ingredients = null, directions = null, time = null, servings = null;
    int id = 0, alg_gf = 0, alg_df = 0, alg_nf = 0, alg_v = 0;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_recipe);

        // Setup the App Bar
        toolbar = findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitleTextColor(0xFFECF0F1);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Disable the "Up" button
        actionBar.setDisplayHomeAsUpEnabled(true);

        ingredientsView = findViewById(R.id.viewIngredients);
        directionsView = findViewById(R.id.viewDirections);
        servingsView = findViewById(R.id.servesView);
        cookTimeView = findViewById(R.id.timingView);
        recipeImage = findViewById(R.id.imageView);
        gfImage = findViewById(R.id.info_g);
        dfImage = findViewById(R.id.info_d);
        nfImage = findViewById(R.id.info_n);
        vImage = findViewById(R.id.info_v);

        // Get extras from index screen
        indexIntent = getIntent();
        id = indexIntent.getIntExtra("IdIndex", 0);

        // Fetch the record
        DatabaseHelper databaseHelper = new DatabaseHelper(ReadRecipeActivity.this);
        Recipe recipe = databaseHelper.getRecipeByCode(id);

        name = recipe.getRecipeName();
        imagePath = recipe.getRecipePath();
        ingredients = recipe.getRecipeIngredients();
        directions = recipe.getRecipeDirections();
        time = recipe.getRecipeCookTime();
        servings = recipe.getRecipeServings();
        alg_gf = recipe.getAllergenGF();
        alg_df = recipe.getAllergenDF();
        alg_nf = recipe.getAllergenNF();
        alg_v = recipe.getAllergenV();

        // Set the view
        int length = 0;

        length = name.length();
        if (length > 0) {
            toolbar.setSubtitle(name);
        } else {
            toolbar.setSubtitle("Not Defined");
        }

        length = ingredients.length();
        if (length > 0) {
            ingredientsView.setText(ingredients);
        }

        length = directions.length();
        if (length > 0) {
            directionsView.setText(directions);
        }

        length = servings.length();
        if (length > 0) {
            servingsView.setText("Serves: " + servings);
        }

        length = time.length();
        if (length > 0) {
            cookTimeView.setText("Time: " + time);
        }

        if (alg_gf == 1) {
            gfImage.setVisibility(View.VISIBLE);
        } else {
            gfImage.setVisibility(View.GONE);
        }
        if (alg_df == 1) {
            dfImage.setVisibility(View.VISIBLE);
        } else {
            dfImage.setVisibility(View.GONE);
        }
        if (alg_nf == 1) {
            nfImage.setVisibility(View.VISIBLE);
        } else {
            nfImage.setVisibility(View.GONE);
        }
        if (alg_v == 1) {
            vImage.setVisibility(View.VISIBLE);
        } else {
            vImage.setVisibility(View.GONE);
        }

        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            recipeImage.setImageBitmap(myBitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 777 && resultCode == RESULT_OK) {
                if (data.getStringExtra("OperationType").equals("UPDATE")) {

                    // Get extras from index screen
                    int recipe_id = data.getIntExtra("IdEdit", 0);

                    // Fetch the record
                    DatabaseHelper databaseHelper = new DatabaseHelper(ReadRecipeActivity.this);
                    Recipe recipe = databaseHelper.getRecipeByCode(recipe_id);

                    String recipe_name = recipe.getRecipeName();
                    String recipe_imagePath = recipe.getRecipePath();
                    String recipe_ingredients = recipe.getRecipeIngredients();
                    String recipe_directions = recipe.getRecipeDirections();
                    String recipe_servings = recipe.getRecipeServings();
                    String recipe_cook_time = recipe.getRecipeCookTime();
                    int recipe_gf = recipe.getAllergenGF();
                    int recipe_df = recipe.getAllergenDF();
                    int recipe_nf = recipe.getAllergenNF();
                    int recipe_v = recipe.getAllergenV();

                    // Update the view
                    int length = 0;

                    length = recipe_name.length();
                    if (length > 0) {
                        toolbar.setSubtitle(recipe_name);
                    } else {
                        toolbar.setSubtitle("Not Defined");
                    }

                    length = recipe_ingredients.length();
                    if (length > 0) {
                        ingredientsView.setText(recipe_ingredients);
                    }

                    length = recipe_directions.length();
                    if (length > 0) {
                        directionsView.setText(recipe_directions);
                    }

                    length = recipe_servings.length();
                    if (length > 0) {
                        servingsView.setText("Serves: " + recipe_servings);
                    }

                    length = recipe_cook_time.length();
                    if (length > 0) {
                        cookTimeView.setText("Time: " + recipe_cook_time);
                    }

                    if (recipe_gf == 1) {
                        gfImage.setVisibility(View.VISIBLE);
                    } else {
                        gfImage.setVisibility(View.GONE);
                    }
                    if (recipe_df == 1) {
                        dfImage.setVisibility(View.VISIBLE);
                    } else {
                        dfImage.setVisibility(View.GONE);
                    }
                    if (recipe_nf == 1) {
                        nfImage.setVisibility(View.VISIBLE);
                    } else {
                        nfImage.setVisibility(View.GONE);
                    }
                    if (recipe_v == 1) {
                        vImage.setVisibility(View.VISIBLE);
                    } else {
                        vImage.setVisibility(View.GONE);
                    }

                    File imgFile = new File(recipe_imagePath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        recipeImage.setImageBitmap(myBitmap);
                    }
                } else if (data.getStringExtra("OperationType").equals("CANCEL")) {
                } else if (data.getStringExtra("OperationType").equals("DELETE")) {
                    // Go to Index Recipes screen
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Must select an image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Adds items to the action bar
        getMenuInflater().inflate(R.menu.view_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionEdit:
                actionEdit();
                return true;

            default:
                // If user's action not recognized, invoke superclass to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    public void actionEdit() {
        //Go to Edit Recipe screen
        Intent intent = new Intent(getBaseContext(), UpdateRecipeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("IdView", id);
        intent.putExtra("OperationType", "VIEW");
        startActivityForResult(intent, 777);
    }
}
