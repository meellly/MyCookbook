package edu.cse4230.schilbe.mycookbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter {

    Context context;
    ArrayList<Recipe> recipeList;
    private static LayoutInflater inflater = null;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.layout_grid_item, null);

        TextView id = convertView.findViewById(R.id.idView);
        ImageView recipeImage = convertView.findViewById(R.id.recipeImage);
        TextView recipe_name = convertView.findViewById(R.id.recipeName);

        Recipe recipe = recipeList.get(position);
        id.setText(String.valueOf(recipe.getId()));

        recipe_name.setText(recipe.getRecipeName());

        String imagePath = recipe.getRecipePath();
        File imgFile = new File(imagePath);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            recipeImage.setImageBitmap(myBitmap);
        }
        return convertView;
    }
}
