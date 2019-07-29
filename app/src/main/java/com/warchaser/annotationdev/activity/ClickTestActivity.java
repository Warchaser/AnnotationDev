package com.warchaser.annotationdev.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.warchaser.annotationdev.R;
import com.warchaser.annotations.bindclick.BindClick;
import com.warchaser.annotations.viewid.ViewById;
import com.warchaser.annotations_ui.ViewBinder;

public class ClickTestActivity extends BaseActivity{

    @ViewById(R.id.mBtn1)
    Button mBtn1;

    @ViewById(R.id.mTv2)
    TextView mTv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_test);

        ViewBinder.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @BindClick(R.id.mBtn1)
    void click(){

    }

}
