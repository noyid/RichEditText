package com.jacksen.richedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jacksen on 2015/12/21.
 */
public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener, View.OnTouchListener, TextWatcher {

    /**
     * show the clear icon or not
     */
    private boolean showClearIcon = false;

    /**
     * clear icon drawable
     */
    private Drawable clearDrawable = null;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText, defStyleAttr, 0);
        showClearIcon = typedArray.getBoolean(R.styleable.ClearEditText_clear, false);

        if (!showClearIcon) {
            return;
        }

        this.setOnFocusChangeListener(this);
        this.setOnTouchListener(this);
        this.addTextChangedListener(this);
        init(context);
    }

    /**
     *
     */
    private void init(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.abc_ic_clear_mtrl_alpha);
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        clearDrawable = wrappedDrawable;
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicHeight(), clearDrawable.getIntrinsicWidth());

//        showClearIcon(true);
    }

    /**
     * @param flag
     */
    private void showClearIcon(boolean flag) {
        clearDrawable.setVisible(flag, false);
        Drawable[] compoundDrawables = getCompoundDrawables();
        this.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1],
                flag ? clearDrawable : null, compoundDrawables[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            showClearIcon(getText().length() > 0);
        } else {
            showClearIcon(false);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        if (clearDrawable.isVisible() && getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth() < x) {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                setText("");
            }
            return true;
        }
        return false;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        showClearIcon(s.length() > 0);
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {

    }
}