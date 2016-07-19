package com.example.administrator.asdasdasd;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class OnclickEffectDialog extends AlertDialog {

    private Context mContext;
    private EffectDialogCallback callback;

    private ImageView iv;//图片控件
    private LinearLayout background;//背景

    private float homeLocaX = 0;//初始位置x
    private float homeLocaY = 0;//初始位置y
    private float endLocationX = 0;//结束位置X
    private float endLocationY = 0;//结束位置Y

    private int imageWidth;//图片宽
    private int imageHeight;//图片高
    private final static int IMAGESIZE =16;//图片的放大长度
    private Drawable imageRes;//图片内容


    protected OnclickEffectDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());
//        setContentView(R.layout.dialog_gewala);
//        iv = (ImageView) findViewById(R.id.iv);
        initIv();
//        background = (LinearLayout) findViewById(R.id.background);
        startTranslationAnimation();
    }

    /**
     * 动态生成布局
     **/
    public RelativeLayout initView(){
        RelativeLayout layout = new RelativeLayout(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);

        background = new LinearLayout(mContext);
        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        background.setLayoutParams(params1);
        layout.addView(background);
        background.setAlpha(0);
        background.setBackgroundColor(mContext.getResources().getColor(R.color.gray));

        iv = new ImageView(mContext);
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params2);
        layout.addView(iv);

        return layout;
    }

    /**
     * 开始播放飞行动画
     **/
    public void startTranslationAnimation() {

        ValueAnimator va = new ValueAnimator();
        va.setDuration(1000);//动画时长
        va.setObjectValues(new PointF(0, 0));//设置动画的变动点
        va.setInterpolator(new LinearInterpolator());
        va.setEvaluator(new TypeEvaluator<PointF>() {
            @Override//fraction = t/duration,意义为已播放时长与设置的总时长的比例，用来表示播放进度
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                PointF point = new PointF();
                point.x = (homeLocaX + (endLocationX - homeLocaX) * fraction) - IMAGESIZE / 2;
                point.y = (homeLocaY + (endLocationY - homeLocaY) * fraction) - IMAGESIZE / 2;
                return point;
            }
        });
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
                iv.setX(point.x);
                iv.setY(point.y);
                background.setAlpha(valueAnimator.getAnimatedFraction() * 1f);
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ValueAnimator va1 = new ValueAnimator();
                va1.setDuration(1000);
                va1.setObjectValues(new PointF(0, 0));//设置动画的变动点
                va1.setInterpolator(new LinearInterpolator());
                va1.setEvaluator(new TypeEvaluator<PointF>() {
                    @Override//fraction = t/duration,意义为已播放时长与设置的总时长的比例，用来表示播放进度
                    public PointF evaluate(float fraction, PointF startValue,
                                           PointF endValue) {
                        PointF point = new PointF();
                        point.x = 1f - 1 / 10 * fraction;
                        point.y = 1f - 1 / 10 * fraction;
                        return point;
                    }
                });

                va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        background.setAlpha(1f - valueAnimator.getAnimatedFraction() * 1f);
                    }
                });
                va1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        callback.onLastAnimtorStart();
                        iv.setScaleX(imageWidth/(imageWidth+IMAGESIZE));
                        iv.setScaleY(imageHeight / (imageHeight + IMAGESIZE));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        callback.onAnimatorFinish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                va1.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 设置图片初始位置和图片内容
     **/
    private void initIv() {
        ViewGroup.LayoutParams params = iv.getLayoutParams();
        params.height = imageHeight+IMAGESIZE;
        params.width = imageWidth+IMAGESIZE;
        iv.setLayoutParams(params);
        if (imageRes != null) {
            iv.setImageDrawable(imageRes);
        }
        iv.bringToFront();
        iv.setX(homeLocaX - IMAGESIZE / 2);
        iv.setY(homeLocaY - IMAGESIZE / 2);
    }


    /**
     * 设置图片大小和图片内容
     **/
    public void setImageInfo(int width, int height, Drawable imageRes) {

        imageWidth = width;
        imageHeight = height;
        this.imageRes = imageRes;
    }


    /**
     * 设置动画的开始位置和结束位置
     **/
    public void setLocation(float homex, float homey, float endx, float endy) {
        homeLocaX = homex;
        homeLocaY = homey;
        endLocationX = endx;
        endLocationY = endy;
    }

    /**
     * 设置动画结束的回调
     **/
    public void setCallback(EffectDialogCallback callback) {
        this.callback = callback;
    }


    public interface EffectDialogCallback {
        public void onAnimatorFinish();
        public void onLastAnimtorStart();
    }
}
