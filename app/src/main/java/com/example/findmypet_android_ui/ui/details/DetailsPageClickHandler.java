package com.example.findmypet_android_ui.ui.details;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.findmypet_android_ui.model.Poster;

public class EditButtonClickHandlers {

    private Context context;

    public EditButtonClickHandlers(Context context) {
        this.context = context;
    }


    public void onEditButtonClicked(View view, Poster poster) {
        Intent intent = new Intent(context, EditPosterActivity.class);

        intent.putExtra("poster", poster);
        context.startActivity(intent);
    }
}
