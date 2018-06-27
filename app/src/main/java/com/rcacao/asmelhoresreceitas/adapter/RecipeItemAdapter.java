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
import com.rcacao.asmelhoresreceitas.data.models.ListItem;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.RecipeItensViewHolder> {

    private final static int VIEW_INGREDIENT = 0;
    private final static int VIEW_STEP = 1;
    private final static int VIEW_GENERIC = 2;

    private final ArrayList<ListItem> itens;
    private final RecipeItemAdapterClickHandler handler;

    public interface RecipeItemAdapterClickHandler{
        void clickRecipeItem(int id);
    }

    public RecipeItemAdapter(ArrayList<ListItem> itens, RecipeItemAdapterClickHandler handler) {
        this.itens = itens;
        this.handler = handler;
    }

    @NonNull
    @Override
    public RecipeItensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        switch (viewType){
            case VIEW_INGREDIENT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_layout,parent,false);
                break;

            case VIEW_STEP:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_layout,parent,false);
                break;

            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_layout,parent,false);
                break;
        }


        return new RecipeItensViewHolder(v);

    }

    @Override
    public int getItemViewType(int position) {

        switch (itens.get(position).getType()) {
            case ListItem.TYPE_INGREDIENT:
                return VIEW_INGREDIENT;
            case ListItem.TYPE_STEP:
                return VIEW_STEP;
            default:
                return VIEW_GENERIC;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItensViewHolder holder, int position) {

        ListItem item = itens.get(position);

        switch (item.getType()) {
            case ListItem.TYPE_INGREDIENT:

                holder.textViewIngredient.setText(item.getTitle());
                holder.textViewQuantity.setText(item.getAuxText1());
                holder.textViewMeasure.setText(item.getAuxText2());

                break;
            case ListItem.TYPE_STEP:
                //Step

                holder.textViewId.setText(String.format(Locale.getDefault(), "%d.", item.getId()));
                holder.textViewStep.setText(item.getTitle());

                //video url
                if (item.getAuxText1() == null || item.getAuxText1().isEmpty()) {
                    holder.imageViewClip.setVisibility(View.INVISIBLE);
                } else {
                    holder.imageViewClip.setVisibility(View.VISIBLE);
                }

                //thumb url
                MyUtils.loadImage(holder.imageViewStep, item.getAuxText2());

                holder.bindClick(position);

                break;
            default:

                holder.textViewTitle.setText(item.getTitle());

                if (item.getAuxText1().isEmpty()) {
                    holder.textViewAux1.setVisibility(View.GONE);
                } else {
                    holder.textViewAux1.setVisibility(View.VISIBLE);
                    holder.textViewAux1.setText(item.getAuxText1());
                }

                //image generic
                MyUtils.loadImage(holder.imageViewGeneric, item.getAuxText2());

                break;
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


    class RecipeItensViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewId;
        final TextView textViewStep;
        final ImageView imageViewClip;
        final ImageView imageViewStep;

        final TextView textViewIngredient;
        final TextView textViewQuantity;
        final TextView textViewMeasure;

        final TextView textViewTitle;
        final TextView textViewAux1;
        final ImageView imageViewGeneric;

        RecipeItensViewHolder(View itemView) {

            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewId);
            textViewStep = itemView.findViewById(R.id.textViewStep);
            imageViewClip = itemView.findViewById(R.id.imageViewClip);
            imageViewStep = itemView.findViewById(R.id.imageViewStep);

            textViewIngredient = itemView.findViewById(R.id.textViewIngredient);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewMeasure = itemView.findViewById(R.id.textViewMeasure);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAux1 = itemView.findViewById(R.id.textViewAux1);
            imageViewGeneric = itemView.findViewById(R.id.imageViewGeneric);


        }

        void bindClick(final int id){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.clickRecipeItem(id);
                }
            });
        }



    }


}
