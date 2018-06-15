package com.rcacao.asmelhoresreceitas.Utils;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MyUtils {

    public static void loadImage(ImageView imageView, String url) {

        if (!url.isEmpty()){
            Picasso.get().load(url).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

    }
}
