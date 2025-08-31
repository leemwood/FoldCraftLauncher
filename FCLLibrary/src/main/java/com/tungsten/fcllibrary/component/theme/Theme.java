package com.tungsten.fcllibrary.component.theme;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.graphics.ColorUtils;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.MaterialColors;
import com.mio.util.ImageUtil;
import com.tungsten.fclcore.fakefx.beans.property.BooleanProperty;
import com.tungsten.fclcore.fakefx.beans.property.IntegerProperty;
import com.tungsten.fclcore.fakefx.beans.property.ObjectProperty;
import com.tungsten.fclcore.fakefx.beans.property.SimpleBooleanProperty;
import com.tungsten.fclcore.fakefx.beans.property.SimpleIntegerProperty;
import com.tungsten.fclcore.fakefx.beans.property.SimpleObjectProperty;
import com.tungsten.fcllibrary.R;
import com.tungsten.fcllibrary.util.ConvertUtils;

public class Theme {

    private final IntegerProperty color = new SimpleIntegerProperty();
    private final IntegerProperty color2 = new SimpleIntegerProperty();
    private final IntegerProperty ltColor = new SimpleIntegerProperty();
    private final IntegerProperty dkColor = new SimpleIntegerProperty();
    private final IntegerProperty autoTint = new SimpleIntegerProperty();
    
    // Material 3 color properties
    private final IntegerProperty primaryColor = new SimpleIntegerProperty();
    private final IntegerProperty secondaryColor = new SimpleIntegerProperty();
    private final IntegerProperty surfaceColor = new SimpleIntegerProperty();
    private final IntegerProperty backgroundColor = new SimpleIntegerProperty();
    private final IntegerProperty onPrimaryColor = new SimpleIntegerProperty();
    private final IntegerProperty onSecondaryColor = new SimpleIntegerProperty();
    private final IntegerProperty onSurfaceColor = new SimpleIntegerProperty();
    private final IntegerProperty onBackgroundColor = new SimpleIntegerProperty();
    private final BooleanProperty fullscreen = new SimpleBooleanProperty();
    private final BooleanProperty closeSkinModel = new SimpleBooleanProperty();
    private final IntegerProperty animationSpeed = new SimpleIntegerProperty();
    private final ObjectProperty<BitmapDrawable> backgroundLt = new SimpleObjectProperty<>();
    private final ObjectProperty<BitmapDrawable> backgroundDk = new SimpleObjectProperty<>();

    public Theme(int color, int color2, boolean fullscreen, boolean closeSkinModel, int animationSpeed, BitmapDrawable backgroundLt, BitmapDrawable backgroundDk) {
        float[] ltHsv = new float[3];
        Color.colorToHSV(color, ltHsv);
        ltHsv[1] -= (1 - ltHsv[1]) * 0.3f;
        ltHsv[2] += (1 - ltHsv[2]) * 0.3f;
        float[] dkHsv = new float[3];
        Color.colorToHSV(color, dkHsv);
        dkHsv[1] += (1 - dkHsv[1]) * 0.3f;
        dkHsv[2] -= (1 - dkHsv[2]) * 0.3f;
        this.color.set(color);
        this.color2.set(color2);
        this.ltColor.set(Color.HSVToColor(ltHsv));
        this.dkColor.set(Color.HSVToColor(dkHsv));
        this.fullscreen.set(fullscreen);
        this.closeSkinModel.set(closeSkinModel);
        this.animationSpeed.set(animationSpeed);
        this.autoTint.set(ColorUtils.calculateLuminance(color) >= 0.5 ? Color.parseColor("#FF000000") : Color.parseColor("#FFFFFFFF"));
        this.backgroundLt.set(backgroundLt);
        this.backgroundDk.set(backgroundDk);
        
        // Initialize Material 3 colors with fallback values
        this.primaryColor.set(color);
        this.secondaryColor.set(color2);
        this.surfaceColor.set(Color.parseColor("#FFFBFE"));
        this.backgroundColor.set(Color.parseColor("#FFFBFE"));
        this.onPrimaryColor.set(Color.parseColor("#FFFFFF"));
        this.onSecondaryColor.set(Color.parseColor("#FFFFFF"));
        this.onSurfaceColor.set(Color.parseColor("#1C1B1F"));
        this.onBackgroundColor.set(Color.parseColor("#1C1B1F"));
    }

    public int getColor() {
        return color.get();
    }

    public int getColor2() {
        return color2.get();
    }

    public int getLtColor() {
        return ltColor.get();
    }

    public int getDkColor() {
        return dkColor.get();
    }

    public int getAutoTint() {
        return autoTint.get();
    }
    
    // Material 3 color getters
    public int getPrimaryColor() {
        return primaryColor.get();
    }
    
    public int getSecondaryColor() {
        return secondaryColor.get();
    }
    
    public int getSurfaceColor() {
        return surfaceColor.get();
    }
    
    public int getBackgroundColor() {
        return backgroundColor.get();
    }
    
    public int getOnPrimaryColor() {
        return onPrimaryColor.get();
    }
    
    public int getOnSecondaryColor() {
        return onSecondaryColor.get();
    }
    
    public int getOnSurfaceColor() {
        return onSurfaceColor.get();
    }
    
    public int getOnBackgroundColor() {
        return onBackgroundColor.get();
    }

    public int getAutoHintTint() {
        return ColorUtils.calculateLuminance(getColor()) >= 0.5 ? Color.parseColor("#99000000") : Color.parseColor("#99FFFFFF");
    }

    public boolean isFullscreen() {
        return fullscreen.get();
    }

    public boolean isCloseSkinModel() {
        return closeSkinModel.get();
    }

    public int getAnimationSpeed() {
        return animationSpeed.get();
    }

    public BitmapDrawable getBackgroundLt() {
        return backgroundLt.get();
    }

    public BitmapDrawable getBackgroundDk() {
        return backgroundDk.get();
    }

    public IntegerProperty colorProperty() {
        return color;
    }

    public IntegerProperty color2Property() {
        return color2;
    }

    public IntegerProperty ltColorProperty() {
        return ltColor;
    }

    public IntegerProperty dkColorProperty() {
        return dkColor;
    }

    public IntegerProperty autoTintProperty() {
        return autoTint;
    }
    
    // Material 3 color properties
    public IntegerProperty primaryColorProperty() {
        return primaryColor;
    }
    
    public IntegerProperty secondaryColorProperty() {
        return secondaryColor;
    }
    
    public IntegerProperty surfaceColorProperty() {
        return surfaceColor;
    }
    
    public IntegerProperty backgroundColorProperty() {
        return backgroundColor;
    }
    
    public IntegerProperty onPrimaryColorProperty() {
        return onPrimaryColor;
    }
    
    public IntegerProperty onSecondaryColorProperty() {
        return onSecondaryColor;
    }
    
    public IntegerProperty onSurfaceColorProperty() {
        return onSurfaceColor;
    }
    
    public IntegerProperty onBackgroundColorProperty() {
        return onBackgroundColor;
    }

    public BooleanProperty fullscreenProperty() {
        return fullscreen;
    }

    public BooleanProperty ignoreSkinContainerProperty() {
        return fullscreen;
    }

    public IntegerProperty animationSpeedProperty() {
        return animationSpeed;
    }

    public ObjectProperty<BitmapDrawable> ltBackgroundProperty() {
        return backgroundLt;
    }

    public ObjectProperty<BitmapDrawable> dkBackgroundProperty() {
        return backgroundDk;
    }

    public BitmapDrawable getBackground(Context context) {
        boolean isNightMode = (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        return isNightMode ? backgroundDk.get() : backgroundLt.get();
    }

    public void setColor(int color) {
        float[] ltHsv = new float[3];
        Color.colorToHSV(color, ltHsv);
        ltHsv[1] -= (1 - ltHsv[1]) * 0.3f;
        ltHsv[2] += (1 - ltHsv[2]) * 0.3f;
        float[] dkHsv = new float[3];
        Color.colorToHSV(color, dkHsv);
        dkHsv[1] += (1 - dkHsv[1]) * 0.3f;
        dkHsv[2] -= (1 - dkHsv[2]) * 0.3f;
        this.ltColor.set(Color.HSVToColor(ltHsv));
        this.dkColor.set(Color.HSVToColor(dkHsv));
        this.autoTint.set(ColorUtils.calculateLuminance(color) >= 0.5 ? Color.parseColor("#FF000000") : Color.parseColor("#FFFFFFFF"));
        this.color.set(color);
    }

    public void setColor2(int color) {
        this.color2.set(color);
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen.set(fullscreen);
    }

    public void setiIgnoreSkinContainer(boolean ignoreSkinContainer) {
        this.closeSkinModel.set(ignoreSkinContainer);
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed.set(animationSpeed);
    }

    public void setBackgroundLt(BitmapDrawable backgroundLt) {
        this.backgroundLt.set(backgroundLt);
    }

    public void setBackgroundDk(BitmapDrawable backgroundDk) {
        this.backgroundDk.set(backgroundDk);
    }
    
    // Material 3 color setters
    public void setPrimaryColor(int color) {
        this.primaryColor.set(color);
    }
    
    public void setSecondaryColor(int color) {
        this.secondaryColor.set(color);
    }
    
    public void setSurfaceColor(int color) {
        this.surfaceColor.set(color);
    }
    
    public void setBackgroundColor(int color) {
        this.backgroundColor.set(color);
    }
    
    public void setOnPrimaryColor(int color) {
        this.onPrimaryColor.set(color);
    }
    
    public void setOnSecondaryColor(int color) {
        this.onSecondaryColor.set(color);
    }
    
    public void setOnSurfaceColor(int color) {
        this.onSurfaceColor.set(color);
    }
    
    public void setOnBackgroundColor(int color) {
        this.onBackgroundColor.set(color);
    }

    public static Theme getTheme(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("theme", MODE_PRIVATE);
        int color = sharedPreferences.getInt("theme_color", Color.parseColor("#7797CF"));
        int color2 = sharedPreferences.getInt("theme_color2", Color.parseColor("#000000"));
        boolean fullscreen = sharedPreferences.getBoolean("fullscreen", false);
        boolean closeSkinModel = sharedPreferences.getBoolean("close_skin_model", false);
        int animationSpeed = sharedPreferences.getInt("animation_speed", 8);
        Bitmap lt = ImageUtil.load(context.getFilesDir().getAbsolutePath() + "/background/lt.png").orElse(ConvertUtils.getBitmapFromRes(context, R.drawable.background_light));
        BitmapDrawable backgroundLt = new BitmapDrawable(context.getResources(), lt);
        Bitmap dk = ImageUtil.load(context.getFilesDir().getAbsolutePath() + "/background/dk.png").orElse(ConvertUtils.getBitmapFromRes(context, R.drawable.background_dark));
        BitmapDrawable backgroundDk = new BitmapDrawable(context.getResources(), dk);
        return new Theme(color, color2, fullscreen, closeSkinModel, animationSpeed, backgroundLt, backgroundDk);
    }

    public static void saveTheme(Context context, Theme theme) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("theme", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("theme_color", theme.getColor());
        editor.putInt("theme_color2", theme.getColor2());
        editor.putBoolean("fullscreen", theme.isFullscreen());
        editor.putInt("animation_speed", theme.getAnimationSpeed());
        editor.putBoolean("close_skin_model", theme.isCloseSkinModel());
        editor.apply();
    }
}
