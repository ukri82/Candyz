package com.candyz.candyz;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by u on 16.06.2015.
 */

public class WideLayout extends RelativeLayout
{

    public WideLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initView();
    }

    public WideLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    public WideLayout(Context context)
    {
        super(context);
        initView();
    }


    private void initView()
    {

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.setLayoutParams(params1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMeasureSpecTemp = (int)(1.5 * widthMeasureSpec);
        int heightMeasureSpecTemp = heightMeasureSpec;
        Log.d("Infinite2dScroller", "onMeasure, widthMeasureSpec :" + widthMeasureSpec + "heightMeasureSpec : " + heightMeasureSpec + "widthMeasureSpecTemp :" + widthMeasureSpecTemp + " heightMeasureSpecTemp : " + heightMeasureSpecTemp);

        super.onMeasure(widthMeasureSpecTemp, heightMeasureSpecTemp);
    }
}