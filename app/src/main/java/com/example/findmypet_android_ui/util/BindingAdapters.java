package com.example.findmypet_android_ui.util;

import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet_android_ui.R;

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            view.setImageResource(R.drawable.placeholder_image);
            return;
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image);

        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(options)
                .into(view);
    }
}
