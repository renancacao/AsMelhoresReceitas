package com.rcacao.asmelhoresreceitas.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.data.models.RecipeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeItensAdapter extends RecyclerView.Adapter<RecipeItensAdapter.RecipeItensViewHolder> {

    private final static int VIEW_INGREDIENT = 0;
    private final static int VIEW_STEP = 1;

    private ArrayList<RecipeItem> itens;

    public RecipeItensAdapter(ArrayList<RecipeItem> itens) {
        this.itens = itens;
    }

    @NonNull
    @Override
    public RecipeItensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        switch (viewType){
            case VIEW_INGREDIENT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_layout,parent,false);
                break;

            default: //step
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_layout,parent,false);
                break;
        }


        return new RecipeItensViewHolder(v, viewType);

    }

    @Override
    public int getItemViewType(int position) {

        if (RecipeItem.TYPE_INGREDIENT.equals(itens.get(position).getType())){
            return VIEW_INGREDIENT;
        }
        else{
            return VIEW_STEP;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItensViewHolder holder, int position) {

        RecipeItem item = itens.get(position);

        if (RecipeItem.TYPE_INGREDIENT.equals(item.getType())){

            holder.textViewIngredient.setText(item.getText());
            holder.textViewQuantity.setText(item.getTextAux1());
            holder.textViewMeasure.setText(item.getTextAux2());

        }
        else {
            //Step

            holder.textViewId.setText(item.getTextAux1());
            holder.textViewStep.setText(item.getText());

            if (item.getVideoUrl() == null || item.getVideoUrl().isEmpty()){
                holder.imageViewClip.setVisibility(View.INVISIBLE);
            }
            else {
                holder.imageViewClip.setVisibility(View.VISIBLE);
            }

            if (item.getImageUrl() == null || item.getImageUrl().isEmpty()){
                holder.imageViewStep.setVisibility(View.GONE);
            }
            else {
                holder.imageViewStep.setVisibility(View.VISIBLE);
                Picasso.get().load(item.getImageUrl()).into(holder.imageViewStep);
            }

        }


    }

    @Override
    public int getItemCount() {
        if (itens != null) {
            return itens.size();
        }
        else{
            return 0;
        }
    }

    public void setItens(ArrayList<RecipeItem> itens) {
        this.itens = itens;
    }


    class RecipeItensViewHolder extends RecyclerView.ViewHolder {

        TextView textViewId;
        TextView textViewStep;
        TextView textViewIngredient;
        TextView textViewQuantity;
        TextView textViewMeasure;

        ImageView imageViewClip;
        ImageView imageViewStep;

        RecipeItensViewHolder(View itemView,  int viewType) {

            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewId);
            textViewStep = itemView.findViewById(R.id.textViewStep);
            textViewIngredient = itemView.findViewById(R.id.textViewIngredient);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewMeasure = itemView.findViewById(R.id.textViewMeasure);

            imageViewClip = itemView.findViewById(R.id.imageViewClip);
            imageViewStep = itemView.findViewById(R.id.imageViewStep);


        }



    }


}
