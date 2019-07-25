package com.warchaser.annotations_ui.viewid;

import android.view.View;

import androidx.annotation.IdRes;

public interface IFinder {
    View findView(Object object, @IdRes int resId);
}
