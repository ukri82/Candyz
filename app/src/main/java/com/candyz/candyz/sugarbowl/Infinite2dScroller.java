package com.candyz.candyz.sugarbowl;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.candyz.candyz.R;

import java.util.ArrayList;
import java.util.List;


public class Infinite2dScroller extends RelativeLayout
{

    Infinite2dTouchListener myViewTouchListener;


    public Infinite2dScroller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public Infinite2dScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Infinite2dScroller(Context context) {
        super(context);
        initView();
    }


    private void initView()
    {

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.setLayoutParams(params1);

        myViewTouchListener = new Infinite2dTouchListener();
        setOnTouchListener(myViewTouchListener);

    }

    public static abstract class ViewHolder {
        public static final int NO_POSITION = -1;
        public final View myItemView;
        int myXPosition = NO_POSITION;
        int myYPosition = NO_POSITION;

        public ViewHolder(View itemView)
        {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.myItemView = itemView;
        }
    }
    public static abstract class DataAadapter<VH extends ViewHolder>
    {
        abstract public int getItemCountX();
        abstract public int getItemCountY();
        abstract public void onBindViewHolder(VH holder, int positionX, int positionY);
        abstract public VH onCreateViewHolder(ViewGroup parent);

        private List<ViewHolder> myVHList = new ArrayList<>();

        public ViewHolder getVHAt(int x, int y)
        {
            for(int i = 0; i < myVHList.size(); i++)
            {
                if(myVHList.get(i).myXPosition == x && myVHList.get(i).myYPosition == y)
                {
                    return myVHList.get(i);
                }
            }
            return null;
        }

        public void setVHAt(int x, int y, ViewHolder aVH_in)
        {
            aVH_in.myXPosition = x;
            aVH_in.myYPosition = y;
            myVHList.add(aVH_in);
        }
    }

    private DataAadapter myDataAdapter;

    public void setAdapter(DataAadapter anAdapter_in)
    {
        myDataAdapter = anAdapter_in;
        createFirstChild();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        Log.d("Infinite2dScroller", "onLayout, changed :" + changed + " l : " + l + ", t : " + t + ", r : " + r + ", b : " + b);
        super.onLayout(changed, l, t, r, b);

        if(changed)
        {
            createChildren();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMeasureSpecTemp = (int)(1.5 * widthMeasureSpec);
        int heightMeasureSpecTemp = (int)(1.5 * heightMeasureSpec);
        Log.d("Infinite2dScroller", "onMeasure, widthMeasureSpec :" + widthMeasureSpec + "heightMeasureSpec : " + heightMeasureSpec + "widthMeasureSpecTemp :" + widthMeasureSpecTemp + " heightMeasureSpecTemp : " + heightMeasureSpecTemp);

        super.onMeasure(widthMeasureSpecTemp, heightMeasureSpecTemp);
    }

    @Override
    public boolean onInterceptTouchEvent (MotionEvent ev)
    {
        return true;
    }

    ViewHolder myFirstChild;
    private void createFirstChild()
    {
        myFirstChild = createViewHolder(0, 0);
    }

    private ViewHolder createViewHolder(int i, int j)
    {
        ViewHolder aViewHolder = myDataAdapter.onCreateViewHolder(this);
        myDataAdapter.setVHAt(i, j, aViewHolder);

        aViewHolder.myItemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ViewHolder aLeftVH = myDataAdapter.getVHAt(i, j - 1);
        if(aLeftVH != null)
        {
            layoutParams.addRule(RelativeLayout.RIGHT_OF, aLeftVH.myItemView.getId());
        }
        else
        {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        }

        ViewHolder aTopVH = myDataAdapter.getVHAt(i - 1, j);
        if(aTopVH != null)
        {
            layoutParams.addRule(RelativeLayout.BELOW, aTopVH.myItemView.getId());
        }
        else
        {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        }

        this.addView(aViewHolder.myItemView, layoutParams);

        return aViewHolder;
    }

    private void createChildren()
    {
        int aWidth = this.getWidth();
        int aHeight = this.getHeight();

        Log.d("Infinite2dScroller", "createChildren, aWidth : " + aWidth + ", aHeight : " + aHeight);


        int aCurrentLeftY = 0;

        int aViewHeight = myFirstChild.myItemView.getHeight();
        int aViewWidth = myFirstChild.myItemView.getWidth();

        for(int i = 0; i < myDataAdapter.getItemCountX(); i++)
        {
            int aCurrentLeftX = 0;
            for(int j = 0; j < myDataAdapter.getItemCountY(); j++)
            {
                ViewHolder aViewHolder = myFirstChild;
                if(!(i == 0 && j == 0))
                {
                    aViewHolder = createViewHolder(i, j);
                }

                aCurrentLeftX += aViewWidth;
                Log.d("Infinite2dScroller", "createChildren, aCurrentLeftX : " + aCurrentLeftX);

                if(aCurrentLeftX + aViewWidth > aWidth)
                {
                    Log.d("Infinite2dScroller", "createChildren, Number of views on row : " + j);
                    break;
                }


            }
            aCurrentLeftY += aViewHeight;
            Log.d("Infinite2dScroller", "createChildren, aCurrentLeftY : " + aCurrentLeftY);
            if(aCurrentLeftY + aViewHeight > aHeight)
            {
                Log.d("Infinite2dScroller", "createChildren, Number of views on column : " + i);
                break;
            }

        }
    }


    private class Infinite2dTouchListener implements View.OnTouchListener
    {

        private int currentX;
        private int currentY;

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            Log.d("aView", "onTouch, event : " + event + ", view = " + v);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {

                    currentX = (int) event.getRawX();
                    currentY = (int) event.getRawY();

                    break;
                }

                case MotionEvent.ACTION_MOVE:
                {
                    int x2 = (int) event.getRawX();
                    int y2 = (int) event.getRawY();
                    scrollBy(currentX - x2, currentY - y2);
                    currentX = x2;
                    currentY = y2;
                    break;
                }
                case MotionEvent.ACTION_UP:
                {

                    break;
                }
            }
            v.onTouchEvent(event);
            return true;
        }
    }
}