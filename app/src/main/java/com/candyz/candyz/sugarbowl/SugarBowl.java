package com.candyz.candyz.sugarbowl;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.candyz.candyz.R;
import com.candyz.candyz.ServerInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class SugarBowl extends Fragment
{

    private OnFragmentInteractionListener myListener;

    private ImageView myHandleImageView;

    SugarBowlDrawerListener myDrawerListener;

    private Infinite2dScroller my2dScroller;

    private FrameLayout myParentView;

    SugarBowlDataAdapter mySBAdapter;

    public SugarBowl()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sugar_bowl, container, false);
    }



    @Override
    public void onStart()
    {
        super.onStart();

        myListener.onFragmentCreation(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        myHandleImageView = (ImageView)view.findViewById(R.id.sugar_bowl_handle_on_drawer_id);
        my2dScroller = (Infinite2dScroller)view.findViewById(R.id.sugar_container);

        mySBAdapter = new SugarBowlDataAdapter(view.getContext());
        my2dScroller.setAdapter(mySBAdapter);
        mySBAdapter.addRow(ServerInterface.getNextChunkRow());
        mySBAdapter.addRow(ServerInterface.getNextChunkRow());
        mySBAdapter.addRow(ServerInterface.getNextChunkRow());
        mySBAdapter.addRow(ServerInterface.getNextChunkRow());
        mySBAdapter.addRow(ServerInterface.getNextChunkRow());
        mySBAdapter.addRow(ServerInterface.getNextChunkRow());
        mySBAdapter.addRow(ServerInterface.getNextChunkRow());

        myDrawerListener = new SugarBowlDrawerListener();

        view.setOnTouchListener(myDrawerListener);

    }


    public void setParentView(FrameLayout aView_in)
    {
        myParentView = aView_in;

    }


    class SugarBowlDrawerListener implements View.OnTouchListener
    {
        int myLastX;
        int myLastY;
        int myOriginalMargin;

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    myLastX = (int) event.getRawX();
                    myLastY = (int) event.getRawY();

                    if(myOriginalMargin == 0)
                    {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myParentView.getLayoutParams();
                        myOriginalMargin = params.leftMargin;
                    }

                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    int x2 = (int) event.getRawX();
                    int y2 = (int) event.getRawY();

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myParentView.getLayoutParams();
                    int aNewLeftMargin = params.leftMargin + (x2 - myLastX);

                    if(aNewLeftMargin >= 0 && aNewLeftMargin < myOriginalMargin)
                    {
                        params.leftMargin = aNewLeftMargin;
                        myParentView.setLayoutParams(params);
                    }
                    Log.d("aView", "onTouch, params.leftMargin : " + params.leftMargin);

                    myLastX = x2;
                    myLastY = y2;
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myParentView.getLayoutParams();
                    if(params.leftMargin < myOriginalMargin / 2)
                    {
                        dragTo(params, params.leftMargin, 0);
                    }
                    else
                    {
                        dragTo(params, params.leftMargin, myOriginalMargin);
                    }

                    break;
                }
           }

            v.onTouchEvent(event);
            return true;
        }

        @TargetApi(11)
        void dragTo(final RelativeLayout.LayoutParams params, int aStart_in, int anEnd_in)
        {
            if(Build.VERSION.SDK_INT < 11)
            {
                //  No animations :(
                params.leftMargin = anEnd_in;
                myParentView.setLayoutParams(params);
                return;
            }

            AnimatorSet as = new AnimatorSet();

            ValueAnimator animator = ValueAnimator.ofInt(aStart_in, anEnd_in);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {
                    params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
                    myParentView.setLayoutParams(params);
                }
            });
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(500);

            as.playSequentially(animator);

            as.start();
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            myListener = (OnFragmentInteractionListener) activity;


        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        myListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
        public void onFragmentCreation(Fragment aFragment_in);
    }

}
