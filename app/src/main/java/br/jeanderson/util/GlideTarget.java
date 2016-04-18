package br.jeanderson.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by jeand on 16/04/2016.
 */
public class GlideTarget extends SimpleTarget<Bitmap> {
    private Context context;
    private RelativeLayout layout;

    public GlideTarget(Context context, RelativeLayout layout) {
        this.context = context;
        this.layout = layout;
    }

    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        Drawable drawable = new BitmapDrawable(context.getResources(),resource);
        layout.setBackground(drawable);
    }
}
