package com.tungsten.fcllibrary.component.theme;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.MaterialColors;
import com.mio.util.ImageUtil;
import com.tungsten.fclauncher.utils.FCLPath;
import com.tungsten.fclcore.util.io.FileUtils;
import com.tungsten.fcllibrary.R;
import com.tungsten.fcllibrary.util.ConvertUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ThemeEngine {

    public boolean initialized;
    public static ThemeEngine instance;
    public Handler handler;
    public HashMap<View, Runnable> runnables;
    public Theme theme;

    public ThemeEngine() {

    }

    public static ThemeEngine getInstance() {
        if (instance == null) {
            instance = new ThemeEngine();
        }
        return instance;
    }

    public void setupThemeEngine(Context context) {
        if (!initialized) {
            handler = new Handler();
            theme = Theme.getTheme(context);
            runnables = new HashMap<>();
            initialized = true;
        }
    }

    public void registerEvent(View view, Runnable runnable) {
        runnables.put(view, runnable);
        handler.post(runnable);
    }

    public void unregisterEvent(View view) {
        runnables.remove(view);
    }

    public Theme getTheme() {
        return theme;
    }

    public static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    public static int getSystemAutoTint(Context context) {
        return isNightMode(context) ? Color.WHITE : Color.BLACK;
    }

    public void applyColor(int color) {
        theme.setColor(color);
        for (View view : runnables.keySet()) {
            if (view != null && runnables.get(view) != null) {
                handler.post(runnables.get(view));
            }
        }
    }

    public void applyColor2(int color) {
        theme.setColor2(color);
        for (View view : runnables.keySet()) {
            if (view != null && runnables.get(view) != null) {
                handler.post(runnables.get(view));
            }
        }
    }
    
    // Material 3 color application methods
    public void applyMaterial3Colors(Context context) {
        if (DynamicColors.isDynamicColorAvailable()) {
            // Use dynamic colors if available (Android 12+)
            try {
                int primary = MaterialColors.getColor(context, com.google.android.material.R.attr.colorPrimary, Color.parseColor("#6750A4"));
                int secondary = MaterialColors.getColor(context, com.google.android.material.R.attr.colorSecondary, Color.parseColor("#625B71"));
                int surface = MaterialColors.getColor(context, com.google.android.material.R.attr.colorSurface, Color.parseColor("#FFFBFE"));
                int background = MaterialColors.getColor(context, com.google.android.material.R.attr.colorBackground, Color.parseColor("#FFFBFE"));
                int onPrimary = MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnPrimary, Color.parseColor("#FFFFFF"));
                int onSecondary = MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnSecondary, Color.parseColor("#FFFFFF"));
                int onSurface = MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnSurface, Color.parseColor("#1C1B1F"));
                int onBackground = MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnBackground, Color.parseColor("#1C1B1F"));
                
                theme.setPrimaryColor(primary);
                theme.setSecondaryColor(secondary);
                theme.setSurfaceColor(surface);
                theme.setBackgroundColor(background);
                theme.setOnPrimaryColor(onPrimary);
                theme.setOnSecondaryColor(onSecondary);
                theme.setOnSurfaceColor(onSurface);
                theme.setOnBackgroundColor(onBackground);
            } catch (Exception e) {
                // Fallback to default Material 3 colors
                applyDefaultMaterial3Colors(context);
            }
        } else {
            applyDefaultMaterial3Colors(context);
        }
        
        // Notify all registered views
        for (View view : runnables.keySet()) {
            if (view != null && runnables.get(view) != null) {
                handler.post(runnables.get(view));
            }
        }
    }
    
    private void applyDefaultMaterial3Colors(Context context) {
        boolean isNightMode = isNightMode(context);
        if (isNightMode) {
            theme.setPrimaryColor(Color.parseColor("#D0BCFF"));
            theme.setSecondaryColor(Color.parseColor("#CCC2DC"));
            theme.setSurfaceColor(Color.parseColor("#1C1B1F"));
            theme.setBackgroundColor(Color.parseColor("#1C1B1F"));
            theme.setOnPrimaryColor(Color.parseColor("#371E73"));
            theme.setOnSecondaryColor(Color.parseColor("#332D41"));
            theme.setOnSurfaceColor(Color.parseColor("#E6E0E9"));
            theme.setOnBackgroundColor(Color.parseColor("#E6E0E9"));
        } else {
            theme.setPrimaryColor(Color.parseColor("#6750A4"));
            theme.setSecondaryColor(Color.parseColor("#625B71"));
            theme.setSurfaceColor(Color.parseColor("#FFFBFE"));
            theme.setBackgroundColor(Color.parseColor("#FFFBFE"));
            theme.setOnPrimaryColor(Color.parseColor("#FFFFFF"));
            theme.setOnSecondaryColor(Color.parseColor("#FFFFFF"));
            theme.setOnSurfaceColor(Color.parseColor("#1C1B1F"));
            theme.setOnBackgroundColor(Color.parseColor("#1C1B1F"));
        }
    }

    public void applyFullscreen(Window window, boolean fullscreen) {
        theme.setFullscreen(fullscreen);
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WindowManager.LayoutParams params = window.getAttributes();
                if (fullscreen) {
                    params.layoutInDisplayCutoutMode = WindowManager.LayoutParams. LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES ;
                } else {
                    params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
                }
                window.setAttributes(params);
            }
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void applyBackground(Context context, View view, String ltPath, String dkPath) {
        try {
            if (ltPath != null && new File(ltPath).exists()) {
                FileUtils.copyFile(new File(ltPath), new File(FCLPath.LT_BACKGROUND_PATH));
            }
            if (dkPath != null && new File(dkPath).exists()) {
                FileUtils.copyFile(new File(dkPath), new File(FCLPath.DK_BACKGROUND_PATH));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Bitmap ltBitmap = ImageUtil.load(FCLPath.LT_BACKGROUND_PATH).orElse(ConvertUtils.getBitmapFromRes(context, R.drawable.background_light));
        final Bitmap dkBitmap = ImageUtil.load(FCLPath.DK_BACKGROUND_PATH).orElse(ConvertUtils.getBitmapFromRes(context, R.drawable.background_dark));
        BitmapDrawable lt = new BitmapDrawable(context.getResources(), ltBitmap);
        BitmapDrawable dk = new BitmapDrawable(context.getResources(), dkBitmap);
        theme.setBackgroundLt(lt);
        theme.setBackgroundDk(dk);
        boolean isNightMode = (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        ImageUtil.loadInto(view, isNightMode ? dk : lt);
    }

    public void applyAndSave(Context context, int color) {
        applyColor(color);
        Theme.saveTheme(context, theme);
    }

    public void applyAndSave2(Context context, int color) {
        applyColor2(color);
        Theme.saveTheme(context, theme);
    }

    public void applyAndSave(Context context, Window window, boolean fullscreen) {
        applyFullscreen(window, fullscreen);
        Theme.saveTheme(context, theme);
    }

    public void applyAndSave(Context context, View view, String lt, String dk) {
        applyBackground(context, view, lt, dk);
        Theme.saveTheme(context, theme);
    }

    public void applyAndSave(Context context, Window window, Theme theme) {
        applyColor(theme.getColor());
        applyMaterial3Colors(context);
        applyFullscreen(window, theme.isFullscreen());
        Theme.saveTheme(context, theme);
    }

    public static int getWallpaperColor(Context context) {
        int color = Color.parseColor("#7797CF");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            WallpaperColors colors = WallpaperManager.getInstance(context).getWallpaperColors(WallpaperManager.FLAG_SYSTEM);
            if (colors != null) {
                color = colors.getPrimaryColor().toArgb();
            }
        }
        return color;
    }
}
