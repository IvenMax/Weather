package com.iven.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.iven.app.bean.DailyForecastBean;

import java.util.ArrayList;

/**
 * @author Iven
 * @date 2017/2/27 16:46
 * @Description
 */

public class WeatherView extends View {
    private static final String TAG = "zpy_WeatherDoubleView";
    private ArrayList<DailyForecastBean> datas;
    //边界线的颜色(灰色)
    private static final String LINECOLOR = "#666666";
    //默认画笔宽度
    private int stockWidth = 3;
    //文本画笔
    private Paint mTextPaint;
    private int width,height;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void init() {
        datas = new ArrayList<>();
    }

    /**
     * 默认画笔
     *
     * @return Paint
     */
    private Paint getDefaultPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//
        paint.setColor(Color.parseColor(LINECOLOR));
        paint.setStyle(Paint.Style.FILL);//填充样式
        paint.setStrokeWidth(stockWidth);
        return paint;
    }

    public WeatherView(Context context) {
        super(context);
        init();
    }


    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setForcastData(ArrayList<DailyForecastBean> datas) {
        this.datas = datas;
    }
}
