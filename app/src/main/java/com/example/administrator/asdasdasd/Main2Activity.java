package com.example.administrator.asdasdasd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    private float[] location;
    private View layout;
    private ImageView iv;
    private OnclickEffectManagement mEffectManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
        initEvent();
    }

    private void init() {

        mEffectManagement = new OnclickEffectManagement(this);
        iv = (ImageView) findViewById(R.id.iv2);
        iv.setImageResource(R.drawable.children);
        layout = getContentView();
        location = getIntent().getFloatArrayExtra(MainActivity.LOCATION);
    }

    private void initEvent() {
        layout.setVisibility(View.INVISIBLE);
        if (location != null) {
            //为了使iv能取出位置数据，必须要等Activity加载完毕，因此延时传入
            layout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEffectManagement.showEffect(location[0], location[1], iv, layout, getWindowManager());
                }
            }, 100);
        }
    }


    public View getContentView() {
        return ((ViewGroup) Main2Activity.this.findViewById(android.R.id.content)).getChildAt(0);
    }
}
