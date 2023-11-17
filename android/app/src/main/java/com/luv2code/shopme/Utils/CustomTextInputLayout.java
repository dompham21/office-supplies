package com.luv2code.shopme.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

public class CustomTextInputLayout extends TextInputLayout {
    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        clearEditTextColorfilter();
    }
    @Override
    public void setError(@Nullable CharSequence error) {
        super.setError(error);
        clearEditTextColorfilter();
    }

    private void clearEditTextColorfilter() {
        EditText editText = getEditText();
        if (editText != null) {
            Drawable background = editText.getBackground();
            if (background != null) {
                background.clearColorFilter();
            }
        }
    }
}