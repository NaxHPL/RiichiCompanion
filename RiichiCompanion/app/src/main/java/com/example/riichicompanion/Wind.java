package com.example.riichicompanion;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

public enum Wind {
    East {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.east, context.getTheme());
        }
    },
    South {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.south, context.getTheme());
        }
    },
    West {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.west, context.getTheme());
        }
    },
    North {
        @Override
        public Drawable getImage(Context context) {
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.north, context.getTheme());
        }
    };

    public abstract Drawable getImage(Context context);
}
