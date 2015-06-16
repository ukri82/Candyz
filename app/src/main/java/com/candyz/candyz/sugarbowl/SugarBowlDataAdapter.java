package com.candyz.candyz.sugarbowl;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.candyz.candyz.R;

import com.candyz.candyz.data.Matrix;
import com.candyz.candyz.utils.Utils;
import com.candyz.candyz.data.DataAccess.Sugar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 16.06.2015.
 */
class SugarBowlDataAdapter extends Infinite2dScroller.DataAadapter<SugarViewHolder>
{

    Context myContext;
    Matrix<Sugar> mySugarMat;
    private LayoutInflater myInflator;

    SugarBowlDataAdapter(Context ctx)
    {
        myContext = ctx;
        this.myInflator = LayoutInflater.from(myContext);
        mySugarMat = new Matrix<>(Sugar.class, 0, 0);

        Matrix<Integer> aMat = new Matrix<>(Integer.class, 4, 4);
        Log.i("SugarBowlDataAdapter", "Matrix = " + aMat);
        aMat.set(2, 0, 23);
        Log.i("SugarBowlDataAdapter", "Matrix = " + aMat);
        aMat.set(3, 2, 27);
        Log.i("SugarBowlDataAdapter", "Matrix = " + aMat);
        ArrayList<Integer> lst = new ArrayList<>();
        lst.add(1);lst.add(2);lst.add(3);lst.add(4);
        aMat.addColumn(lst);
        Log.i("SugarBowlDataAdapter", "Matrix = " + aMat);
        aMat.set(3, 4, 27);
        Log.i("SugarBowlDataAdapter", "Matrix = " + aMat);

        ArrayList<Integer> lst1 = new ArrayList<>();
        lst1.add(1);lst1.add(2);lst1.add(3);lst1.add(4);lst1.add(5);
        aMat.addRow(lst1);
        Log.i("SugarBowlDataAdapter", "Matrix = " + aMat);
        aMat.set(4, 4, 29);
        Log.i("SugarBowlDataAdapter", "Matrix = " + aMat);
    }

    @Override
    public int getItemCountX()
    {
        return mySugarMat.getNumRows();
    }

    @Override
    public int getItemCountY()
    {
        return mySugarMat.getNumCols();
    }

    @Override
    public void onBindViewHolder(SugarViewHolder holder, int positionX, int positionY)
    {
        Sugar aCurrent = mySugarMat.get(positionX, positionY);
        holder.myTitleView.setText(aCurrent.myName);
        holder.myDescView.setText(aCurrent.myDescription);
    }

    @Override
    public SugarViewHolder onCreateViewHolder(ViewGroup parent)
    {
        /*ImageView imageView  = new ImageView(myContext);
        imageView.setImageResource(R.drawable.candyzlogo);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        imageView.setId(Utils.IDGenerator.generateViewId());*/

        View view = this.myInflator.inflate(R.layout.sugar, parent, false);
        view.setId(Utils.IDGenerator.generateViewId());

        return new SugarViewHolder(view);
    }


    public void addColumn(List<Sugar> aSugarList_in)
    {
        mySugarMat.addColumn(aSugarList_in);
    }

    public void addRow(List<Sugar> aSugarList_in)
    {
        mySugarMat.addRow(aSugarList_in);
    }

}

