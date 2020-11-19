package com.example.riichicompanion;

public enum ThemeOption {
    Auto {
        @Override
        public int getThemeId() {
            return R.style.Theme_AppCompat_DayNight_NoActionBar;
        }
    },
    Light {
        @Override
        public int getThemeId() {
            return R.style.Theme_AppCompat_Light_NoActionBar;
        }
    },
    Dark {
        @Override
        public int getThemeId() {
            return R.style.Theme_AppCompat_NoActionBar;
        }
    };

    public abstract int getThemeId();
}
