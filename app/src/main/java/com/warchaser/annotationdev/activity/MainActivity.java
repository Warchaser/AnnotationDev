package com.warchaser.annotationdev.activity;

import android.os.Bundle;
import android.view.View;

import com.warchaser.annotationdev.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize(){
        findViewById(R.id.mBtnClickInject1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCertainActivity(ClickTestActivity.class);
            }
        });
    }
}
