package com.example.riichicompanion;

public enum ThemeOption {
    Dark {
        @Override
        public int getThemeId() {
            return R.style.Theme_AppCompat_NoActionBar;
        }
    },
    Light {
        @Override
        public int getThemeId() {
            return R.style.Theme_AppCompat_Light_NoActionBar;
        }
    };

    public abstract int getThemeId();
}
