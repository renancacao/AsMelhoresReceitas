package com.rcacao.asmelhoresreceitas.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.adapter.RecipeItemAdapter;
import com.rcacao.asmelhoresreceitas.data.models.GenericItem;
import com.rcacao.asmelhoresreceitas.data.models.ListItem;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment  implements RecipeItemAdapter.RecipeItemAdapterClickHandler {


    @BindView(R.id.recyclerViewRecipeItens)
    RecyclerView recyclerViewRecipeItens;

    ArrayList<ListItem> itens;
    RecipeItemAdapter adapter;

    private int inicioSteps;
    private Recipe recipe = null;

    private OnStepClickListener mCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewRecipeItens.setLayoutManager(layoutManager);

        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " precisa implementar OnStepClickListener");
        }
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }



    public void loadItens() {

        if (recipe==null){
            return;
        }

        itens = new ArrayList<>();

        //nome da receita
        inicioSteps++;
        itens.add(new GenericItem(recipe.getName(), recipe.getServings() + " porções", recipe.getImage()));

        //ingredientes
        inicioSteps++;
        itens.add(new GenericItem("Ingredientes", "",""));

        inicioSteps+=recipe.getIngredients().length;
        itens.addAll(Arrays.asList(recipe.getIngredients()));

        //passo a passo
        inicioSteps++;
        itens.add(new GenericItem("Passo a passo", "",""));

        itens.addAll(Arrays.asList(recipe.getSteps()));

        adapter = new RecipeItemAdapter(itens, this);
        recyclerViewRecipeItens.setAdapter(adapter);


    }

    @Override
    public void clickRecipeItem(int id) {

        id = id-inicioSteps;
        mCallback.onClickStep(id);

    }

    public interface OnStepClickListener{
        void onClickStep(int id);
    }
}
