package com.rcacao.asmelhoresreceitas.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.utils.MyUtils;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Recipe[] recipes;
    private final RecipeAdapterClickHandler handler;

    public interface RecipeAdapterClickHandler{
        void clickRecipe(int id);
    }

    public RecipeAdapter(Recipe[] recipes, RecipeAdapterClickHandler handler) {
        this.recipes = recipes;
        this.handler = handler;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout,parent,false);
        return new RecipeViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipes[position];
        holder.textViewRecipe.setText(recipe.getName());
        holder.textViewServings.setText(String.valueOf(recipe.getServings()));
        MyUtils.loadImage(holder.imageViewRecipe,recipe.getImage());
        holder.bindClick(position);

    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.length;
        }
        else{
            return 0;
        }
    }

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewRecipe)
        TextView textViewRecipe;

        @BindView(R.id.imageViewRecipe)
        ImageView imageViewRecipe;

        @BindView(R.id.textViewServings)
        TextView textViewServings;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        void bindClick(final int id){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.clickRecipe(id);
                }
            });
        }


    }


}
