package com.warchaser.annotationdev.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.warchaser.annotationdev.R;
import com.warchaser.annotationdev.module.click.ButterKnifeTest;
import com.warchaser.annotationdev.module.click.OnClick;
import com.warchaser.annotationdev.module.click.ViewInject;
import com.warchaser.compiler.annotationprocessor.TrackManager;
import com.warchaser.compiler.annotationprocessor.annotation.TrackName;

@TrackName(name = "ClickTestActivity")
public class ClickTestActivity extends BaseActivity{

    @ViewInject(R.id.mBtn1)
    private Button mBtn1;

    @ViewInject(R.id.mTv2)
    private TextView mTv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_test);

        ButterKnifeTest.inject(this);

        TrackManager.getInstance().getTrackNameByClass("ClickTestActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.mBtn1, R.id.mTv2})
    void onClick(View view){
        switch (view.getId()){
            case R.id.mBtn1:
                break;
            case R.id.mTv2:
                break;
            default:
                break;
        }
    }
}
