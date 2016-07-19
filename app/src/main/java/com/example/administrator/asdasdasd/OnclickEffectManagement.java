package com.example.administrator.asdasdasd;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;

public class OnclickEffectManagement {

    private Context mContext;

    private float homeLocaX = 0;//初始位置x
    private float homeLocaY = 0;//初始位置y
    private float endLocationX = 0;//结束位置X
    private float endLocationY = 0;//结束位置Y

    public OnclickEffectManagement(Context context) {
        mContext = context;
    }

    /**
     * 开启点击特效
     **/
    public void showEffect(float homeLocationX, float homeLocationY, ImageView endView, final View contentView, WindowManager windowManager) {
        homeLocaX = homeLocationX;
        homeLocaY = homeLocationY;
        float[] end = getViewLocationInActivity((View) endView, contentView);
        endLocationX = end[0];
        endLocationY = end[1];

        final OnclickEffectDialog dialog = new OnclickEffectDialog(mContext, R.style.EffectDialog);
        dialog.setLocation(homeLocaX, homeLocaY, endLocationX, endLocationY);
        dialog.setImageInfo(endView.getWidth(), endView.getHeight(), endView.getDrawable());
        dialog.setCallback(new OnclickEffectDialog.EffectDialogCallback() {
            @Override
            public void onAnimatorFinish() {
                dialog.cancel();
            }

            @Override
            public void onLastAnimtorStart() {
                contentView.setVisibility(View.VISIBLE);
                mContext.setTheme(R.style.AppTheme);
            }
        });
        dialog.show();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        lp.height = (int) (display.getHeight());//设置高度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);

    }

    /**
     * 获得目标View在本Activity相对于contentView的坐标，location[0]为X,  location[1]为Y
     **/
    public static float[] getViewLocationInActivity(View mOnclickView, View contentView) {
        float[] location = new float[2];
        location[0] = mOnclickView.getX();
        location[1] = mOnclickView.getY();
        ViewParent viewParent = mOnclickView.getParent();
        while (viewParent instanceof View && viewParent != contentView) {
            final View view = (View) viewParent;
            location[0] = location[0] + view.getX();
            location[1] = location[1] + view.getY();
            viewParent = view.getParent();
        }
        return location;
    }
}
