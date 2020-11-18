package com.example.riichicompanion;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

public enum Wind {
    East {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.east_black, context.getTheme());
        }
    },
    South {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.south_black, context.getTheme());
        }
    },
    West {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.west_black, context.getTheme());
        }
    },
    North {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.north_black, context.getTheme());
        }
    };

    public abstract Drawable getImage(Context context);
}
