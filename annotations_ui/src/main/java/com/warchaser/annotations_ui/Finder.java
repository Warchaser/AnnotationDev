package com.warchaser.annotations_ui;

import android.app.Dialog;
import android.view.View;
import android.view.Window;

public class Finder implements IFinder{
    @Override
    public View findView(Object object, int resId) {
        if(object instanceof View){
            return findView((View) object, resId);
        }

        if(object instanceof Dialog){
            return findView((Dialog)object, resId);
        }

        if(object instanceof Window){
            return findView((Window)object, resId);
        }

        return null;
    }

    private View findView(Window object, int resId){
        return object.findViewById(resId);
    }

    private View findView(View object, int resId){
        return object.findViewById(resId);
    }

    private View findView(Dialog object, int resId){
        return object.findViewById(resId);
    }
}
