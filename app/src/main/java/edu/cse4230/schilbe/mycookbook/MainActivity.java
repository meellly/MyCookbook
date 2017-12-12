package edu.cse4230.schilbe.mycookbook;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/******************************************************************************
 * My Cookbook
 * Author: Melissa Schilbe
 *
 * Source Code: https://github.com/meellly/MyCookbook
 *
 * Application features:
 * - Recipe and cooking application
 * - All CRUD operations can be performed on a recipe
 * - Dynamic toolbar for easy navigation and access to page operations
 * - Filtering on different food allergies can be done from the main list page
 *
 * To Use:
 * STEP 1 - Create some recipes
 * Note: provided some nice recipe images in a zip file on Moodle submission
 * a. From main screen, click on plus button
 * b. Fill out fields  and select an image (FYI - an image is required to store the recipe)
 * c. Check off some food allergy options at the bottom of the page
 * d. Click the save (Can also click the back arrow or cancel option on the toolbar)
 * e. Repeat to get multiple recipes in the view
 * STEP 2 - View a recipe
 * a. From main screen, select a recipe from the list
 * b. Use the scrollable view to see all ingredient and directions
 * STEP 3 - Edit a recipe
 * a. From View Recipe screen, click on settings menu in the toolbar (3 vertical dots on top right)
 * b. Click ‘Edit’ option (Can also click ‘up arrow’ on toolbar to go back to list view)
 * c. Edit desired fields or swap out the image
 * d. Click “SAVE” (Can also click “CANCEL” or “DELETE” from toolbar)
 * STEP 4 - Delete a recipe
 * a. From Edit Recipe screen, click on settings menu in the toolbar (3 vertical dots on top right)
 * b. Click on settings menu in the toolbar (3 vertical dots on top right)
 * c. Click on “Delete”
 * d. Should be routed back to main screen and that recipe should be removed from the list
 * STEP 5 - Filter recipes
 * a. From main screen, click on one of 4 available food allergy options at top of screen (GF, DF, NF, V)
 * b. The list of recipes will be limited to the option selected
 * c. Click on the “X” to clear the filters, all the recipes should now be displayed
 *****************************************************************************/


public class MainActivity extends AppCompatActivity {
    TextView filterText = null;
    ImageButton addButton = null, filterGF = null, filterDF = null, filterNF = null, filterV = null, filterClear = null;
    GridView listView = null;
    ArrayList<Recipe> recipeList;
    RecipeAdapter adapter = null;
    int setFilterGF = 0, setFilterDF = 0, setFilterNF = 0, setFilterV = 0;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the App Bar
        toolbar = findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("My Cookbook");
        toolbar.setSubtitle("");
        toolbar.setSubtitleTextColor(0xFFECF0F1);
        toolbar.setTitleTextColor(0xFFECF0F1);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Disable the "Up" button
        actionBar.setDisplayHomeAsUpEnabled(false);

        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);
        filterGF = findViewById(R.id.filterGF);
        filterDF = findViewById(R.id.filterDF);
        filterNF = findViewById(R.id.filterNF);
        filterV = findViewById(R.id.filterV);
        filterClear = findViewById(R.id.filterClear);
        filterText = findViewById(R.id.filterText);

        filterGF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilterGF = 1;
                setFilterDF = 0;
                setFilterNF = 0;
                setFilterV = 0;
                filterList(setFilterGF, setFilterDF, setFilterNF, setFilterV);
                filterGF.setVisibility(View.GONE);
                filterDF.setVisibility(View.GONE);
                filterNF.setVisibility(View.GONE);
                filterV.setVisibility(View.GONE);
                filterClear.setVisibility(View.VISIBLE);
                filterText.setVisibility(View.VISIBLE);
                filterText.setText("Gluten Free");
            }
        });

        filterDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilterGF = 0;
                setFilterDF = 1;
                setFilterNF = 0;
                setFilterV = 0;
                filterList(setFilterGF, setFilterDF, setFilterNF, setFilterV);
                filterGF.setVisibility(View.GONE);
                filterDF.setVisibility(View.GONE);
                filterNF.setVisibility(View.GONE);
                filterV.setVisibility(View.GONE);
                filterClear.setVisibility(View.VISIBLE);
                filterText.setVisibility(View.VISIBLE);
                filterText.setText("Dairy Free");
            }
        });

        filterNF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilterGF = 0;
                setFilterDF = 0;
                setFilterNF = 1;
                setFilterV = 0;
                filterList(setFilterGF, setFilterDF, setFilterNF, setFilterV);
                filterGF.setVisibility(View.GONE);
                filterDF.setVisibility(View.GONE);
                filterNF.setVisibility(View.GONE);
                filterV.setVisibility(View.GONE);
                filterClear.setVisibility(View.VISIBLE);
                filterText.setVisibility(View.VISIBLE);
                filterText.setText("Nut Free");
            }
        });

        filterV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilterGF = 0;
                setFilterDF = 0;
                setFilterNF = 0;
                setFilterV = 1;
                filterList(setFilterGF, setFilterDF, setFilterNF, setFilterV);
                filterGF.setVisibility(View.GONE);
                filterDF.setVisibility(View.GONE);
                filterNF.setVisibility(View.GONE);
                filterV.setVisibility(View.GONE);
                filterClear.setVisibility(View.VISIBLE);
                filterText.setVisibility(View.VISIBLE);
                filterText.setText("Vegan");
            }
        });

        filterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilterGF = 0;
                setFilterDF = 0;
                setFilterNF = 0;
                setFilterV = 0;
                filterList(setFilterGF, setFilterDF, setFilterNF, setFilterV);
                filterGF.setVisibility(View.VISIBLE);
                filterDF.setVisibility(View.VISIBLE);
                filterNF.setVisibility(View.VISIBLE);
                filterV.setVisibility(View.VISIBLE);
                filterClear.setVisibility(View.GONE);
                filterText.setVisibility(View.GONE);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to Add Recipe screen
                Intent intent = new Intent(view.getContext(), CreateRecipeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(intent, 999);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string_id = ((TextView) view.findViewById(R.id.idView)).getText().toString();
                int id = Integer.parseInt(string_id);

                // Go to View Recipe screen
                Intent intent = new Intent(view.getContext(), ReadRecipeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("IdIndex", id);
                intent.putExtra("OperationType", "INDEX");
                startActivity(intent);
            }
        });
        updateList();
    }

    public void onResume() {
        super.onResume();
        filterList(setFilterGF, setFilterDF, setFilterNF, setFilterV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 777 && resultCode == RESULT_OK && null != data) {
                if (data.getStringExtra("OperationType").equals("ADD")) {
                    updateList();
                } else if (data.getStringExtra("OperationType").equals("CANCEL")) {
                } else {
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Must select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        recipeList = new ArrayList<Recipe>();
        recipeList = databaseHelper.getAllRecipes();
        adapter = new RecipeAdapter(MainActivity.this, recipeList);
        listView.setAdapter(adapter);
    }

    private void filterList(int gf, int df, int nf, int v) {
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        recipeList = new ArrayList<Recipe>();
        recipeList = databaseHelper.getFilteredRecipes(gf, df, nf, v);
        adapter = new RecipeAdapter(MainActivity.this, recipeList);
        listView.setAdapter(adapter);
    }
}
