package com.candyz.candyz.sugarbowl;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.candyz.candyz.R;

/**
 * Created by u on 16.06.2015.
 */
public class SugarViewHolder extends Infinite2dScroller.ViewHolder
{
    public CardView myCard;
    public TextView myTitleView;
    public TextView myDescView;
    public ImageView myImageView;

    SugarViewHolder(View itemView)
    {
        super(itemView);

        myCard = (CardView)itemView.findViewById(R.id.sugar_card);
        myTitleView = (TextView)itemView.findViewById(R.id.sugar_title);
        myDescView = (TextView)itemView.findViewById(R.id.sugar_desc);
        myImageView = (ImageView)itemView.findViewById(R.id.sugar_photo);

    }
}
