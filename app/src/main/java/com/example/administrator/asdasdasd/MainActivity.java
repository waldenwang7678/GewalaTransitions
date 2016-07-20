package com.example.administrator.asdasdasd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String LOCATION ="location" ;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
    }

   // @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void click(View view) {

//        TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 200);
//        //AnimationUtils.loadAnimation(R.anim.**);
//        animation.setDuration(1000);
//        iv.startAnimation(animation);
        //animation.start();
        float[] location = OnclickEffectManagement.getViewLocationInActivity(iv, getContentView());


        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        intent.putExtra(LOCATION,location);
        startActivity(intent);
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

    }
    public View getContentView() {
        return ((ViewGroup) MainActivity.this.findViewById(android.R.id.content)).getChildAt(0);
    }



    public void gewala(View view){
        Intent intent=new Intent(this,FlyCircleActivity.class);
        startActivity(intent);
    }
}
