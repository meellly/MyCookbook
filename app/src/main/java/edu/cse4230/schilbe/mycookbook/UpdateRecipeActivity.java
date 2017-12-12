package edu.cse4230.schilbe.mycookbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpdateRecipeActivity extends AppCompatActivity {
    EditText editName = null, editIngredients = null, editDirections = null, editCookTime = null, editServing = null;
    ImageButton recipeImage = null;
    CheckBox allergenGF = null, allergenDF = null, allergenNF = null, allergenV = null;
    Intent viewIntent = null;
    String name = null, imagePath = null, ingredients = null, directions = null, time = null, servings = null;
    int id = 0, alg_gf = 0, alg_df = 0, alg_nf = 0, alg_v = 0;
    final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        // Setup the App Bar
        Toolbar toolbar = findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("Edit Recipe");
        toolbar.setSubtitleTextColor(0xFFECF0F1);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Disable the "Up" button
        actionBar.setDisplayHomeAsUpEnabled(false);

        editName = findViewById(R.id.editName);
        editIngredients = findViewById(R.id.editIngredients);
        editDirections = findViewById(R.id.editDirections);
        recipeImage = findViewById(R.id.recipeImageButton);
        editCookTime = findViewById(R.id.editTime);
        editServing = findViewById(R.id.editServing);
        allergenGF = findViewById(R.id.allergenGf);
        allergenDF = findViewById(R.id.allergenDf);
        allergenNF = findViewById(R.id.allergenNf);
        allergenV = findViewById(R.id.allergenV);

        // Get extras from index screen
        viewIntent = getIntent();
        id = viewIntent.getIntExtra("IdView", 0);

        // Fetch the record
        DatabaseHelper databaseHelper = new DatabaseHelper(UpdateRecipeActivity.this);
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

        // Set view
        editName.setText(name);
        editIngredients.setText(ingredients);
        editDirections.setText(directions);
        editCookTime.setText(time);
        editServing.setText(servings);

        if (alg_gf == 1) {
            allergenGF.setChecked(true);
        }
        if (alg_df == 1) {
            allergenDF.setChecked(true);
        }
        if (alg_nf == 1) {
            allergenNF.setChecked(true);
        }
        if (alg_v == 1) {
            allergenV.setChecked(true);
        }

        String imagePath = recipe.getRecipePath();
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            recipeImage.setImageBitmap(myBitmap);
        }

        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images (no videos, etc)
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Adds items to the action bar
        getMenuInflater().inflate(R.menu.edit_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionCancel:
                actionCancel();
                return true;

            case R.id.actionSave:
                actionSave();
                return true;


            case R.id.actionDelete:
                actionDelete();
                return true;

            default:
                // If user's action not recognized, invoke superclass to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    public void actionDelete() {
        Toast.makeText(getBaseContext(), "Recipe was deleted", Toast.LENGTH_SHORT).show();

        // Delete the record
        DatabaseHelper databaseHelper = new DatabaseHelper(UpdateRecipeActivity.this);
        databaseHelper.deleteRecipe(id);

        //Go back to View Recipe screen
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("OperationType", "DELETE");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void actionCancel() {
        Toast.makeText(getBaseContext(), "Recipe was not updated", Toast.LENGTH_SHORT).show();

        // Go to View Recipe screen
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("OperationType", "CANCEL");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void actionSave() {
        Toast.makeText(getBaseContext(), "Recipe was successfully updated", Toast.LENGTH_SHORT).show();

        // Update record
        String recipe_name = editName.getText().toString();
        String recipe_path = imagePath;
        String recipe_ingredients = editIngredients.getText().toString();
        String recipe_directions = editDirections.getText().toString();
        String recipe_cook_time = editCookTime.getText().toString();
        String recipe_servings = editServing.getText().toString();

        int allergen_gf = 0, allergen_df = 0, allergen_nf = 0, allergen_v = 0;
        if (allergenGF.isChecked()) {
            allergen_gf = 1;
        }
        if (allergenDF.isChecked()) {
            allergen_df = 1;
        }
        if (allergenNF.isChecked()) {
            allergen_nf = 1;
        }
        if (allergenV.isChecked()) {
            allergen_v = 1;
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(UpdateRecipeActivity.this);
        databaseHelper.updateRecipe(id, recipe_name, recipe_path, recipe_ingredients, recipe_directions, recipe_cook_time, recipe_servings, allergen_gf, allergen_df, allergen_nf, allergen_v);

        // Go back to View Recipe screen
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("IdEdit", id);
        intent.putExtra("OperationType", "UPDATE");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            File tempFile = new File(imagePath);

            Uri uri = data.getData();


            //Copy URI contents into temporary file.
            try {
                tempFile.createNewFile();
                copyAndClose(this.getContentResolver().openInputStream(data.getData()), new FileOutputStream(tempFile));
            } catch (IOException e) {
                //Log Error
            }

            //Now fetch the new URI
            Uri newUri = Uri.fromFile(tempFile);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                // Log.d(TAG, String.valueOf(bitmap));

                recipeImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyAndClose(InputStream inputStream, FileOutputStream fileOutputStream) {
        try {
            byte[] buffer = new byte[4096]; // To hold file contents
            int bytes_read; // How many bytes in buffer

            // Read a chunk of bytes into the buffer, then write them out,
            // looping until we reach the end of the file (when read() returns
            // -1). Note the combination of assignment and comparison in this
            // while loop. This is a common I/O programming idiom.
            while ((bytes_read = inputStream.read(buffer)) != -1)
                // Read until EOF
                fileOutputStream.write(buffer, 0, bytes_read); // write
        } catch (Exception e) {

        }
        // Always close the streams, even if exceptions were thrown
        finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    ;
                }
            if (fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    ;
                }
        }
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public String getPhotoPath(Uri uri) {
        if (isMediaDocument(uri)) {
            final String docId;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                docId = DocumentsContract.getDocumentId(uri);

                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(this, contentUri, selection, selectionArgs);
            }
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {MediaStore.Images.Media.DATA};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
