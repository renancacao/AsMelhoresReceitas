package com.rcacao.asmelhoresreceitas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rcacao.asmelhoresreceitas.R;
import com.rcacao.asmelhoresreceitas.adapter.RecipeItensAdapter;
import com.rcacao.asmelhoresreceitas.data.models.GenericItem;
import com.rcacao.asmelhoresreceitas.data.models.Recipe;
import com.rcacao.asmelhoresreceitas.data.models.ListItem;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {


    @BindView(R.id.recyclerViewRecipeItens)
    RecyclerView recyclerViewRecipeItens;

    public static final String ARG_RECIPE = "recipe";

    ArrayList<ListItem> itens;
    RecipeItensAdapter adapter;

    private Recipe recipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent !=null){
            recipe = getIntent().getParcelableExtra(ARG_RECIPE);
        }

        if (recipe==null){
            finish();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewRecipeItens.setLayoutManager(layoutManager);

        loadItens();


    }

    private void loadItens() {

        itens = new ArrayList<>();

        //nome da receita
        itens.add(new GenericItem(recipe.getName(), recipe.getServings() + " porções", recipe.getImage()));

        //ingredientes
        itens.add(new GenericItem("Ingredientes", "",""));
        itens.addAll(Arrays.asList(recipe.getIngredients()));

        //passo a passo
        itens.add(new GenericItem("Passo a passo", "",""));
        itens.addAll(Arrays.asList(recipe.getSteps()));

        adapter = new RecipeItensAdapter(itens);
        recyclerViewRecipeItens.setAdapter(adapter);


    }
}
