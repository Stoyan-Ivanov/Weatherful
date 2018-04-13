package com.stoyan.weatherful.view_utils.recyclerview_utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by stoyan.ivanov2 on 3/26/2018.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }
}
