package com.example.musicstreamingapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Reusable search field (Material filled pill); inflated from {@code R.layout.view_search_bar}. */
public class SearchBarView extends FrameLayout {

    private TextInputLayout inputLayout;
    private TextInputEditText editText;

    public SearchBarView(@NonNull Context context) {
        this(context, null);
    }

    public SearchBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context);
    }

    private void inflate(Context context) {
        inflate(context, R.layout.view_search_bar, this);
        inputLayout = findViewById(R.id.searchInputLayout);
        editText = findViewById(R.id.searchEditText);
    }

    /** Material wrapper — hints, start icon, box background. */
    public TextInputLayout getTextInputLayout() {
        return inputLayout;
    }

    /** Raw field — attach {@link android.text.TextWatcher}, IME listeners, etc. */
    public TextInputEditText getEditText() {
        return editText;
    }

    public CharSequence getQuery() {
        return editText != null ? editText.getText() : "";
    }

    public void setQuery(CharSequence text) {
        if (editText != null) {
            editText.setText(text);
        }
    }
}
