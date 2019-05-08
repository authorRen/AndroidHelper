package com.rex.app_library.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 着色工具类
 *
 * Created by renzeqiang
 * on 2019/5/7
 */
public class TintUtils {

    /**
     * 给Drawable着色
     *
     * @param drawable          待着色的drawable
     * @param colorStateList    ColorStateList,如ColorStateList.valueOf(Color.RED)
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colorStateList) {
        final Drawable wrap = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrap, colorStateList);
        return wrap;
    }

    /**
     * 给EditText光标着色
     *
     * @param color     如Color.RED
     */
    public static void tintCursorDrawable(EditText editText, int color) {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field mEditor = TextView.class.getDeclaredField("mEditor");
            mEditor.setAccessible(true);
            Object editor = mEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field mCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            mCursorDrawable.setAccessible(true);

            if (mCursorDrawableRes <= 0) {
                return;
            }

            Drawable cursorDrawable = editText.getContext().getResources().getDrawable(mCursorDrawableRes);
            if (cursorDrawable == null) {
                return;
            }

            Drawable tintDrawable = tintDrawable(cursorDrawable, ColorStateList.valueOf(color));
            Drawable[] drawables = new Drawable[]{tintDrawable, tintDrawable};
            mCursorDrawable.set(editor, drawables);
        } catch (Throwable ignore) {
        }
    }















}
