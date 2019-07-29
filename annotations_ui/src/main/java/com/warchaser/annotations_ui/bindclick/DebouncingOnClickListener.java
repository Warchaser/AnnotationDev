package com.warchaser.annotations_ui.bindclick;

import android.view.View;

public abstract class DebouncingOnClickListener implements View.OnClickListener {

    static boolean enabled = true;

    private static final Runnable ENABLE_AGAIN = new Runnable() {
        @Override
        public void run() {
            enabled = true;
        }
    };

    @Override
    public void onClick(View v) {
        if(enabled){
            enabled = false;
            v.post(ENABLE_AGAIN);
            doClick(v);
        }
    }

    public abstract void doClick(View v);
}
