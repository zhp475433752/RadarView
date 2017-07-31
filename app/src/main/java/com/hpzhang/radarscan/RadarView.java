package com.hpzhang.radarscan;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by hpzhang on 2017/7/27.
 * 雷达扫描界面
 */

public class RadarView extends RelativeLayout {

    private Context mContext;
    /**
     * 四个圆的半径
     */
    private int vadar_radius1, vadar_radius2, vadar_radius3, vadar_radius4;
    private Paint paintCicle;
    /**
     * 四个圆画笔的宽度
     */
    private int ciclrStrokeWidth;
    /**
     * 雷达扫描的原点坐标
     */

    /**
     * 雷达半径
     */
    private int scanRadius;

    /**
     * 旋转动画
     */
    RotateAnimation rotateAnimation;
    /**
     * 扇形区域
     */
    SectorView sectorView;

    public RadarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        vadar_radius1 = (int) (screenWidth * 0.2);
        vadar_radius2 = (int) (screenWidth * 0.3);
        vadar_radius3 = (int) (screenWidth * 0.45);
        vadar_radius4 = (int) (screenWidth * 0.7);
        ciclrStrokeWidth = 1;
        scanRadius = vadar_radius4;

        setBackgroundResource(R.mipmap.radar_bg);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT, TRUE);

        paintCicle = new Paint();
        paintCicle.setAntiAlias(true);
        paintCicle.setStyle(Paint.Style.STROKE);
        paintCicle.setStrokeWidth(1);
        paintCicle.setColor(ContextCompat.getColor(mContext, R.color.colorGray));

        // 扇形区域View
        sectorView = new SectorView(mContext);
        addView(sectorView, layoutParams);

        // 旋转动画
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(linearInterpolator);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setFillAfter(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mPointX = getWidth() / 2;
        int mPointY = getHeight() / 2;
        canvas.drawCircle(mPointX, mPointY, vadar_radius1, paintCicle);
        canvas.drawCircle(mPointX, mPointY, vadar_radius2, paintCicle);
        canvas.drawCircle(mPointX, mPointY, vadar_radius3, paintCicle);
        canvas.drawCircle(mPointX, mPointY, vadar_radius4, paintCicle);
    }

    /**
     * 扇形区域
     */
    public class SectorView extends View {
        RectF rectF;
        Paint paintSector;
        Paint paintLine;
        SweepGradient sweepGradient;

        public SectorView(Context context) {
            super(context);
            paintSector = new Paint();
            paintSector.setAntiAlias(true);
            paintSector.setStyle(Paint.Style.FILL);
            paintSector.setStrokeWidth(scanRadius);
            paintSector.setColor(ContextCompat.getColor(mContext, R.color.colorGrayAlpha));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int positionX = getWidth() / 2;
            int positionY = getHeight() / 2;

            // 渐变
//            sweepGradient = new SweepGradient(positionX, positionY, Color.TRANSPARENT, Color.parseColor("33FFFFFF"));
            /**
             * 扇形的原点在这个矩形区域的中心
             */
            rectF = new RectF(positionX - scanRadius, positionY - scanRadius, positionX + scanRadius, positionY + scanRadius);
            paintLine = new Paint();
            paintLine.setAntiAlias(true);
            paintLine.setStyle(Paint.Style.FILL);
            paintLine.setStrokeWidth(1);
            paintLine.setColor(ContextCompat.getColor(mContext, R.color.colorGray));
            canvas.drawLine(positionX, positionY, positionX, positionY+scanRadius, paintLine);
            // 绘制扇形区域
//            paintSector.setShader(sweepGradient);
            // 绘制渐变圆形 当旋转动画执行后 超出屏幕的位置无绘制图像。。。！！！
//            canvas.drawCircle(positionX, positionY, scanRadius, paintSector);
            canvas.drawArc(rectF, 45, 45, true, paintSector);
        }
    }

    public void startRotateAnimation() {
        sectorView.startAnimation(rotateAnimation);
    }

}
