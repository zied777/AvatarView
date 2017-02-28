package me.fahmisdk6.avatarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import me.fahmisdk6.avatarview.rounded.DynamicRoundedImageView;

/**
 * Created by USER on 2/13/2017.
 */

public class AvatarView extends FrameLayout {

    DynamicRoundedImageView imgAvatar;
    FontTextView textAvatar;

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_avatar, this);
        imgAvatar = (DynamicRoundedImageView) findViewById(R.id.round_img_avatar);
        textAvatar = (FontTextView) findViewById(R.id.text_avatar_name);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.AvatarView);
        int cornerRadius = styledAttrs.getDimensionPixelSize(R.styleable.AvatarView_cornerRadius, 0);
        styledAttrs.recycle();


        imgAvatar.setCornerRadius((float) cornerRadius);
        GradientDrawable drawable = (GradientDrawable) textAvatar.getBackground();
        drawable.setCornerRadius(cornerRadius);
        textAvatar.invalidate();


    }


    public void bind(String name, String pic) {
        textAvatar.setVisibility(VISIBLE);
        if(!TextUtils.isEmpty(name)){
            String nameArray[] = name.split(" ");
            String initial = "";
            for (int i = 0; i < nameArray.length; i++) {
                initial += nameArray[i].charAt(0);
            }
            textAvatar.setText(initial);
        }

        if (TextUtils.isEmpty(pic)) {
            imgAvatar.setImageDrawable(null);
        } else {
            Glide.with(getContext())
                    .load(pic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            textAvatar.setVisibility(GONE);
                            return false;
                        }
                    })
                    .into(imgAvatar);
        }
    }
}