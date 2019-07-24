package com.warchaser.annotations_ui;

import android.view.View;

import androidx.annotation.IdRes;

public interface IFinder {
    View findView(Object object, @IdRes int resId);
}
