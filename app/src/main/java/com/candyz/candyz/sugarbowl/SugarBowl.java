package com.candyz.candyz.sugarbowl;


import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.candyz.candyz.R;
import com.candyz.candyz.ServerInterface;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * A simple {@link Fragment} subclass.
 */
public class SugarBowl extends Fragment
{

    private OnFragmentInteractionListener mListener;

    private ImageView myHandleImageView;

    float myOrigDrawerWidth = 0;
    SugarBowlDrawerListener myDrawerListener;

    DrawerLayout myDrawerLayout;
    ImageView myDrawerHandle;

    private Infinite2dScroller my2dScroller;

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
        mListener.onFragmentCreation(this);
    }

    SugarBowlDataAdapter mySBAdapter;
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



    public void setDrawerParams(DrawerLayout aDrawerLayout_in, ImageView aDrawerHandle_in)
    {
        myDrawerLayout = aDrawerLayout_in;

        myDrawerHandle = aDrawerHandle_in;

        adjustMainDrawer();
    }


    void adjustMainDrawer()
    {
        myDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        myDrawerLayout.setDrawerListener(myDrawerListener);

        moveNavigationDrawerHandleInfluenceToLeft();

    }



    private void moveNavigationDrawerHandleInfluenceToLeft()
    {
        try
        {

            Field mDragger = myDrawerLayout.getClass().getDeclaredField("mRightDragger");//mRightDragger for right obviously
            mDragger.setAccessible(true);
            ViewDragHelper draggerObj = (ViewDragHelper) mDragger.get(myDrawerLayout);

            Field mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
            mEdgeSize.setAccessible(true);
            int edge = mEdgeSize.getInt(draggerObj);

            mEdgeSize.setInt(draggerObj, edge * 5);

        }
        catch (NoSuchFieldException e)
        {

        } catch (IllegalAccessException e)
        {

        }
    }

    class SugarBowlDrawerListener implements DrawerLayout.DrawerListener, View.OnTouchListener
    {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset)
        {
            if(myOrigDrawerWidth == 0)
            {
                myOrigDrawerWidth = myDrawerHandle.getWidth();
            }
            Log.d("aView", "onDrawerSlide. slideOffset : " + slideOffset);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                myDrawerHandle.setTranslationX(-slideOffset * drawerView.getWidth() + slideOffset * myOrigDrawerWidth);

                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) myDrawerHandle.getLayoutParams();
                params.width = (int)((1 - slideOffset) * myOrigDrawerWidth);
                myDrawerHandle.setLayoutParams(params);

            }
            else
            {
                myDrawerHandle.setVisibility(View.INVISIBLE);
            }
            if(slideOffset >= 1.0)
            {
                myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, Gravity.RIGHT);
            }
            else if(slideOffset == 0.0)
            {
                myDrawerHandle.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onDrawerOpened(View drawerView)
        {
            Log.d("aView", "onDrawerOpened");

        }

        @Override
        public void onDrawerClosed(View drawerView)
        {
            Log.d("aView", "onDrawerClosed");

        }

        @Override
        public void onDrawerStateChanged(int newState)
        {
            Log.d("aView", "onDrawerStateChanged, newState : " + newState);
        }

        private boolean isPointInsideView(float x, float y, View view){
            int location[] = new int[2];
            view.getLocationOnScreen(location);
            int viewX = location[0];
            int viewY = location[1];

            //point is inside view bounds
            if(( x > viewX && x < (viewX + view.getWidth())) &&
                    ( y > viewY && y < (viewY + view.getHeight()))){
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            int currentX = (int) event.getRawX();
            int currentY = (int) event.getRawY();

            if(isPointInsideView(currentX, currentY, myHandleImageView))
            {
                myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
            }

            v.onTouchEvent(event);
            return true;
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnFragmentInteractionListener) activity;


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
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
        public void onFragmentCreation(Fragment aFragment_in);
    }

}
